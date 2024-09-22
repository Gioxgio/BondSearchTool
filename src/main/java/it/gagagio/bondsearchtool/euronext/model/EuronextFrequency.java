package it.gagagio.bondsearchtool.euronext.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
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
    @Getter
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
}
