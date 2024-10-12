package it.gagagio.bondsearchtool.euronext.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public enum EuronextFrequency {

    ANNUAL("Annual", BigDecimal.valueOf(1)),
    HALF_YEARLY("Half yearly", BigDecimal.valueOf(2)),
    QUARTERLY("Quarterly", BigDecimal.valueOf(4)),
    MONTHLY("Monthly", BigDecimal.valueOf(12)),
    ZERO("Zero coupon", null);

    private final String label;
    private final BigDecimal multiplier;

    public static Optional<EuronextFrequency> from(final String label) {

        for (EuronextFrequency value : values()) {

            if (value.label.equals(label)) {
                return Optional.of(value);
            }
        }

        log.warn("Missing frequency: {}", label);

        return Optional.empty();
    }

    public BigDecimal getMultiplier(final String market) {

        final List<String> MARKETS_WITH_ANNUALISED_COUPON = List.of("ALXP", "ENXL", "XAMS", "XBRU", "XLIS", "XMLI", "XOAM", "XOSL", "XPAR");

        return MARKETS_WITH_ANNUALISED_COUPON.contains(market) ?
                BigDecimal.valueOf(1) :
                this.multiplier;
    }
}
