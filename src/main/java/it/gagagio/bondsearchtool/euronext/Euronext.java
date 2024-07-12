package it.gagagio.bondsearchtool.euronext;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gagagio.bondsearchtool.euronext.model.BondResponse;
import it.gagagio.bondsearchtool.euronext.model.EuronextBondMapper;
import it.gagagio.bondsearchtool.model.Bond;
import lombok.RequiredArgsConstructor;
import lombok.val;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Euronext {

    private static final String BASE_URL = "https://live.euronext.com/en/pd/data/bond";
    private static final int LENGTH = 7000;
    private static final List<String> MARKETS = List.of("ALXB", "ALXL", "ALXP", "XPAR", "XAMS", "XBRU", "XLIS", "XMLI", "MLXB", "ENXB", "ENXL", "TNLA", "TNLB", "XLDN", "XHFT", "VPXB", "XOSL", "XOAM", "EXGM", "ETLX", "MOTX", "XMOT");

    private final EuronextBondMapper euronextBondMapper;

    public List<Bond> refresh() {

        var page = 0;
        var totalRecords = 0;
        List<Bond> bonds = new ArrayList<>();

        do {

            val responseOptional = getBonds(page);

            if (responseOptional.isEmpty()) {
                return bonds;
            }

            val response = responseOptional.get();
            val data = response.aaData();

            totalRecords = response.iTotalDisplayRecords();

            bonds.addAll(data.stream().map(euronextBondMapper::getBondFromData).toList());

            page++;
        } while (LENGTH * page < totalRecords);

        return bonds;
    }

    private Optional<BondResponse> getBonds(final int page) {

        val urlBuilder = Objects.requireNonNull(HttpUrl.parse(BASE_URL)).newBuilder();
        urlBuilder.addQueryParameter("mics", String.join(",", MARKETS));

        val body = getBondsBody(page);

        val request = new Request.Builder()
                .url(urlBuilder.build())
                .post(body)
                .build();

        val client = new OkHttpClient();
        try (val responseBody = client.newCall(request).execute().body()) {
            val objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return Optional.of(objectMapper.readValue(responseBody.string(), BondResponse.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private FormBody getBondsBody(final int page) {

        return new FormBody.Builder()
                .add("iDisplayLength", String.valueOf(LENGTH))
                .add("iDisplayStart", String.valueOf(LENGTH * page))
                .add("sSortDir_0", "asc").build();
    }
}
