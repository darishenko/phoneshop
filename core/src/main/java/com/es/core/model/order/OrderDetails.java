package com.es.core.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetails {
    private String firstName;
    private String lastName;
    private String deliveryAddress;
    private String contactPhoneNo;
    private String additionalInfo;
}
