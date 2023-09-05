package com.es.core.model.phone;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PhoneDao {
    Optional<Phone> get(Long key);

    void save(Phone phone);

    List<Phone> findAll(int offset, int limit);

    List<Phone> findAllInStock(String sort, String order, String search, long offset, long limit);

    Page<Phone> findAllInStock(String sort, String order, String search, Pageable pageable);
}
