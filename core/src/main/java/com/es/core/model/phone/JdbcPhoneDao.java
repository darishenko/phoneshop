package com.es.core.model.phone;

import com.es.core.model.color.ColorDao;
import com.es.core.model.phone.sortEnam.SortField;
import com.es.core.model.phone.sortEnam.SortOrder;
import com.es.core.model.stock.StockDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private static final String QUERY_PHONES_WITH_NOT_NULL_PRICE = " and price is not NULL";
    private static final String QUERY_SELECT_PHONES_WITH_POSITIVE_STOCK = QUERY_SELECT_FROM_PHONES + QUERY_PHONES_WITH_POSITIVE_STOCK;
    private static final String QUERY_SELECT_PHONES_COUNT_WITH_POSITIVE_STOCK = "select count(*) from PHONES" + QUERY_PHONES_WITH_POSITIVE_STOCK;
    private static final String SPACE = " ";
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private ColorDao colorDao;
    @Resource
    private StockDao stockDao;

    public Optional<Phone> get(final Long key) {
        List<Phone> phones = jdbcTemplate.query(QUERY_SELECT_PHONE_BY_ID, new PhoneMapper(colorDao, stockDao), key);
        if (!phones.isEmpty()) {
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

    public Page<Phone> findAllForSale(SortField sort, SortOrder order, String search, Pageable pageable) {
        List<Phone> phones = findAllForSale(sort, order, search, pageable.getPageSize(), pageable.getOffset());
        Long count = getTotalPhonesCountForSale(search);
        return new PageImpl<>(phones, pageable, count);
    }

    private List<Phone> findAllForSale(SortField sort, SortOrder order, String search, long limit, long offset) {
        String query = new StringJoiner(SPACE)
                .add(QUERY_SELECT_PHONES_WITH_POSITIVE_STOCK)
                .add(QUERY_PHONES_WITH_NOT_NULL_PRICE)
                .add(getSearchOrderQuery(sort, order, search))
                .add(QUERY_USE_LIMIT_OFFSET).toString();
        return jdbcTemplate.query(query, new PhoneMapper(colorDao, stockDao), limit, offset);
    }

    private Long getTotalPhonesCountForSale(String search) {
        String query = new StringJoiner(SPACE)
                .add(QUERY_SELECT_PHONES_COUNT_WITH_POSITIVE_STOCK)
                .add(QUERY_PHONES_WITH_NOT_NULL_PRICE)
                .add(getSearchQuery(search)).toString();
        return jdbcTemplate.queryForObject(query, Long.class);
    }

    private void savePhoneColors(final Phone phone) {
        List<Object[]> phones2Colors = new ArrayList<>();
        phone.getColors()
                .forEach(color -> phones2Colors.add(new Object[]{phone.getId(), color.getId()}));
        jdbcTemplate.batchUpdate(QUERY_INSERT_PHONE_TO_COLOR, phones2Colors);
    }

    private String getSearchOrderQuery(SortField sort, SortOrder order, String search) {
        StringJoiner query = new StringJoiner(SPACE);
        query.add(getSearchQuery(search));

        if (Objects.nonNull(sort) && Objects.nonNull(order)) {
            query.add(QUERY_ORDER_BY)
                    .add(sort.toString())
                    .add(order.toString());
        }
        return query.toString();
    }

    private String getSearchQuery(String search) {
        StringBuilder searchQuery = new StringBuilder();
        if (StringUtils.isNotBlank(search)) {
            search = search.trim();
            searchQuery.append(QUERY_PHONE_MODEL_LIKE)
                    .append(search)
                    .append(QUERY_PHONE_BRAND_LIKE)
                    .append(search)
                    .append("%')");
        }
        return searchQuery.toString();
    }

}
