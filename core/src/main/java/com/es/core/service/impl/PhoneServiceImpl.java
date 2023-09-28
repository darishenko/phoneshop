package com.es.core.service.impl;

import com.es.core.dao.PhoneDao;
import com.es.core.exception.PhoneNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.sortEnam.SortField;
import com.es.core.model.phone.sortEnam.SortOrder;
import com.es.core.service.PhoneService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Resource
    private PhoneDao phoneDao;

    @Override
    public Phone get(Long key) throws PhoneNotFoundException {
        return phoneDao.get(key).orElseThrow(() -> new PhoneNotFoundException(key));
    }

    @Override
    public void save(Phone phone) {
        phoneDao.save(phone);
    }

    @Override
    public Page<Phone> findAllForSale(SortField sort, SortOrder order, String search, Pageable pageable) {
        return phoneDao.findAllForSale(sort, order, search, pageable);
    }
}
