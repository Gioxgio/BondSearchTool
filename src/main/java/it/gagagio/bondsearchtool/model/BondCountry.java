package it.gagagio.bondsearchtool.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public enum BondCountry {

    AR("ARG", "Argentina"),
    AT("AUT", "Austria"),
    AU("AUS", "Australia"),
    BH("BHR", "Bahrain"),
    BE("BEL", "Belgium"),
    BG("BGR", "Bulgaria"),
    BM("BMU", "Bermuda"),
    BR("BRA", "Brazil"),
    CA("CAN", "Canada"),
    CD("COD", "Democratic Republic of the Congo"),
    CH("CHE", "Switzerland"),
    CI("CIV", "Côte d'Ivoire"),
    CL("CHL", "Chile"),
    CN("CHN", "China"),
    CO("COL", "Colombia"),
    CW("CUW", "Curaçao"),
    CY("CYP", "Cyprus"),
    DE("DEU", "Germany"),
    DK("DNK", "Denmark"),
    EE("EST", "Estonia"),
    EG("EGY", "Egypt"),
    ES("ESP", "Spain"),
    FI("FIN", "Finland"),
    FR("FRA", "France"),
    GB("GBR", "United Kingdom"),
    GG("GGY", "Guernsey"),
    GR("GRC", "Greece"),
    HR("HRV", "Croatia"),
    HU("HUN", "Hungary"),
    IE("IRL", "Ireland"),
    IS("ISL", "Iceland"),
    IT("ITA", "Italy"),
    JE("JEY", "Jersey"),
    JP("JPN", "Japan"),
    KY("CYM", "Cayman Islands"),
    LR("LBR", "Liberia"),
    LT("LTU", "Lithuania"),
    LU("LUX", "Luxembourg"),
    LV("LVA", "Latvia"),
    ME("MNE", "Montenegro"),
    MX("MEX", "Mexico"),
    NG("NGA", "Nigeria"),
    NL("NLD", "Netherlands"),
    NO("NOR", "Norway"),
    NZ("NZL", "New Zealand"),
    OM("OMN", "Oman"),
    PE("PER", "Peru"),
    PH("PHL", "Philippines"),
    PL("POL", "Poland"),
    PT("PRT", "Portugal"),
    QA("QAT", "Qatar"),
    RO("ROU", "Romania"),
    SA("SAU", "Saudi Arabia"),
    SE("SWE", "Sweden"),
    SI("SVN", "Slovenia"),
    SM("SMR", "San Marino"),
    TN("TUN", "Tunisia"),
    TR("TUR", "Turkey"),
    US("USA", "United States"),
    ZA("ZAF", "South Africa");

    private final String a3;
    @Getter
    private final String name;

    public static Optional<BondCountry> from(final String a3) {

        for (val value : values()) {
            if (value.a3.contains(a3)) {
                return Optional.of(value);
            }
        }

        log.warn("Missing country: {}", a3);

        return Optional.empty();
    }
}
