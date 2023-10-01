package com.es.core.model.stock;

import com.es.core.model.phone.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    private Phone phone;
    private Integer stock;
    private Integer reserved;
}
