package it.gagagio.bondsearchtool.borsaitaliana;

import lombok.val;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BorsaItaliana {

    private final String URL_TEMPLATE = "https://www.borsaitaliana.it/borsa/obbligazioni/%s/rendimenti-effettivi.html";

    public int getYieldToMaturity(final String isin, final String market) {

        val url = getUrlFromMarket(market);

        val urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
        urlBuilder.addQueryParameter("isin", isin);
        urlBuilder.addQueryParameter("lang", "it");

        val request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .build();

        val client = new OkHttpClient();
        try (val responseBody = client.newCall(request).execute().body()) {
            return getYieldToMaturityFromHtml(responseBody.string());
        } catch (Exception e) {
            return 0;
        }
    }

    private String getUrlFromMarket(final String market) {

        val borsa = switch (market) {
            case "ETLX" -> "eurotlx";
            case "MOTX" -> "mot/euro-obbligazioni";
            case "XMOT" -> "extramot";
            default -> "";
        };

        return URL_TEMPLATE.formatted(borsa);
    }

    private int getYieldToMaturityFromHtml(final String html) {

        val number = Jsoup.parse(html)
                .select("table.m-table tr td:contains(Rendimento effettivo a scadenza lordo) + td")
                .text().trim().replace(",", "");

        return NumberUtils.toInt(number, 0);
    }
}