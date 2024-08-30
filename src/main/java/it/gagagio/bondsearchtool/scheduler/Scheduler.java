package it.gagagio.bondsearchtool.scheduler;

import it.gagagio.bondsearchtool.data.entity.JobEntity;
import it.gagagio.bondsearchtool.data.repository.JobRepository;
import it.gagagio.bondsearchtool.scheduler.runners.JobRunner;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final JobRepository jobRepository;
    private final List<JobRunner> jobRunners;

    @Scheduled(fixedDelay = 5000)
    public void executeJobs() {

        val jobs = jobRepository.findAllByNextExecutionDateBefore(Instant.now());

        jobs.forEach(this::processJob);
    }

    private void processJob(final JobEntity job) {
        val runnerOptional = jobRunners.stream().filter(j -> j.match(job.getType())).findFirst();
        if (runnerOptional.isEmpty()) {
            return;
        }
        val runner = runnerOptional.get();

        System.out.println("Running job " + job.getId() + " " + job.getType());
        val updatedJob = runner.run(job);
        updatedJob.setLastExecutionDate(Instant.now());
        jobRepository.save(updatedJob);
    }
}
