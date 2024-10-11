package it.gagagio.bondsearchtool.data.repository;

import it.gagagio.bondsearchtool.data.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface JobRepository extends JpaRepository<JobEntity, String> {

    List<JobEntity> findAllByNextExecutionDateBeforeOrderByNextExecutionDate(Instant date);
}
