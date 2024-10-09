package it.gagagio.bondsearchtool.scheduler.runners;

import it.gagagio.bondsearchtool.data.entity.JobEntity;
import it.gagagio.bondsearchtool.data.repository.BondRepository;
import it.gagagio.bondsearchtool.model.JobType;
import it.gagagio.bondsearchtool.utils.CorrectionsUtils;
import it.gagagio.bondsearchtool.utils.JobConstants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplyCorrections implements JobRunner {

    private final CorrectionsUtils correctionsUtils;
    private final BondRepository bondRepository;

    @Override
    @Transactional
    public JobEntity run(JobEntity job) {

        try {
            var count = 0;
            val corrections = correctionsUtils.getCorrections();

            for (val correction : corrections) {
                if (correction.coupon() != null) {
                    val result = bondRepository.updateCoupon(correction.isin(), correction.coupon());
                    if (result != 1) {
                        log.error("Failed to apply correction to: {}", correction.isin());
                    } else {
                        count++;
                    }
                }
            }
            log.info("Updated {} records", count);
            bondRepository.flush();
        } catch (final Exception e) {
            log.error("Failed to apply corrections", e);
        }

        job.setNextExecutionDate(Instant.now().plusSeconds(JobConstants.TEN_YEARS));

        return job;
    }

    @Override
    public boolean match(final JobType jobType) {
        return JobType.APPLY_CORRECTIONS.equals(jobType);
    }
}
