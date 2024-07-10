package it.gagagio.bondsearchtool.scheduler;

import it.gagagio.bondsearchtool.service.BondService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final BondService bondService;

    @Scheduled(fixedDelay = 10000)
    public void getYieldToMaturity() {

        bondService.calculateYieldToMaturity();
    }
}
