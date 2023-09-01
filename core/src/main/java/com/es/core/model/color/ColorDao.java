package com.es.core.model.color;

import java.util.Set;

public interface ColorDao {
    Set<Color> getPhoneColors(Long phoneId);
}
