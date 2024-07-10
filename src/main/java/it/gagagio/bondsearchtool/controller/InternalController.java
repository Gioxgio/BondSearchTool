package it.gagagio.bondsearchtool.controller;

import it.gagagio.bondsearchtool.service.ScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/internal")
@RequiredArgsConstructor
@RestController
public class InternalController {

    private final ScraperService scraperService;

    @PostMapping("/refresh")
    public int refresh() {

        return scraperService.refresh();
    }
}
