package it.gagagio.bondsearchtool.scheduler.runners;

import it.gagagio.bondsearchtool.data.entity.JobEntity;
import it.gagagio.bondsearchtool.model.JobType;
import it.gagagio.bondsearchtool.scheduler.utils.JobConstants;
import it.gagagio.bondsearchtool.service.BondService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class UpdateStaticFieldsRunner implements JobRunner {

    private final BondService bondService;

    @Override
    public JobEntity run(JobEntity job) {

        val pageSize = 100;

        val savedBondsSize = bondService.enrichBonds(100);

        val nextExecutionInterval = savedBondsSize < pageSize ? JobConstants.ONE_DAY : JobConstants.FIVE_SECONDS;

        job.setNextExecutionDate(Instant.now().plus(nextExecutionInterval, ChronoUnit.SECONDS));

        return job;
    }

    @Override
    public boolean match(final JobType jobType) {
        return JobType.UPDATE_STATIC_FIELDS.equals(jobType);
    }
}
