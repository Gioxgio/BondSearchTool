package it.gagagio.bondsearchtool.scheduler.runners;

import it.gagagio.bondsearchtool.data.entity.JobEntity;
import it.gagagio.bondsearchtool.model.JobType;
import it.gagagio.bondsearchtool.scheduler.utils.JobConstants;
import it.gagagio.bondsearchtool.service.ScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class UpdateListRunner implements JobRunner {

    private final ScraperService scraperService;

    @Override
    public JobEntity run(JobEntity job) {

        scraperService.refresh();

        job.setNextExecutionDate(Instant.now().plus(JobConstants.ONE_DAY, ChronoUnit.SECONDS));

        return job;
    }

    @Override
    public boolean match(final JobType jobType) {
        return JobType.UPDATE_LIST.equals(jobType);
    }
}
