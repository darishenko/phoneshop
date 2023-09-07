package com.es.core.model.phone;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);

    void save(Phone phone);

    List<Phone> findAll(int limit, int offset);

    List<Phone> findAllForSale(String sort, String order, String search, long limit, long offset);

    Page<Phone> findAllForSale(String sort, String order, String search, Pageable pageable);
}
