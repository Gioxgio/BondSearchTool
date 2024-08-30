package it.gagagio.bondsearchtool.scheduler.runners;

import it.gagagio.bondsearchtool.data.entity.JobEntity;
import it.gagagio.bondsearchtool.model.JobType;

public interface JobRunner {

    JobEntity run(JobEntity job);

    boolean match(final JobType jobType);
}
