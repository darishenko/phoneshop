package com.es.core.dao;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.sortEnam.SortField;
import com.es.core.model.phone.sortEnam.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> getById(Long key);

    Optional<Phone> getByModel(String model);

    void save(Phone phone);

    Page<Phone> findAllForSale(SortField sort, SortOrder order, String search, Pageable pageable);
}
