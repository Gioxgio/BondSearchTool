package it.gagagio.bondsearchtool;

import it.gagagio.bondsearchtool.model.BondMapper;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static it.gagagio.bondsearchtool.model.BondIssuerCountry.AT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BondMapperTest {

    private final BondMapper unitToTest = new BondMapper();

    @Test
    void getBondFromData_success() {

        val response = getResponse();

        val result = unitToTest.getBondFromData(response);

        assertEquals("AT0000A39UW5-MOTX", result.id());
        assertEquals("AUSTRIA FX 2.9% FEB34 EUR", result.name());
        assertEquals("MOTX", result.market());
        assertEquals(Instant.parse("2034-02-20T00:00:00Z"), result.maturityAt());
        assertEquals(123, result.coupon());
        assertEquals(9860, result.lastPrice());
        assertEquals(AT, result.country());
    }

    private List<String> getResponse() {
        return List.of(
                "<a href='/en/product/bonds/AT0000A39UW5-MOTX' data-order='AU FX FEB34 EUR' data-title-hover='AUSTRIA FX 2.9% FEB34 EUR'>AU FX FEB34 EUR</a>",
                "REPUBLIC OF AUSTRIA</div>",
                "<div class='nowrap pointer' title='MOT' >MOTX</div>",
                "<div class=\"text-right nowrap\">2034-02-20</div>",
                "<div class=\"text-right nowrap\">1.23%</div>",
                "<div class=\"text-right nowrap\">-</div>",
                "<div class='text-right pd_currency'>% <span class='pd_last_price'>98,60</span></div>",
                "<div class='text-right pd_percent'><span class=text-brand-kelly-green>0,23%</span></div>",
                "<div class='text-right pointer tooltipDesign' >05 Jul 2024<span class=\"tooltiptext\">09:42 CEST</span></div>",
                "<div class=\"text-right nowrap\">45.000M</div>",
                "<div class=\"text-right nowrap\">REPUBLIC OF AUSTRIA</div>"
        );
    }
}