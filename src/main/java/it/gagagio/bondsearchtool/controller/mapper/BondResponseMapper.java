package it.gagagio.bondsearchtool.controller.mapper;

import it.gagagio.bondsearchtool.controller.response.CountryResponse;
import it.gagagio.bondsearchtool.model.BondCountry;
import org.springframework.stereotype.Component;

@Component
public class BondResponseMapper {

    public CountryResponse from(final BondCountry bondCountry) {
        return new CountryResponse(bondCountry.name(), bondCountry.getName());
    }
}
