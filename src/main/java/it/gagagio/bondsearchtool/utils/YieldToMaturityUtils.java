package it.gagagio.bondsearchtool.utils;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
@Slf4j
public class YieldToMaturityUtils {

    /**
     * Assertions
     * A year is made up of 360 days
     * Rounding is done at 4th decimal digit, rounding mode is half down
     * [Annual Coupon + (FV – PV) ÷ Number of Compounding Periods] ÷ [(FV + PV) ÷ 2]
     * [coupon + (100 - lastPrice) / now()-maturityAt] / [(100 + lastPrice) / 2]
     */
    public Optional<Integer> calculateYieldToMaturity(final Instant today, final Instant maturityAt, final Integer coupon, final Integer lastPrice) {

        try {
            val diff = differenceBetweenDates(today, maturityAt);

            val bdCoupon = BigDecimal.valueOf(coupon);
            val bdFaceValue = BigDecimal.valueOf(100_00);
            val bdLastPrice = BigDecimal.valueOf(lastPrice);

            return Optional.of(bdCoupon.add((bdFaceValue.subtract(bdLastPrice).divide(diff, 4, RoundingMode.HALF_DOWN)))
                    .divide((bdFaceValue.add(bdLastPrice).divide(BigDecimal.valueOf(2), 4, RoundingMode.HALF_DOWN)), 4, RoundingMode.HALF_DOWN)
                    .multiply(BigDecimal.valueOf(100_00)).intValue());
        } catch (final Exception e) {
            log.error("Failed to calculate yield to maturity\ncoupon: {}, last price: {}, today: {}, maturity at: {}", coupon, lastPrice, today, maturityAt);
            return Optional.empty();
        }
    }

    public BigDecimal differenceBetweenDates(final Instant from, final Instant to) {

        val days = ChronoUnit.DAYS.between(from, to);
        return BigDecimal.valueOf(days).divide(BigDecimal.valueOf(360), 4, RoundingMode.HALF_DOWN);
    }
}
