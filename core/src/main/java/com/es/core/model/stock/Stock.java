package com.es.core.model.stock;

import com.es.core.model.phone.Phone;
import lombok.Data;

@Data
public class Stock {
    private Phone phone;
    private Integer stock;
    private Integer reserved;
}
