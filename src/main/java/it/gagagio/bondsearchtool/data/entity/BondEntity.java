package it.gagagio.bondsearchtool.data.entity;


import it.gagagio.bondsearchtool.euronext.model.BondIssuerRegion;
import it.gagagio.bondsearchtool.model.BondIssuerCountry;
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
@Entity(name = "bond")
@EntityListeners({AuditingEntityListener.class})
@Getter
@NoArgsConstructor
@Setter
public class BondEntity {

    @Id
    private String isin;
    private String name;
    @Id
    private String market;
    private Instant maturityAt;
    private Integer coupon;
    private Integer lastPrice;
    @Enumerated(EnumType.STRING)
    private BondIssuerCountry country;
    @Enumerated(EnumType.STRING)
    private BondIssuerRegion region;
    @Column(name = "yield_to_maturity")
    private Integer yieldToMaturity;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant lastModifiedAt;
}