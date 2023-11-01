package com.es.core.service.impl;

import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.model.stock.Stock;
import com.es.core.service.PhoneService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @InjectMocks
    private HttpSessionCartService httpSessionCartService;
    @Mock
    private PhoneService phoneService;
    private Cart cart;
    private Phone phone;
    private Phone newPhone;

    @SneakyThrows
    @Before
    public void init() {
        cart = new Cart();
        setCart();

        phone = createNewPhone(BigDecimal.valueOf(100), 10, 3);
        newPhone = createNewPhone(BigDecimal.valueOf(200), 5, 2);
    }

    @Test
    public void getCart_returnCart() {
        Cart cart = httpSessionCartService.getCart();

        assertNotNull(cart);
    }

    @Test
    public void addPhone_phoneNotFromCart_addPhoneToCart() {
        cart.getItems().clear();
        Long quantity = 2L;
        when(phoneService.getById(any())).thenReturn(phone);

        httpSessionCartService.addPhoneById(phone.getId(), quantity);

        assertEquals(1, cart.getItems().size());
        assertEquals(phone, cart.getItems().get(0).getPhone());
        assertEquals(quantity, cart.getTotalQuantity());
        assertEquals(phone.getPrice().multiply(BigDecimal.valueOf(quantity)), cart.getTotalCost());
    }

    @Test(expected = OutOfStockException.class)
    public void addPhone_quantityMoreThanPhoneStock_OutOfStockException() {
        cart.getItems().clear();
        Long quantity = (long) (phone.getStock().getStock() - phone.getStock().getReserved() + 1);
        when(phoneService.getById(any())).thenReturn(phone);

        httpSessionCartService.addPhoneById(phone.getId(), quantity);
    }

    @Test
    public void addPhone_phoneFromCart_updateCartItem() {
        Long quantity = 2L;
        Long expectedQuantity = quantity * 2;
        BigDecimal expectedTotalCost = phone.getPrice().multiply(BigDecimal.valueOf(expectedQuantity));
        addNewPhoneToCart(new ArrayList<>(), phone, quantity);
        when(phoneService.getById(any())).thenReturn(phone);

        httpSessionCartService.addPhoneById(phone.getId(), quantity);

        assertEquals(1, cart.getItems().size());
        assertEquals(phone, cart.getItems().get(0).getPhone());
        assertEquals(expectedQuantity, cart.getItems().get(0).getQuantity());
        assertEquals(expectedQuantity, cart.getTotalQuantity());
        assertEquals(expectedTotalCost, cart.getTotalCost());
    }

    @Test
    public void addPhone_fullCart_addNewPhone() {
        Long phoneQuantity = 2L;
        Long newPhoneQuantity = 3L;
        addNewPhoneToCart(new ArrayList<>(), phone, phoneQuantity);
        BigDecimal expectedTotalCost = phone.getPrice().multiply(BigDecimal.valueOf(phoneQuantity))
                .add(newPhone.getPrice().multiply(BigDecimal.valueOf(newPhoneQuantity)));
        when(phoneService.getById(newPhone.getId())).thenReturn(newPhone);

        httpSessionCartService.addPhoneById(newPhone.getId(), newPhoneQuantity);

        assertEquals(2, cart.getItems().size());
        assertTrue(isPhoneInCart(newPhone));
        assertEquals(phoneQuantity + newPhoneQuantity, cart.getTotalQuantity().longValue());
        assertEquals(expectedTotalCost, cart.getTotalCost());
    }

    @Test
    public void remove_phoneInCart_deletePhoneFromCart() {
        Long phoneQuantity = 2L;
        Long phoneQuantityForDelete = 3L;
        addNewPhoneToCart(new ArrayList<>(), phone, phoneQuantity);
        addNewPhoneToCart(cart.getItems(), newPhone, phoneQuantityForDelete);
        BigDecimal expectedTotalCost = phone.getPrice().multiply(BigDecimal.valueOf(phoneQuantity));

        httpSessionCartService.remove(newPhone.getId());

        assertEquals(1, cart.getItems().size());
        assertTrue(isPhoneInCart(phone));
        assertFalse(isPhoneInCart(newPhone));
        assertEquals(phoneQuantity, cart.getTotalQuantity());
        assertEquals(expectedTotalCost, cart.getTotalCost());
    }

    @Test
    public void tryToUpdate_phoneNotFromCart_notUpdate() {
        cart.getItems().clear();

        boolean updateResult = httpSessionCartService.tryToUpdate(phone.getId(), 2L);

        assertEquals(Boolean.FALSE, updateResult);
        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    public void tryToUpdate_notNewQuantity_notUpdate() {
        Long phoneQuantity = 1L;
        addNewPhoneToCart(new ArrayList<>(), phone, phoneQuantity);

        boolean updateResult = httpSessionCartService.tryToUpdate(phone.getId(), phoneQuantity);

        assertEquals(Boolean.FALSE, updateResult);
        assertEquals(1, cart.getItems().size());
        assertEquals(phoneQuantity, cart.getTotalQuantity());
        assertEquals(phone.getPrice(), cart.getTotalCost());
    }

    @Test(expected = OutOfStockException.class)
    public void tryToUpdate_notAvailableQuantity_notUpdate() {
        addNewPhoneToCart(new ArrayList<>(), phone, 1L);

        httpSessionCartService.tryToUpdate(phone.getId(), (long) (phone.getStock().getStock() + 1));
    }

    @Test
    public void tryToUpdate_availableQuantity_update() {
        long phoneQuantity = 1L;
        addNewPhoneToCart(new ArrayList<>(), phone, phoneQuantity);
        BigDecimal expectedTotalCost = phone.getPrice().multiply(BigDecimal.valueOf(phoneQuantity + 1));

        boolean updateResult = httpSessionCartService.tryToUpdate(phone.getId(), phoneQuantity + 1);

        assertEquals(Boolean.TRUE, updateResult);
        assertEquals(1, cart.getItems().size());
        assertEquals(phoneQuantity + 1, cart.getTotalQuantity().longValue());
        assertEquals(expectedTotalCost, cart.getTotalCost());
    }

    private Phone createNewPhone(BigDecimal price, Integer stock, Integer reserved) {
        Phone phone = new Phone();
        phone.setId(new Random().nextLong());
        phone.setPrice(price);
        phone.setStock(new Stock(phone, stock, reserved));
        return phone;
    }

    private void addNewPhoneToCart(List<CartItem> cartItems, Phone phone, Long quantity) {
        cartItems.add(new CartItem(phone, quantity));
        cart.setItems(cartItems);
        cart.setTotalQuantity(cart.getTotalQuantity() + quantity);
        cart.setTotalCost(cart.getTotalCost().add(phone.getPrice().multiply(BigDecimal.valueOf(quantity))));
    }

    private boolean isPhoneInCart(Phone phone) {
        return cart.getItems().stream()
                .map(CartItem::getPhone)
                .collect(Collectors.toList())
                .contains(phone);
    }

    private void setCart() throws IllegalAccessException, NoSuchFieldException {
        Field productDaoField = HttpSessionCartService.class.getDeclaredField("cart");
        productDaoField.setAccessible(true);
        productDaoField.set(httpSessionCartService, cart);
    }

}