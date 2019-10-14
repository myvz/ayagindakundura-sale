package com.ayagindakundura.sale.domain.campaign;

import com.ayagindakundura.sale.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.Date;
import java.util.Optional;

public interface SeasonalCampaignRepository extends JpaRepository<SeasonalCampaign, Long> {

    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @Query("Select s from SeasonalCampaign s where s.product=:product and :currentDate between s.startDate and s.endDate")
    Optional<Campaign> findSeasonalCampaignByProduct(Product product, Date currentDate);
}
