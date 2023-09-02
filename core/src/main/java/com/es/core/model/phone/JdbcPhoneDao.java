package com.es.core.model.phone;

import com.es.core.model.color.ColorDao;
import com.es.core.model.phone.sortEnam.SortField;
import com.es.core.model.phone.sortEnam.SortOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class JdbcPhoneDao implements PhoneDao {
    private static final String QUERY_SELECT_FROM_PHONES = "select * from PHONES";
    private static final String QUERY_SELECT_PHONE_BY_ID = QUERY_SELECT_FROM_PHONES + " where id = ?";
    private static final String QUERY_INSERT_PHONE = "insert into PHONES (brand, model, price, displaySizeInches, weightGr, lengthMm, widthMm, heightMm, announced, deviceType, os, displayResolution, pixelDensity, displayTechnology, backCameraMegapixels, frontCameraMegapixels, ramGb, internalStorageGb, batteryCapacityMah, talkTimeHours, standByTimeHours, bluetooth, positioning, imageUrl, description) values (:brand, :model, :price, :displaySizeInches, :weightGr, :lengthMm, :widthMm, :heightMm, :announced, :deviceType, :os, :displayResolution, :pixelDensity, :displayTechnology, :backCameraMegapixels, :frontCameraMegapixels, :ramGb, :internalStorageGb, :batteryCapacityMah, :talkTimeHours, :standByTimeHours, :bluetooth, :positioning, :imageUrl, :description)";
    private static final String QUERY_INSERT_PHONE_TO_COLOR = "insert into PHONE2COLOR (phoneId, colorId) values (?, ?)";
    private static final String QUERY_USE_LIMIT_OFFSET = " limit ? offset ?";
    private static final String QUERY_ORDER_BY = " order by";
    private static final String QUERY_PHONE_MODEL_LIKE = " and (model ilike '%";
    private static final String QUERY_PHONE_BRAND_LIKE = "%' or brand ilike '%";
    private static final String QUERY_PHONES_WITH_POSITIVE_STOCK = " where id in (select phoneId from STOCKS where stock > 0)";
    private static final String QUERY_SELECT_PHONES_WITH_POSITIVE_STOCK = QUERY_SELECT_FROM_PHONES + QUERY_PHONES_WITH_POSITIVE_STOCK;
    private static final String SPACE = " ";
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
        String query = new StringJoiner(SPACE)
                .add(QUERY_SELECT_FROM_PHONES)
                .add(QUERY_USE_LIMIT_OFFSET).toString();
        return jdbcTemplate.query(query, new PhoneMapper(colorDao), limit, offset);
    }

    public List<Phone> findAllInStock(int offset, int limit, String sort, String order, String search) {
        String query = new StringJoiner(SPACE)
                .add(QUERY_SELECT_PHONES_WITH_POSITIVE_STOCK)
                .add(getSearchPhonesQueryByOrder(sort, order, search))
                .add(QUERY_USE_LIMIT_OFFSET).toString();
        return jdbcTemplate.query(query, new PhoneMapper(colorDao), limit, offset);
    }

    private void savePhoneColors(final Phone phone) {
        List<Object[]> phones2Colors = new ArrayList<>();
        phone.getColors()
                .forEach(color -> phones2Colors.add(new Object[]{phone.getId(), color.getId()}));
        jdbcTemplate.batchUpdate(QUERY_INSERT_PHONE_TO_COLOR, phones2Colors);
    }

    private String getSearchPhonesQueryByOrder(String sort, String order, String search) {
        StringJoiner query = new StringJoiner(SPACE);
        if (Objects.nonNull(search) && !search.isEmpty()) {
            query.add(new StringBuilder(QUERY_PHONE_MODEL_LIKE)
                    .append(search)
                    .append(QUERY_PHONE_BRAND_LIKE)
                    .append(search)
                    .append("%')"));
        }
        SortField sortField = Optional.ofNullable(sort).map(SortField::valueOfLabel).orElse(null);
        SortOrder sortOrder = Optional.ofNullable(order).map(SortOrder::valueOfLabel).orElse(null);
        if (Objects.nonNull(sortField) && Objects.nonNull(sortOrder)) {
            query.add(QUERY_ORDER_BY)
                    .add(sortField.toString())
                    .add(sortOrder.toString());
        }
        return query.toString();
    }

}
