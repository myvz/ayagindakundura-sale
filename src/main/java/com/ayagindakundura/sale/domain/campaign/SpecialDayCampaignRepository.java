package com.ayagindakundura.sale.domain.campaign;

import com.ayagindakundura.sale.domain.product.Brand;
import com.ayagindakundura.sale.domain.campaign.SpecialDayCampaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface SpecialDayCampaignRepository extends JpaRepository<SpecialDayCampaign, Long> {

    Optional<SpecialDayCampaign> findSpecialDayCampaignByBrandAndCampaignDate(Brand brand, Date campaignDate);
}
