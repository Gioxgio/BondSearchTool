package it.gagagio.bondsearchtool.scheduler.utils;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class YieldToMaturityUtilsTest {

    private final YieldToMaturityUtils unitToTest = new YieldToMaturityUtils();

    @Test
    void calculateYieldToMaturity_success() {

        val today = stringDateToInstant("08/10/2024");
        val maturityAt = stringDateToInstant("20/10/2030");
        val coupon = 345;
        val price = 10577;

        val result = unitToTest.calculateYieldToMaturity(today, maturityAt, coupon, price);

        assertTrue(result.isPresent());
        assertEquals(242, result.get());
    }

    private Instant stringDateToInstant(final String s) {
        val segments = s.split("/");
        val intSegments = Arrays.stream(segments).map(Integer::parseInt).toList();
        val date = LocalDateTime.of(intSegments.get(2), intSegments.get(1), intSegments.get(0), 0, 0);
        return date.toInstant(ZoneOffset.UTC);
    }
}