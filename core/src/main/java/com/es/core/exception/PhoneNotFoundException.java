package com.es.core.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhoneNotFoundException extends RuntimeException {
    public static final String PHONE_WITH_ID_NOT_FOUND = "Phone with ID %d not found.";
    private Long phoneId;

    public PhoneNotFoundException(Long phoneId) {
        super(String.format(PHONE_WITH_ID_NOT_FOUND, phoneId));
        this.phoneId = phoneId;
    }
}
