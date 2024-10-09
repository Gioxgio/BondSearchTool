package it.gagagio.bondsearchtool.scheduler.runners;

import it.gagagio.bondsearchtool.data.entity.JobEntity;
import it.gagagio.bondsearchtool.model.JobType;
import it.gagagio.bondsearchtool.utils.JobConstants;
import it.gagagio.bondsearchtool.service.ScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UpdateListRunner implements JobRunner {

    private final ScraperService scraperService;

    @Override
    public JobEntity run(JobEntity job) {

        scraperService.updateBondList();

        job.setNextExecutionDate(Instant.now().plusSeconds(JobConstants.ONE_DAY));

        return job;
    }

    @Override
    public boolean match(final JobType jobType) {
        return JobType.UPDATE_LIST.equals(jobType);
    }
}
