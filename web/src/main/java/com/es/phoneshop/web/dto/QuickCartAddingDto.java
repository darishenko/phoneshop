package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuickCartAddingDto {
    @Valid
    private List<QuickCartAddingItemDto> items;
    private String successMessage;
    private String errorMessage;
}
