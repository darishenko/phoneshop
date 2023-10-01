package com.es.core.dao;

import com.es.core.model.color.Color;

import java.util.Set;

public interface ColorDao {
    Set<Color> getPhoneColors(Long phoneId);
}
