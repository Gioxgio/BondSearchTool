package it.gagagio.bondsearchtool.scheduler.runners;

import it.gagagio.bondsearchtool.data.entity.JobEntity;
import it.gagagio.bondsearchtool.model.JobType;
import it.gagagio.bondsearchtool.scheduler.utils.JobConstants;
import it.gagagio.bondsearchtool.service.BondService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UpdateStaticFieldsRunner implements JobRunner {

    private final BondService bondService;

    @Override
    public JobEntity run(JobEntity job) {

        val savedBondsSize = bondService.updateStaticFields(JobConstants.PAGE_SIZE);

        val nextExecutionInterval = savedBondsSize < JobConstants.PAGE_SIZE ? JobConstants.ONE_DAY : JobConstants.TEN_SECONDS;

        job.setNextExecutionDate(Instant.now().plusSeconds(nextExecutionInterval));

        return job;
    }

    @Override
    public boolean match(final JobType jobType) {
        return JobType.UPDATE_STATIC_FIELDS.equals(jobType);
    }
}
