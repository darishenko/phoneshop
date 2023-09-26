package com.es.core.service;

import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.sortEnam.SortField;
import com.es.core.model.phone.sortEnam.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PhoneService {
    Phone get(Long key) throws PhoneNotFoundException;

    void save(Phone phone);

    Page<Phone> findAllForSale(SortField sort, SortOrder order, String search, Pageable pageable);

}
