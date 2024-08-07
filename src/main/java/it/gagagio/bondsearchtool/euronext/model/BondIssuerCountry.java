package it.gagagio.bondsearchtool.euronext.model;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum BondIssuerCountry {
    AR("REPUBLIC OF ARGENTINA"),
    AT("REPUBLIC OF AUSTRIA"),
    BE("KINGDOM OF BELGIUM"),
    BH("KINGDOM OF BAHRAIN"),
    BG("REPUBLIC OF BULGARIA"),
    BR("REPUBLIC OF BRAZIL"),
    CA("KINGDOM OF CANADA"),
    CG("REPUBLIC OF CONGO"),
    CL("REPUBLIC OF CHILE"),
    CN("REPUBLIC OF CHINA"),
    CO("REPUBLIC OF COLOMBIA"),
    CY("REPUBLIC OF CYPRUS"),
    DE("REPUBBLICA FEDERALE TEDESCA"),
    DK("KINGDOM OF DENMARK"),
    EE("REPUBLIC OF ESTONIA"),
    EG("REPUBLIC OF EGYPT"),
    ES("KINGDOM OF SPAIN"),
    FI("REPUBLIC OF FINLAND"),
    FR("REPUBLIC OF FRANCE"),
    GR("REPUBLIC OF GREECE"),
    HR("REPUBLIC OF CROATIA"),
    HU("REPUBLIC OF HUNGARY"),
    IE("REPUBLIC OF IRELAND"),
    IT("REPUBLIC OF ITALY"),
    LT("REPUBLIC OF LITUANIA"),
    LV("REPUBLIC OF LATVIA"),
    ME("REPUBLIC OF MONTENGRO"),
    MX("REPUBLIC OF MEXICO"),
    NG("REPUBLIC OF NIGERIA"),
    NL("KINGDOM OF THE NETHERLANDS"),
    NO("KINGDOM OF NORWAY"),
    NZ("KINGDOM OF NEW ZEALAND"),
    PE("REPUBLIC OF PERU"),
    PL("REPUBLIC OF POLAND"),
    PT("REPUBLIC OF PORTUGAL"),
    RO("REPUBLIC OF ROMANIA"),
    SA("KINGDOM OF SAUDI ARABIA"),
    SI("REPUBLIC OF SLOVENIA"),
    SE("KINGDOM OF SWEDEN"),
    TR("REPUBLIC OF TURKEY"),
    US("UNITED STATES OF AMERICA"),
    UK("UNITED KINGDOM"),
    ZA("REPUBLIC OF SOUTH AFRICA"),
    OTHERS("OTHERS");

    private final String name;

    public static BondIssuerCountry valueFrom(final String s) {
        return Arrays.stream(values())
                .filter(v -> v.name.equals(s))
                .findFirst().orElse(OTHERS);
    }
}
