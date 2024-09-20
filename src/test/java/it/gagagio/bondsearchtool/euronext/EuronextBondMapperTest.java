package it.gagagio.bondsearchtool.euronext;

import it.gagagio.bondsearchtool.euronext.model.EuronextBondMapper;
import it.gagagio.bondsearchtool.euronext.model.EuronextType;
import it.gagagio.bondsearchtool.model.BondCountry;
import lombok.val;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EuronextBondMapperTest {

    private final EuronextBondMapper unitToTest = new EuronextBondMapper();

    @Test
    void toBond_success() {

        val response = getListEntry();

        val result = unitToTest.toBond(response);

        assertEquals("AT0000A39UW5", result.isin());
        assertEquals("AUSTRIA FX 2.9% FEB34 EUR", result.name());
        assertEquals("MOTX", result.market());
        assertEquals(Instant.parse("2034-02-20T00:00:00Z"), result.maturityAt());
        assertEquals(123, result.coupon());
        assertEquals(9860, result.lastPrice());
    }

    @Test
    void getCountryFromHtml_success() {

        val html = getIssuerInfo();

        val result = unitToTest.getCountryFromHtml(html);

        assertTrue(result.isPresent());
        assertEquals(BondCountry.FR, result.get());
    }

    @Test
    void getPerpetualFromHtml_perpetual() {

        val html = getInstrumentInfo_perpetual();

        val result = unitToTest.getPerpetualFromHtml(html);

        assertTrue(result);
    }

    @Test
    void getPerpetualFromHtml_not_perpetual() {

        val html = getInstrumentInfo_not_perpetual();

        val result = unitToTest.getPerpetualFromHtml(html);

        assertFalse(result);
    }

    @Test
    void getTypeFromHtml_success() {

        val html = getInfo();

        val result = unitToTest.getTypeFromHtml(html);

        assertTrue(result.isPresent());
        assertEquals(EuronextType.CORPORATE, result.get());
    }

    private List<String> getListEntry() {
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

    private Document getInfo() {

        return Jsoup.parse("""
                <div class="card">
                    <div class="card-header">
                        <h3 class="nowrap">General Information</h3>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table__group table-sm table-hover">
                                <tbody>
                                    <tr>
                                        <td class='text-left'>
                                            Type
                                        </td>
                                        <td class='text-left font-weight-bold '>
                                            Medium-term notes
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class='text-left'>
                                            Sub type
                                        </td>
                                        <td class='text-left font-weight-bold '>Covered Bonds</td>
                                    </tr>
                                    <tr>
                                        <td class='text-left'>
                                            Market</td>
                                        <td class='text-left font-weight-bold '>Euronext Paris</td>
                                    </tr>
                                    <tr>
                                        <td class='text-left'>
                                            ISIN Code
                                        </td>
                                        <td class='text-left font-weight-bold '>
                                            FR0013396355
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class='text-left'>
                                            Euronext Code
                                        </td>
                                        <td class='text-left font-weight-bold '>
                                            NSCFR00DXBS1
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                """);
    }

    private Document getInstrumentInfo_perpetual() {
        return Jsoup.parse("""
                <div class="card">
                    <div class="card-header">
                        <h3>Instrument Information</h3>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table__group table-sm table-hover">
                                <tr>
                                    <td>Issue Price</td>
                                    <td>
                                        <strong>100.0</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Total number</td>
                                    <td>
                                        <strong>2,500</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Denomination</td>
                                    <td>
                                        <strong>200000</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Issue Date</td>
                                    <td>
                                        <strong>01/02/2018</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Repayment type</td>
                                    <td>
                                        <strong>Perpetual bond</strong>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                """);
    }

    private Document getInstrumentInfo_not_perpetual() {
        return Jsoup.parse("""
                <div class="card">
                    <div class="card-header">
                        <h3>Instrument Information</h3>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table__group table-sm table-hover">
                                <tr>
                                    <td>Issue Price</td>
                                    <td>
                                        <strong>99.882</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Total number</td>
                                    <td>
                                        <strong>45,000,000</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Denomination</td>
                                    <td>
                                        <strong>100</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Issue Date</td>
                                    <td>
                                        <strong>04/09/2024</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Repayment date</td>
                                    <td>
                                        <strong>20/10/2029</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Repayment type</td>
                                    <td>
                                        <strong>In fine</strong>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                """);
    }

    private Document getIssuerInfo() {
        return Jsoup.parse("""
                    <div class="card">
                      <div class="card-header">
                        <h3>Issuer Information</h3>
                      </div>
                      <div class="card-body">
                        <p class="issuerName-row"><span class="issuerName-column-left">Issuer name : </span> <span class="issuerName-column-right"><strong>CAISSE FSE FINANCEMENT LOCAL</strong></span></p>        <p>Issuer Type :  <strong>Other</strong></p>        <p>Issuer country :  <strong>FRA</strong></p>      </div>
                    </div>
                """);
    }
}