package it.gagagio.bondsearchtool.controller.mapper;

import it.gagagio.bondsearchtool.controller.response.BondResponse;
import it.gagagio.bondsearchtool.controller.response.CountryResponse;
import it.gagagio.bondsearchtool.controller.response.MarketResponse;
import it.gagagio.bondsearchtool.data.entity.BondEntity;
import it.gagagio.bondsearchtool.model.BondCountry;
import it.gagagio.bondsearchtool.model.BondMarket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class BondResponseMapper {

    public BondResponse from(final BondEntity bond) {
        return BondResponse.builder()
                .isin(bond.getIsin())
                .name(bond.getName())
                .market(from(bond.getMarket()))
                .maturityAt(bond.getMaturityAt())
                .perpetual(bond.isPerpetual())
                .coupon(from(bond.getCoupon()))
                .lastPrice(from(bond.getLastPrice()))
                .country(from(bond.getCountry()))
                .yieldToMaturity(from(bond.getYieldToMaturity()))
                .type(bond.getType())
                .build();
    }

    public BigDecimal from(final int number) {
        return BigDecimal.valueOf(number).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_DOWN);
    }

    public CountryResponse from(final BondCountry country) {
        return new CountryResponse(country.name(), country.getName());
    }

    public MarketResponse from(final BondMarket market) {
        return new MarketResponse(market.name(), market.getName());
    }
}
