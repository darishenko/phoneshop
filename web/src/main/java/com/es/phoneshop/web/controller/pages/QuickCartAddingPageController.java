package com.es.phoneshop.web.controller.pages;

import com.es.core.exception.OutOfStockException;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.service.CartService;
import com.es.phoneshop.web.controller.constant.ControllerConstant;
import com.es.phoneshop.web.controller.constant.ControllerConstant.ErrorMessage;
import com.es.phoneshop.web.controller.constant.ControllerConstant.JspPage;
import com.es.phoneshop.web.controller.constant.ControllerConstant.ModelsAttribute;
import com.es.phoneshop.web.dto.QuickCartAddingDto;
import com.es.phoneshop.web.dto.QuickCartAddingItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/quickCartAdding")
public class QuickCartAddingPageController {
    private static final String QUICK_CART_ADDING_DTO_ITEM_FIELD = "items[%d].%s";
    private static final String QUANTITY = "quantity";
    private static final String MODEL = "model";
    @Resource
    private CartService cartService;

    @GetMapping
    public String getQuickCartAdding(@ModelAttribute QuickCartAddingDto quickCartAddingDto, Model model) {
        setModelAttributes(model, quickCartAddingDto);
        return JspPage.QUICK_CART_ADDING;
    }

    @PostMapping
    public String addPhonesToCart(@Valid @ModelAttribute QuickCartAddingDto quickCartAddingDto,
                                  BindingResult bindingResult, Model model) {
        Map<String, Long> addedPhones = new HashMap<>();
        boolean areAllAdded = tryToAddAllItemsToCartSeparately(quickCartAddingDto, bindingResult, addedPhones);
        setModelAttributes(model, quickCartAddingDto);
        setQuickCartAddingDtoResultMessages(quickCartAddingDto, areAllAdded);
        model.addAttribute(ModelsAttribute.ADDED_PHONES, addedPhones);
        return JspPage.QUICK_CART_ADDING;
    }

    private boolean tryToAddAllItemsToCartSeparately(QuickCartAddingDto quickCartAddingDto, BindingResult bindingResult,
                                                     Map<String, Long> addedPhones) {
        boolean result = true;
        for (int i = 0; i < quickCartAddingDto.getItems().size(); i++) {
            boolean emptyItemErrors = areEmptyQuickCartAddingItemDtoFieldErrors(bindingResult, i, QUANTITY)
                    && areEmptyQuickCartAddingItemDtoFieldErrors(bindingResult, i, MODEL);
            boolean isItemCorrect = false;
            if (emptyItemErrors) {
                isItemCorrect = tryToAddItemToCart(quickCartAddingDto.getItems().get(i), bindingResult, addedPhones, i);
            }
            result = result && isItemCorrect;
        }
        return result;
    }

    private boolean tryToAddItemToCart(QuickCartAddingItemDto item, BindingResult bindingResult,
                                       Map<String, Long> addedPhones, int i) {
        try {
            String model = item.getModel();
            Long quantity = item.getQuantity();
            cartService.addPhoneByModel(model, quantity);
            addedPhones.merge(model.toLowerCase(Locale.ROOT), quantity, Long::sum);
            clearQuickCartAddingItemDto(item);
        } catch (OutOfStockException exception) {
            addQuickCartAddingItemDtoFieldError(bindingResult, i, QUANTITY, item.getQuantity(), exception.getMessage());
            return false;
        } catch (PhoneNotFoundException exception) {
            addQuickCartAddingItemDtoFieldError(bindingResult, i, MODEL, item.getModel(), exception.getMessage());
            return false;
        }
        return true;
    }

    private void clearQuickCartAddingItemDto(QuickCartAddingItemDto item) {
        item.setModel(null);
        item.setQuantity(null);
    }

    private void setModelAttributes(Model model, QuickCartAddingDto quickCartAddingDto) {
        model.addAttribute(ModelsAttribute.CART, cartService.getCart());
        model.addAttribute(ModelsAttribute.QUICK_CART_ADDING_DTO, quickCartAddingDto);
    }

    private void setQuickCartAddingDtoResultMessages(QuickCartAddingDto quickCartAddingDto, boolean areAllAdded) {
        if (areAllAdded) {
            quickCartAddingDto.setSuccessMessage(ControllerConstant.SuccessMessage.ALL_ADDED_TO_CART);
            quickCartAddingDto.setErrorMessage(null);
        } else {
            quickCartAddingDto.setErrorMessage(ErrorMessage.ADD_TO_CART_ERROR);
            quickCartAddingDto.setSuccessMessage(null);
        }
    }

    private void addQuickCartAddingItemDtoFieldError(BindingResult bindingResult, int i, String fieldName,
                                                     Object rejectedValue, String message) {
        bindingResult.addError(new FieldError(ModelsAttribute.QUICK_CART_ADDING_DTO,
                String.format(QUICK_CART_ADDING_DTO_ITEM_FIELD, i, fieldName), rejectedValue, false,
                null, null, message));
    }

    private boolean areEmptyQuickCartAddingItemDtoFieldErrors(BindingResult bindingResult, int i, String fieldName) {
        return bindingResult.getFieldErrors(String.format(QUICK_CART_ADDING_DTO_ITEM_FIELD, i, fieldName)).isEmpty();
    }

}
