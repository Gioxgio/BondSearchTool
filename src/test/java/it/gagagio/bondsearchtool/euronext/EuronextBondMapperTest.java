package it.gagagio.bondsearchtool.euronext;

import it.gagagio.bondsearchtool.euronext.model.EuronextBondMapper;
import it.gagagio.bondsearchtool.euronext.model.EuronextType;
import it.gagagio.bondsearchtool.model.BondCountry;
import it.gagagio.bondsearchtool.model.BondMarket;
import lombok.val;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

        val resultOptional = unitToTest.toBond(response);
        assertTrue(resultOptional.isPresent());

        val result = resultOptional.get();
        assertEquals("AT0000A39UW5", result.isin());
        assertEquals("AUSTRIA FX 2.9% FEB34 EUR", result.name());
        assertEquals(BondMarket.MOTX, result.market());
    }

    @ParameterizedTest
    @CsvSource({"3,300", "3.1,310", "3.12,312", "3.127,313"})
    void getCouponFromHtml_success(final String number, final int expected) {

        val html = getCouponInfo(number);

        val result = unitToTest.getCouponFromHtml(html, BondMarket.MOTX);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    void getCountryFromHtml_success() {

        val html = getIssuerInfo();

        val result = unitToTest.getCountryFromHtml(html);

        assertTrue(result.isPresent());
        assertEquals(BondCountry.FR, result.get());
    }

    @Test
    void getLastPriceFromHtml_success() {

        val html = getLastPrice();

        val result = unitToTest.getLastPriceFromHtml(html);

        assertTrue(result.isPresent());
        assertEquals(9391, result.get());
    }

    @Test
    void getMaturityAtFromHtml_success() {

        val html = getInstrumentInfo_not_perpetual();

        val result = unitToTest.getMaturityAtFromHtml(html);

        assertTrue(result.isPresent());
        assertEquals(Instant.parse("2029-10-20T00:00:00Z"), result.get());
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

    private Document getCouponInfo(final String number) {

        return Jsoup.parse("""
                <div class="card">
                        <div class="card-header">
                            <h3>Coupon Information</h3>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table__group table-sm table-hover">
                                    <tr>
                                        <td>Interest Rate</td>
                                        <td><strong>%s%%</strong></td>
                                    </tr>
                                    <tr>
                                        <td>Interest rate frequency</td>
                                        <td><strong>Annual</strong></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                """.formatted(number));
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

    private Document getLastPrice() {
        return Jsoup.parse("""
                <div class="bg-ui-grey-1 pt-4">
                    <div class="container">
                        <div class="row data-header__out">
                            <div class="col">
                                <h1 class="display-6 text-white" id="header-instrument-name">
                                    <strong>CNP TSDI 6,5 3+TV</strong>
                                </h1>
                            </div>
                            <div
                                class="col text-uppercase text-white text-right font-weight-bold x-small address d-flex flex-column pt-2 ">
                                <div>
                                    <div>
                                        <p class="address__text  address__text-sans-line ">
                                            Euronext Paris
                                            Other Markets:&nbsp;
                                            Amsterdam
                                            <br>FR0010167247
                									-
                																			Bond
                                            <br>CNP ASSURANCES
                                            <br>
                                        </p>
                                    </div>
                                    <div>
                                        <div class="enx-symbol-group">
                                            <div class="enx-symbol-top-custom bg-brand-spring-green enx-symbol-length-5">
                                                CNPAP</div>
                
                                            <div class="enx-symbol-bottom bg-brand-teal-green"></div>
                                        </div>
                                    </div>
                                </div>
                                <p>
                                    <span class="d-inline-block">
                										<strong class="pl-2"><a href="/en/product/index/FR0003500008-XPAR" style="text-decoration:none !important;color:#fff !important;">CAC 40</a></strong>
                										0.39%
                																					<svg viewbox="0 0 24 24" width="12" height="9" class="text-brand-spring-green">
                												<use xlink:href="/themes/custom/euronext_live/frontend-library/public/assets//spritemap.svg#index-up"></use>
                											</svg>
                																			</span>
                                    <span class="d-inline-block">
                										<strong class="pl-2"><a href="/en/product/index/FR0003999481-XPAR" style="text-decoration:none !important;color:#fff !important;">SBF 120</a></strong>
                										0.37%
                																					<svg viewbox="0 0 24 24" width="12" height="9" class="text-brand-spring-green">
                												<use xlink:href="/themes/custom/euronext_live/frontend-library/public/assets//spritemap.svg#index-up"></use>
                											</svg>
                																			</span>
                                    <span class="d-inline-block">
                										<strong class="pl-2"><a href="/en/product/index/EURUSDFLIT-WFORX" style="text-decoration:none !important;color:#fff !important;">EUR / USD</a></strong>
                										0.41%
                																					<svg viewbox="0 0 24 24" width="12" height="9" class="text-brand-spring-green">
                												<use xlink:href="/themes/custom/euronext_live/frontend-library/public/assets//spritemap.svg#index-up"></use>
                											</svg>
                																			</span>
                                    <span class="d-inline-block">
                										<strong class="pl-2"><a href="/en/product/index/EURGBPFLIT-WFORX" style="text-decoration:none !important;color:#fff !important;">EUR / GBP</a></strong>
                										0.11%
                																					<svg viewbox="0 0 24 24" width="12" height="9" class="text-brand-spring-green">
                												<use xlink:href="/themes/custom/euronext_live/frontend-library/public/assets//spritemap.svg#index-up"></use>
                											</svg>
                																			</span>
                                </p>
                            </div>
                        </div>
                        <div class="data-header ">
                            <div class="data-header__row bg-ui-yellow">
                                <div class="data-header__col data-header__col-left  ">
                                    <div>
                                        <div class="col text-ui-grey-0 font-weight-bold data-header-cash ">
                                            <div class="lastprice_min_height  pb-1 ">
                                                <span class="data-50 " id="header-instrument-currency">
                																																							%
                																																				</span>
                                                <span class="data-60" id="header-instrument-price">93.91</span>
                                            </div>
                                        </div>
                                        <div class="data-header-last-price-date-time">
                                            <div class="container d-flex justify-content-between">
                                                <div class=" text-ui-grey-0 text-left">
                                                    Last traded price
                                                </div>
                                                <div class="ml-2 last-price-date-time text-ui-grey-0 text-right">
                                                    18/10/2024 - 11:05
                                                    &nbsp;CET
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="mr-0 text-white bg-brand-sky-blue container head_detail head_detail__height">
                                        <div class="d-flex flex-column">
                                            <div class=" font-weight-bold text-uppercase ">
                                                <!-- status CLO -->
                                                <!-- bookState 1 -->
                                                <!-- haltReasonKey  -->
                                            </div>
                                            <div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="data-header__col data-header__col-right bg-white">
                                    <div class="data-header__row head_detail_top">
                                        <div class="col col_head_detail_bottom">
                                            <div class="">
                                                <div class="text-ui-grey-1 data-12 font-weight-bold">Since Open</div>
                                                <span class=" data-24 font-weight-bold mr-2">0.00</span>
                                                <span class="text-ui-grey-1 mr-2">(0.00%)</span>
                                            </div>
                                        </div>
                                        <div class="col col_head_detail_bottom">
                                            <div class="mt-auto">
                                                <div class="text-ui-grey-1 data-12 font-weight-bold">Since Previous Close</div>
                                                <span class="text-brand-kelly-green data-24 font-weight-bold mr-2">+0.06</span>
                                                <span class="text-ui-grey-1 mr-2">(+0.06%)</span>
                                            </div>
                                        </div>
                                        <div class="col col_head_detail_closing_price">
                                            <div class="my-0 ">
                                                <div class="text-ui-grey-1 data-12 font-weight-bold">Valuation Close</div>
                                                <span class=" font-weight-bold  data-24 " id="col-header-instrument-currency">
                  													  														  															%
                  														  													  												</span>
                                                <span class=" font-weight-bold data-24" id="col-header-instrument-price">
                  												</span>
                                            </div>
                                            <div class="mt-0">
                                                <div class="text-ui-grey-1" id="col-header-instrument-closing-price-date-time">
                                                    18/10/2024 - 17:55
                                                    &nbsp;CET
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col col_head_detail_logo d-none d-lg-block text-center">
                                            <div> <img src="/sites/default/files/company_logo/075015.jpg?itok=1675803208 " class="mw-100" alt="image">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="data-header__row head_detail_bottom head_detail__height bg-ui-grey-4">
                                        <h5 class="col">
                                            <span class="text-ui-grey-1 data-12 font-weight-bold">Best Bid</span>
                                            <span>93.91</span>
                                        </h5>
                                        <h5 class="col">
                                            <span class="text-ui-grey-1 data-12 font-weight-bold">Best Ask</span>
                                            <span>94.60</span>
                                        </h5>
                                        <div class="col">
                                            <span class="text-ui-grey-1 data-10">18/10/2024 - 11:05
                													&nbsp;CET</span>
                                        </div>
                                        <div class="icons__column icons__column--column-direction">
                                            <ul class="icons__listing">
                                                <li>
                                                    <button type="button" class="btn btn-link p-0 ml-auto mr-2" role="button" data-toggle="popover" data-placement="bottom" data-trigger="focus" data-html="true" data-content="1D (1 day) and 5D (5 days) charts represents intraday prices (unadjsted) using a 1 minute resolution.">
                													<svg viewbox="0 0 24 24" width="24" height="24" role="presentation" class="">
                														<use xlink:href="/themes/custom/euronext_live/frontend-library/public/assets//spritemap.svg#info"></use>
                													</svg>
                												</button>
                                                </li>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div> 
                """);
    }
}