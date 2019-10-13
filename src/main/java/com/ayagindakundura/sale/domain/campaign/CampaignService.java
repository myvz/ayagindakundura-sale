package com.ayagindakundura.sale.domain.campaign;

import com.ayagindakundura.sale.date.DateTimeUtil;
import com.ayagindakundura.sale.domain.product.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class CampaignService {

    private final SeasonalCampaignRepository seasonalCampaignRepository;

    private final SpecialDayCampaignRepository specialDayCampaignRepository;

    public CampaignService(SeasonalCampaignRepository seasonalCampaignRepository,
                           SpecialDayCampaignRepository specialDayCampaignRepository) {
        this.seasonalCampaignRepository = seasonalCampaignRepository;
        this.specialDayCampaignRepository = specialDayCampaignRepository;
    }

    public BigDecimal getDiscountedPrice(Product product) {
        Date currentDate = DateTimeUtil.getTruncatedDate();
        return getSpecialDayCampaign(product, currentDate)
                .map(campaign -> campaign.applyDiscount(product))
                .orElseGet(() -> getSeasonalCampaign(product, currentDate)
                        .map(campaign -> campaign.applyDiscount(product))
                        .orElse(product.getPrice()));
    }

    private Optional<SeasonalCampaign> getSeasonalCampaign(Product product, Date currentDate) {
        return seasonalCampaignRepository.findSeasonalCampaignByProduct(product, currentDate);
    }

    private Optional<SpecialDayCampaign> getSpecialDayCampaign(Product product, Date currentDate) {
        return specialDayCampaignRepository.findSpecialDayCampaignByBrandAndCampaignDate(product.getBrand(), currentDate);
    }
}
