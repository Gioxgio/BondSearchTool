package it.gagagio.bondsearchtool.euronext.model;

import it.gagagio.bondsearchtool.model.Bond;
import it.gagagio.bondsearchtool.model.BondCountry;
import lombok.val;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class EuronextBondMapper {

    public Bond toBond(final List<String> data) {

        val isin = getIsinFromData(data);
        val name = getNameFromData(data);
        val market = getMarketFromData(data);
        val maturityAt = getMaturityAtFromData(data);
        val coupon = getCouponFromData(data);
        val lastPrice = getLastPriceFromData(data);

        return Bond.builder()
                .isin(isin)
                .name(name)
                .market(market)
                .maturityAt(maturityAt)
                .coupon(coupon)
                .lastPrice(lastPrice)
                .build();
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

    private Instant getMaturityAtFromData(final List<String> data) {

        val row = data.get(3);
        val regex = ">([^<]*)<";

        val date = executeRegex(row, regex).orElse("-");

        return date.equals("-") ? null : Instant.parse(date + "T00:00:00Z");
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

    private Optional<String> executeRegex(final String s, final String regex) {

        val pattern = Pattern.compile(regex);
        val matcher = pattern.matcher(s);

        return matcher.find() ? Optional.ofNullable(matcher.group(1)) : Optional.empty();
    }

    private String select(final Document html, final String path) {

        return html.select(path).text().trim();
    }
}
