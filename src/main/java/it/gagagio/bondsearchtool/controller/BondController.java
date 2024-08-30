package it.gagagio.bondsearchtool.controller;

import it.gagagio.bondsearchtool.data.entity.BondEntity;
import it.gagagio.bondsearchtool.service.BondService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/bond")
@RequiredArgsConstructor
@RestController
public class BondController {

    private final BondService bondService;

    @GetMapping
    public List<BondEntity> getBonds() {

        return bondService.getBonds();
    }
}
