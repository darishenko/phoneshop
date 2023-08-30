package com.es.core.model.phone;

import com.es.core.model.color.ColorDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {
    private static final String QUERY_SELECT_FROM_PHONES = "select * from PHONES";
    private static final String QUERY_SELECT_PHONE_BY_ID = QUERY_SELECT_FROM_PHONES + " where id = ?";
    private static final String QUERY_SELECT_ALL_PHONES = QUERY_SELECT_FROM_PHONES + " limit ? offset ?";
    private static final String QUERY_INSERT_PHONE = "insert into PHONES (brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values (:brand, :model, :price, :displaySizeInches, :weightGr, :lengthMm, :widthMm, :heightMm, :announced, :deviceType, :os, :displayResolution, :pixelDensity, :displayTechnology, :backCameraMegapixels, :frontCameraMegapixels, :ramGb, :internalStorageGb, :batteryCapacityMah, :talkTimeHours, :standByTimeHours, :bluetooth, :positioning, :imageUrl, :description)";
    private static final String QUERY_INSERT_PHONE_TO_COLOR = "insert into PHONE2COLOR (phoneId, colorId) values (?, ?)";
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private ColorDao colorDao;

    public Optional<Phone> get(final Long key) {
        List<Phone> phones = jdbcTemplate.query(QUERY_SELECT_PHONE_BY_ID, new PhoneMapper(colorDao), key);
        if (!phones.isEmpty()){
           return Optional.of(phones.get(0));
        }
        return Optional.empty();
    }

    public void save(final Phone phone) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(phone);
        namedParameterJdbcTemplate.update(QUERY_INSERT_PHONE, namedParameters);
        savePhoneColors(phone);
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query(QUERY_SELECT_ALL_PHONES, new PhoneMapper(colorDao), limit, offset);
    }

    private void savePhoneColors(final Phone phone) {
        List<Object[]> phones2Colors = new ArrayList<>();
        phone.getColors()
                .forEach(color -> phones2Colors.add(new Object[]{phone.getId(), color.getId()}));
        jdbcTemplate.batchUpdate(QUERY_INSERT_PHONE_TO_COLOR, phones2Colors);
    }

}
