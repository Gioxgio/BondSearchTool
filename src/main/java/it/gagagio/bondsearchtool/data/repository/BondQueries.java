package it.gagagio.bondsearchtool.data.repository;

public class BondQueries {

    public static final String COMMON_FILTER = """
            WHERE b.coupon IS NOT NULL
            AND b.country IS NOT NULL
            AND (b.country <> 'AT' OR (b.name NOT LIKE 'AUSTRIA%FRN' AND b.name NOT LIKE 'AUSTRIA%LINK'))
            AND (b.country <> 'BE' OR (b.name NOT LIKE 'BRUC%' AND b.name NOT LIKE 'CFB%' AND b.name NOT LIKE 'CO%' AND b.name NOT LIKE 'CTB%' AND b.name NOT LIKE 'DGB%' AND b.name NOT LIKE 'R%WAL%' AND b.name NOT LIKE 'VLAGE%'))
            AND (b.country <> 'DE' OR b.name NOT LIKE 'BUNDEI%')
            AND (b.country <> 'ES' OR b.name NOT LIKE 'OBLIGACIONESEI%')
            AND (b.country <> 'FR' OR (b.name NOT LIKE 'D%' AND b.name NOT LIKE 'G%' AND b.name NOT LIKE 'ID%' AND b.name NOT LIKE 'OAT0%2505%' AND b.name NOT LIKE 'OAT%DEM' AND b.name NOT LIKE 'OATEI%' AND b.name NOT LIKE 'OATI%' AND b.name NOT LIKE 'OATPPMT%' AND b.name NOT LIKE 'R%' AND b.name NOT LIKE 'V%'))
            AND (b.country <> 'GB' OR b.name NOT LIKE 'UKTI%')
            AND (b.country <> 'GR' OR b.name NOT LIKE 'GGB TV%')
            AND (b.country <> 'IT' OR (b.name NOT LIKE 'BTP COUPON STRIP%' AND b.name NOT LIKE 'BTP ITALIA%' AND b.name NOT LIKE 'BTPI%' AND b.name NOT LIKE 'CCT%'))
            AND (b.country <> 'PT' OR (b.name NOT LIKE 'OTRV%' AND b.name NOT LIKE 'PORTUGAL TV%'))
            AND TIMESTAMPDIFF(HOUR, b.lastModifiedAt, CURDATE()) < 48
            AND b.lastPrice <> 10000
            AND b.market IN ('ETLX', 'MOTX', 'XAMS', 'XBRU', 'XLIS', 'XMOT', 'XPAR')
            AND b.type = 'GOVERNMENT'
            AND b.yieldToMaturity > 0
            AND b.yieldToMaturity < 1500
            """;

    public static final String BONDS_TO_UPDATE = "SELECT b FROM bond b WHERE" +
            " (b.type IS NULL" +
            " OR b.country IS NULL" +
            " OR b.coupon IS NULL" +
            " OR b.maturityAt IS NULL AND NOT b.perpetual)" +
            " AND b.error IS NULL";

    public static final String VALID_BONDS = "SELECT b FROM bond b " + COMMON_FILTER +
            " ORDER BY b.yieldToMaturity DESC";

    public static final String VALID_BONDS_COUNTRIES = "SELECT b.country FROM bond b " + COMMON_FILTER +
            " GROUP BY b.country" +
            " ORDER BY b.country";

    public static final String VALID_BONDS_MARKETS = "SELECT b.market FROM bond b " + COMMON_FILTER +
            " GROUP BY b.market" +
            " ORDER BY b.market";
}
