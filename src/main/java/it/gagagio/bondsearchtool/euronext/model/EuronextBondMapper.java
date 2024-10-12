package it.gagagio.bondsearchtool.euronext.model;

import it.gagagio.bondsearchtool.model.Bond;
import it.gagagio.bondsearchtool.model.BondCountry;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
@Slf4j
public class EuronextBondMapper {

    public Bond toBond(final List<String> data) {

        val isin = getIsinFromData(data);
        val name = getNameFromData(data);
        val market = getMarketFromData(data);

        return Bond.builder()
                .isin(isin)
                .name(name)
                .market(market)
                .build();
    }

    public Optional<Integer> getCouponFromHtml(final Document html, final String market) {

        val frequency = getFrequencyFromHtml(html);

        if (frequency.isEmpty()) {
            return Optional.empty();
        }

        val f = frequency.get();
        if (EuronextFrequency.ZERO.equals(f)) {
            return Optional.of(0);
        }

        val coupon = select(html, "tr:contains(Interest Rate):first-child strong");
        val couponMillis = stringToIntegerMillis(coupon);

        return couponMillis.map(c -> {
            BigDecimal result = f.getMultiplier(market).multiply(c).divide(BigDecimal.valueOf(10), RoundingMode.HALF_UP);
            return result.setScale(0, RoundingMode.HALF_UP).intValue();
        });
    }

    public Optional<BondCountry> getCountryFromHtml(final Document html) {

        val country = select(html, "p:nth-child(3) > strong");

        return BondCountry.from(country);
    }

    public Optional<Integer> getLastPriceFromHtml(final Document html) {

        val lastPriceOptional = select(html, "#col-header-instrument-price");
        val lastPrice = stringToIntegerCents(lastPriceOptional).map(lp -> lp.setScale(0, RoundingMode.HALF_UP).intValue());

        // Last price is considered invalid if it is missing or equal to 100 (100_00)
        return lastPrice.map(lp -> lp.equals(100_00)).orElse(true)
                ? getLastPriceFromHtml2(html)
                : lastPrice;
    }

    private Optional<Integer> getLastPriceFromHtml2(final Document html) {

        val lastPrice = select(html, "#header-instrument-price");

        return stringToIntegerCents(lastPrice).map(lp -> lp.setScale(0, RoundingMode.HALF_UP).intValue());
    }

    public Optional<Instant> getMaturityAtFromHtml(final Document html) {

        val maturityAt = select(html, "tr:contains(Repayment date) strong");

        return stringDateToInstant(maturityAt);
    }

    public boolean getPerpetualFromHtml(final Document html) {

        val PERPETUALS = List.of("Indeterminate", "Perpetual bond");
        val repaymentType = select(html, "tr:contains(Repayment type) strong");

        return PERPETUALS.contains(repaymentType);
    }

    public Optional<EuronextType> getTypeFromHtml(final Document html) {

        val subtype = select(html, "tr:nth-child(2) .font-weight-bold");

        return EuronextType.from(subtype);
    }

    private Optional<EuronextFrequency> getFrequencyFromHtml(final Document html) {

        val frequency = select(html, "tr:contains(Interest rate frequency) strong");
        return EuronextFrequency.from(frequency);
    }

    private String getIsinFromData(final List<String> data) {

        val row = data.getFirst();
        val regex = "/bonds/(.*?)-";

        return executeRegex(row, regex).orElseThrow();
    }

    private String getNameFromData(final List<String> data) {

        val row = data.getFirst();
        val regex = "data-title-hover='([^']*)'";

        return executeRegex(row, regex).orElseThrow();
    }

    private String getMarketFromData(final List<String> data) {

        val row = data.get(2);
        val regex = ">([^<]*)<";

        return executeRegex(row, regex).orElseThrow();
    }

    private Optional<BigDecimal> stringToIntegerCents(String s) {
        s = s.replaceAll("%", "");

        try {
            val bdNumber = new BigDecimal(s);
            return Optional.of(bdNumber.multiply(BigDecimal.valueOf(100)));
        } catch (Exception e) {
            log.warn("Failing to parse string {} to int", s);
            return Optional.empty();
        }
    }

    private Optional<BigDecimal> stringToIntegerMillis(String s) {
        s = s.replaceAll("%", "");

        try {
            val bdNumber = new BigDecimal(s);
            return Optional.of(bdNumber.multiply(BigDecimal.valueOf(1000)));
        } catch (Exception e) {
            log.warn("Failing to parse string {} to int", s);
            return Optional.empty();
        }
    }

    private Optional<Instant> stringDateToInstant(final String s) {
        if (StringUtils.isBlank(s)) {
            log.warn("Malformed date is blank");
            return Optional.empty();
        }
        val segments = s.split("/");
        if (segments.length != 3) {
            log.warn("Malformed date has {} segments", segments.length);
            return Optional.empty();
        }
        val intSegments = Arrays.stream(segments).map(Integer::parseInt).toList();
        val date = LocalDateTime.of(intSegments.get(2), intSegments.get(1), intSegments.get(0), 0, 0);
        return Optional.of(date.toInstant(ZoneOffset.UTC));
    }

    private Optional<String> executeRegex(final String s, final String regex) {

        val pattern = Pattern.compile(regex);
        val matcher = pattern.matcher(s);

        return matcher.find() ? Optional.ofNullable(matcher.group(1)) : Optional.empty();
    }

    private String select(final Document html, final String path) {

        return html.select(path).text().trim();
    }
}
