package com.es.phoneshop.web.dto;

public class AddToCartDto {
    private Long phoneId;
    private Long quantity;

    public AddToCartDto() {
    }

    public AddToCartDto(Long phoneId, Long quantity) {
        this.phoneId = phoneId;
        this.quantity = quantity;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
