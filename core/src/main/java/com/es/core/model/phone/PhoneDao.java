package com.es.core.model.phone;

import com.es.core.model.phone.sortEnam.SortField;
import com.es.core.model.phone.sortEnam.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);

    void save(Phone phone);

    Page<Phone> findAllForSale(SortField sort, SortOrder order, String search, Pageable pageable);
}
