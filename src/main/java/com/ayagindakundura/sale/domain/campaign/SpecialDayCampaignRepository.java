package com.ayagindakundura.sale.domain.campaign;

import com.ayagindakundura.sale.domain.product.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.Date;
import java.util.Optional;

public interface SpecialDayCampaignRepository extends JpaRepository<SpecialDayCampaign, Long> {

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    Optional<Campaign> findSpecialDayCampaignByBrandAndCampaignDate(Brand brand, Date campaignDate);
}
