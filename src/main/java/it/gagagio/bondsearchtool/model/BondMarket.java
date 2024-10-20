package it.gagagio.bondsearchtool.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@Slf4j
public enum BondMarket {
    ALXB("Euronext Growth Brussels", false),
    ALXP("Euronext Growth Paris", true),
    ENXL("Euronext Access Lisbon", true),
    ETLX("Eurotlx", false),
    EXGM("Euronext Growth Milan", false),
    MOTX("Electronic Bond Market", false),
    VPXB("Euronext Ventes Publiques Brussels", false),
    XAMS("Euronext Amsterdam", true),
    XBRU("Euronext Brussels", true),
    XLIS("Euronext Lisbon", true),
    XMLI("Euronext Access Paris", true),
    XMOT("Euronext Access Milan", false),
    XOAM("Nordic Alternative Bond Market", true),
    XOSL("Oslo Bors", true),
    XPAR("Euronext Paris", true);

    private final String name;
    private final boolean annualisedCoupon;

    public static Optional<BondMarket> from(final String codes) {

        val code = codes.split(", ")[0];
        for (val value : values()) {
            if (value.name().equals(code)) {
                return Optional.of(value);
            }
        }

        log.warn("Missing market: {}", code);

        return Optional.empty();
    }
}
