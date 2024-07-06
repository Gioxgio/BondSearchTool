package it.gagagio.bondsearchtool.controller;

import it.gagagio.bondsearchtool.ScraperService;
import it.gagagio.bondsearchtool.controller.model.RefreshRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/internal")
@RequiredArgsConstructor
@RestController
public class InternalController {

    private final ScraperService scraperService;

    @PostMapping("/refresh")
    public int refresh(@RequestBody final RefreshRequest request) {

        return scraperService.refresh(request.bondIssuerRegion(), request.bondIssuerType());
    }
}
