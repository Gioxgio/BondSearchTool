package it.gagagio.bondsearchtool.controller;

import it.gagagio.bondsearchtool.controller.mapper.BondResponseMapper;
import it.gagagio.bondsearchtool.controller.response.BondResponse;
import it.gagagio.bondsearchtool.controller.response.CountryResponse;
import it.gagagio.bondsearchtool.service.BondService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/bond")
@RequiredArgsConstructor
@RestController
public class BondController {

    private final BondService bondService;
    private final BondResponseMapper bondResponseMapper;

    @CrossOrigin
    @GetMapping
    public List<BondResponse> getBonds() {
        return bondService.getBonds().stream().map(bondResponseMapper::from).toList();
    }

    @CrossOrigin
    @GetMapping("/countries")
    public List<CountryResponse> getCountires() {
        return bondService.getCountries().stream().map(bondResponseMapper::from).toList();
    }
}
