package it.gagagio.bondsearchtool.data.repository;

public class BondQueries {

    public static final String BONDS_TO_UPDATE = "SELECT b FROM bond b WHERE" +
            " (b.type IS NULL" +
            " OR b.country IS NULL" +
            " OR b.coupon IS NULL" +
            " OR b.maturityAt IS NULL AND NOT b.perpetual)" +
            " AND b.error IS NULL";

    public static final String VALID_BONDS = "SELECT b FROM bond b WHERE" +
            " b.coupon IS NOT NULL" +
            " AND b.country IS NOT NULL" +
            " AND (b.country <> '" + "AT" + "' OR (b.name NOT LIKE 'AUSTRIA%FRN' AND b.name NOT LIKE 'AUSTRIA%LINK'))" +
            " AND (b.country <> '" + "BE" + "' OR (name NOT LIKE 'BRUC%' AND name NOT LIKE 'CFB%' AND name NOT LIKE 'CO%' AND name NOT LIKE 'CTB%' AND name NOT LIKE 'DGB%' AND name NOT LIKE 'CTB%' AND name NOT LIKE 'R%WAL%' AND name NOT LIKE 'VLAGE%'))" +
            " AND (b.country <> '" + "DE" + "' OR b.name NOT LIKE 'BUNDEI%')" +
            " AND (b.country <> '" + "ES" + "' OR b.name NOT LIKE 'OBLIGACIONESEI%')" +
            " AND (b.country <> '" + "FR" + "' OR (name NOT LIKE 'D%' AND name NOT LIKE 'G%' AND name NOT LIKE 'ID%' AND name NOT LIKE 'OAT0%2505%' AND name NOT LIKE 'OAT%DEM' AND name NOT LIKE 'OATEI%' AND name NOT LIKE 'OATI%' AND name NOT LIKE 'OATPPMT%' AND name NOT LIKE 'R%' AND name NOT LIKE 'V%'))" +
            " AND (b.country <> '" + "GB" + "' OR b.name NOT LIKE 'UKTI%')" +
            " AND (b.country <> '" + "GR" + "' OR b.name NOT LIKE 'GGB TV%')" +
            " AND (b.country <> '" + "IT" + "' OR (b.name NOT LIKE 'BTP COUPON STRIP%' AND b.name NOT LIKE 'BTP ITALIA%' AND b.name NOT LIKE 'BTPI%' AND b.name NOT LIKE 'CCT%'))" +
            " AND (b.country <> '" + "PT" + "' OR (b.name NOT LIKE 'OTRV%' AND b.name NOT LIKE 'PORTUGAL TV%'))" +
            // ALXB - 2 = ALXP - 3 = XMLI - 3 = XOAM - 1 =
            " AND TIMESTAMPDIFF(HOUR, b.lastModifiedAt, CURDATE()) < 48" + // Get bonds updated in the past 2 days
            " AND b.lastPrice <> 10000" +
            " AND b.market IN ('ETLX', 'MOTX', 'XAMS', 'XBRU', 'XLIS', 'XMOT', 'XPAR')" +
            " AND b.type = '" + "GOVERNMENT" + "'" +
            " AND b.yieldToMaturity > 0" +
            " AND b.yieldToMaturity < 1500" +
            " ORDER BY b.yieldToMaturity DESC";

    public static final String VALID_BONDS_COUNTRIES = "SELECT b.country FROM bond b WHERE" +
            " b.coupon IS NOT NULL" +
            " AND b.country IS NOT NULL" +
            " AND (b.country <> '" + "AT" + "' OR (b.name NOT LIKE 'AUSTRIA%FRN' AND b.name NOT LIKE 'AUSTRIA%LINK'))" +
            " AND (b.country <> '" + "BE" + "' OR (name NOT LIKE 'BRUC%' AND name NOT LIKE 'CFB%' AND name NOT LIKE 'CO%' AND name NOT LIKE 'CTB%' AND name NOT LIKE 'DGB%' AND name NOT LIKE 'CTB%' AND name NOT LIKE 'R%WAL%' AND name NOT LIKE 'VLAGE%'))" +
            " AND (b.country <> '" + "DE" + "' OR b.name NOT LIKE 'BUNDEI%')" +
            " AND (b.country <> '" + "ES" + "' OR b.name NOT LIKE 'OBLIGACIONESEI%')" +
            " AND (b.country <> '" + "FR" + "' OR (name NOT LIKE 'D%' AND name NOT LIKE 'G%' AND name NOT LIKE 'ID%' AND name NOT LIKE 'OAT0%2505%' AND name NOT LIKE 'OAT%DEM' AND name NOT LIKE 'OATEI%' AND name NOT LIKE 'OATI%' AND name NOT LIKE 'OATPPMT%' AND name NOT LIKE 'R%' AND name NOT LIKE 'V%'))" +
            " AND (b.country <> '" + "GB" + "' OR b.name NOT LIKE 'UKTI%')" +
            " AND (b.country <> '" + "GR" + "' OR b.name NOT LIKE 'GGB TV%')" +
            " AND (b.country <> '" + "IT" + "' OR (b.name NOT LIKE 'BTP COUPON STRIP%' AND b.name NOT LIKE 'BTP ITALIA%' AND b.name NOT LIKE 'BTPI%' AND b.name NOT LIKE 'CCT%'))" +
            " AND (b.country <> '" + "PT" + "' OR (b.name NOT LIKE 'OTRV%' AND b.name NOT LIKE 'PORTUGAL TV%'))" +
            // Government bonds
            // ALXB - 2 = ALXP - 3 = XMLI - 3 = XOAM - 1 =
            " AND b.lastModifiedAt > CURDATE()" +
            " AND b.lastPrice <> 10000" +
            " AND b.market IN ('ETLX', 'MOTX', 'XAMS', 'XBRU', 'XLIS', 'XMOT', 'XPAR')" +
            " AND b.type = '" + "GOVERNMENT" + "'" +
            " AND b.yieldToMaturity > 0" +
            " GROUP BY b.country" +
            " ORDER BY b.country";

    public static final String VALID_BONDS_MARKETS = "SELECT b.market FROM bond b WHERE" +
            " b.coupon IS NOT NULL" +
            " AND b.country IS NOT NULL" +
            " AND (b.country <> '" + "AT" + "' OR (b.name NOT LIKE 'AUSTRIA%FRN' AND b.name NOT LIKE 'AUSTRIA%LINK'))" +
            " AND (b.country <> '" + "BE" + "' OR (name NOT LIKE 'BRUC%' AND name NOT LIKE 'CFB%' AND name NOT LIKE 'CO%' AND name NOT LIKE 'CTB%' AND name NOT LIKE 'DGB%' AND name NOT LIKE 'CTB%' AND name NOT LIKE 'R%WAL%' AND name NOT LIKE 'VLAGE%'))" +
            " AND (b.country <> '" + "DE" + "' OR b.name NOT LIKE 'BUNDEI%')" +
            " AND (b.country <> '" + "ES" + "' OR b.name NOT LIKE 'OBLIGACIONESEI%')" +
            " AND (b.country <> '" + "FR" + "' OR (name NOT LIKE 'D%' AND name NOT LIKE 'G%' AND name NOT LIKE 'ID%' AND name NOT LIKE 'OAT0%2505%' AND name NOT LIKE 'OAT%DEM' AND name NOT LIKE 'OATEI%' AND name NOT LIKE 'OATI%' AND name NOT LIKE 'OATPPMT%' AND name NOT LIKE 'R%' AND name NOT LIKE 'V%'))" +
            " AND (b.country <> '" + "GB" + "' OR b.name NOT LIKE 'UKTI%')" +
            " AND (b.country <> '" + "GR" + "' OR b.name NOT LIKE 'GGB TV%')" +
            " AND (b.country <> '" + "IT" + "' OR (b.name NOT LIKE 'BTP COUPON STRIP%' AND b.name NOT LIKE 'BTP ITALIA%' AND b.name NOT LIKE 'BTPI%' AND b.name NOT LIKE 'CCT%'))" +
            " AND (b.country <> '" + "PT" + "' OR (b.name NOT LIKE 'OTRV%' AND b.name NOT LIKE 'PORTUGAL TV%'))" +
            // Government bonds
            // ALXB - 2 = ALXP - 3 = XMLI - 3 = XOAM - 1 =
            " AND b.lastPrice <> 10000" +
            " AND b.market IN ('ETLX', 'MOTX', 'XAMS', 'XBRU', 'XLIS', 'XMOT', 'XPAR')" +
            " AND b.type = '" + "GOVERNMENT" + "'" +
            " AND b.yieldToMaturity > 0" +
            " GROUP BY b.market" +
            " ORDER BY b.market";
}
