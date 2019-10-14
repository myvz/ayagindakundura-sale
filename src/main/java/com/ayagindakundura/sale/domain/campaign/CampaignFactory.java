package com.ayagindakundura.sale.domain.campaign;

import com.ayagindakundura.sale.date.DateTimeUtil;
import com.ayagindakundura.sale.domain.product.Product;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CampaignFactory {

    private SeasonalCampaignRepository seasonalCampaignRepository;

    private SpecialDayCampaignRepository specialDayCampaignRepository;

    public CampaignFactory(SeasonalCampaignRepository seasonalCampaignRepository,
                           SpecialDayCampaignRepository specialDayCampaignRepository) {
        this.seasonalCampaignRepository = seasonalCampaignRepository;
        this.specialDayCampaignRepository = specialDayCampaignRepository;
    }

    public Campaign getApplicableCampaign(Product product) {
        Date currentDate = DateTimeUtil.getTruncatedDate();
        return specialDayCampaignRepository.findSpecialDayCampaignByBrandAndCampaignDate(product.getBrand(), currentDate)
                .orElseGet(() ->
                        seasonalCampaignRepository.findSeasonalCampaignByProduct(product, currentDate)
                                .orElse(Product::getPrice)
                );
    }
}
