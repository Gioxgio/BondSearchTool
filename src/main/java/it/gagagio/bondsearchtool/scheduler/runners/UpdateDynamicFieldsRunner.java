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
public class UpdateDynamicFieldsRunner implements JobRunner {

    private final BondService bondService;

    @Override
    public JobEntity run(JobEntity job) {

        val pageSize = 100;

        val savedBondsSize = bondService.calculateYieldToMaturity(pageSize);

        val nextExecutionInterval = savedBondsSize < pageSize ? JobConstants.ONE_DAY : JobConstants.FIVE_SECONDS;

        job.setNextExecutionDate(Instant.now().plusSeconds(nextExecutionInterval));

        return job;
    }

    @Override
    public boolean match(final JobType jobType) {
        return JobType.UPDATE_DYNAMIC_FIELDS.equals(jobType);
    }
}
