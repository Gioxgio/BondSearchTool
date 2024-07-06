package it.gagagio.bondsearchtool.model;

import it.gagagio.bondsearchtool.euronext.model.BondIssuerRegion;
import lombok.val;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class BondMapper {

    public Bond getBondFromData(final List<String> data, final BondIssuerRegion region) {

        val id = getIdFromData(data);
        val name = getNameFromData(data);
        val market = getMarketFromData(data);
        val maturityAt = getMaturityAtFromData(data);
        val coupon = getCouponFromData(data);
        val lastPrice = getLastPriceFromData(data);
        val country = getCountryFromData(data);

        return Bond.builder()
                .id(id)
                .name(name)
                .market(market)
                .maturityAt(maturityAt)
                .coupon(coupon)
                .lastPrice(lastPrice)
                .country(country)
                .region(region)
                .build();
    }

    private String getIdFromData(final List<String> data) {

        val row = data.getFirst();
        val regex = "href='[^']*/([^']*)'";

        return executeRegex(row, regex);
    }

    private String getNameFromData(final List<String> data) {

        val row = data.getFirst();
        val regex = "data-title-hover='([^']*)'";

        return executeRegex(row, regex);
    }

    private String getMarketFromData(final List<String> data) {

        val row = data.get(2);
        val regex = ">([^<]*)<";

        return executeRegex(row, regex);
    }

    private Instant getMaturityAtFromData(final List<String> data) {

        val row = data.get(3);
        val regex = ">([^<]*)<";

        val date = executeRegex(row, regex);

        return date.equals("-") ? null : Instant.parse(date + "T00:00:00Z");
    }

    private int getCouponFromData(final List<String> data) {

        val row = data.get(4);
        val regex = "(\\d+\\.\\d+)";

        val coupon = executeRegex(row, regex)
                .replace(".", "");

        return NumberUtils.toInt(coupon, 0);
    }

    private int getLastPriceFromData(final List<String> data) {

        val row = data.get(6);
        val regex = "(\\d+\\,\\d+)";

        val lastPrice = executeRegex(row, regex)
                .replace(",", "");

        return NumberUtils.toInt(lastPrice, 0);
    }

    private BondIssuerCountry getCountryFromData(final List<String> data) {

        val row = data.get(1);
        val regex = "^(.*?)(</div>)$";

        val country = executeRegex(row, regex);

        return BondIssuerCountry.valueFrom(country);
    }

    private String executeRegex(final String s, final String regex) {

        val pattern = Pattern.compile(regex);
        val matcher = pattern.matcher(s);

        return matcher.find() ? matcher.group(1) : "";
    }
}