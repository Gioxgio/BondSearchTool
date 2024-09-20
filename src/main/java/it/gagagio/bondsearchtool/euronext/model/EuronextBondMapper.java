package it.gagagio.bondsearchtool.euronext.model;

import it.gagagio.bondsearchtool.model.Bond;
import it.gagagio.bondsearchtool.model.BondCountry;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

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
        val coupon = getCouponFromData(data);
        val lastPrice = getLastPriceFromData(data);

        return Bond.builder()
                .isin(isin)
                .name(name)
                .market(market)
                .coupon(coupon)
                .lastPrice(lastPrice)
                .build();
    }

    public Optional<Instant> getMaturityAtFromHtml(final Document html) {

        val maturityAt = select(html, "tr:contains(Repayment date) strong");

        return stringDateToInstant(maturityAt);
    }

    public Optional<BondCountry> getCountryFromHtml(final Document html) {

        val country = select(html, "p:nth-child(3) > strong");

        return BondCountry.from(country);
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

    private Integer getCouponFromData(final List<String> data) {

        val row = data.get(4);
        val regex = "(\\d+\\.\\d+)";

        return executeRegex(row, regex)
                .map(r -> r.replace(".", "").replace(",", ""))
                .filter(NumberUtils::isCreatable)
                .map(NumberUtils::toInt)
                .orElse(null);
    }

    private Integer getLastPriceFromData(final List<String> data) {

        val row = data.get(6);
        val regex = "(\\d+\\,\\d+)";

        return executeRegex(row, regex)
                .map(r -> r.replace(".", "").replace(",", ""))
                .filter(NumberUtils::isCreatable)
                .map(NumberUtils::toInt)
                .orElse(null);
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
