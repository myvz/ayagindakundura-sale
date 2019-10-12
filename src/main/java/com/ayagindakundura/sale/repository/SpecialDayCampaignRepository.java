package com.ayagindakundura.sale.repository;

import com.ayagindakundura.sale.domain.Brand;
import com.ayagindakundura.sale.domain.SpecialDayCampaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface SpecialDayCampaignRepository extends JpaRepository<SpecialDayCampaign, Long> {

    Optional<SpecialDayCampaign> findSpecialDayCampaignByBrandAndCampaignDate(Brand brand, Date campaignDate);
}
