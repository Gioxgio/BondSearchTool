package it.gagagio.bondsearchtool.data.entity;


import it.gagagio.bondsearchtool.model.JobType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;


@AllArgsConstructor
@Builder
@Entity(name = "job")
@EntityListeners({AuditingEntityListener.class})
@Getter
@NoArgsConstructor
@Setter
public class JobEntity {

    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private JobType type;
    @Column(name = "last_execution_date")
    private Instant lastExecutionDate;
    @Column(name = "next_execution_date")
    private Instant nextExecutionDate;
    private String data;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant lastModifiedAt;
}