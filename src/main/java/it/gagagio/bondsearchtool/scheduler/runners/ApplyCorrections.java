package it.gagagio.bondsearchtool.scheduler.runners;

import it.gagagio.bondsearchtool.data.entity.JobEntity;
import it.gagagio.bondsearchtool.data.repository.BondRepository;
import it.gagagio.bondsearchtool.model.Correction;
import it.gagagio.bondsearchtool.model.JobType;
import it.gagagio.bondsearchtool.service.BondService;
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

    private final BondRepository bondRepository;
    private final BondService bondService;
    private final CorrectionsUtils correctionsUtils;

    @Override
    @Transactional
    public JobEntity run(JobEntity job) {

        try {
            val corrections = correctionsUtils.getCorrections();
            val isins = corrections.stream().map(Correction::isin).toList();
            val bonds = bondRepository.findByIsinIn(isins);

            for (val bond : bonds) {
                val correctionOptional = corrections.stream().filter(c -> c.isin().equals(bond.getIsin())).findFirst();
                if (correctionOptional.isEmpty()) {
                    continue;
                }
                val correction = correctionOptional.get();

                if (correction.coupon() != null) {
                    bond.setCoupon(correction.coupon());
                }

                bondService.updateDynamicFields(bond);
            }
            bondRepository.flush();
            log.info("Updated {} records", bonds.size());
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
