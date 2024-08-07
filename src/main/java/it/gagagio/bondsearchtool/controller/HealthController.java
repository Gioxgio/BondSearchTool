package it.gagagio.bondsearchtool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HealthController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}