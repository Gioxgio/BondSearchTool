package it.gagagio.bondsearchtool.data.entity;


import it.gagagio.bondsearchtool.euronext.model.BondIssuerRegion;
import it.gagagio.bondsearchtool.model.BondIssuerCountry;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class BondEntity {

    @Id
    private String id;
    private String name;
    private String market;
    private Instant maturityAt;
    private int coupon;
    private int lastPrice;
    @Enumerated(EnumType.STRING)
    private BondIssuerCountry country;
    @Enumerated(EnumType.STRING)
    private BondIssuerRegion region;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant lastModifiedAt;
}