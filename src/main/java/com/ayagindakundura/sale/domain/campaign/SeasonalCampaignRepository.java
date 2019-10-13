package com.ayagindakundura.sale.domain.campaign;

import com.ayagindakundura.sale.domain.product.Product;
import com.ayagindakundura.sale.domain.campaign.SeasonalCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface SeasonalCampaignRepository extends JpaRepository<SeasonalCampaign, Long> {

    @Query("Select s from SeasonalCampaign s where s.product=:product and :currentDate between s.startDate and s.endDate")
    Optional<SeasonalCampaign> findSeasonalCampaignByProduct(Product product, Date currentDate);
}
