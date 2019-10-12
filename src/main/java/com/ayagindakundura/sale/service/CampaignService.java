package com.ayagindakundura.sale.service;

import com.ayagindakundura.sale.date.DateTimeUtil;
import com.ayagindakundura.sale.domain.Product;
import com.ayagindakundura.sale.domain.SeasonalCampaign;
import com.ayagindakundura.sale.repository.SeasonalCampaignRepository;
import com.ayagindakundura.sale.repository.SpecialDayCampaignRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Optional;

@Service
public class CampaignService {

    private static final BigDecimal DIVISOR = BigDecimal.valueOf(100);

    private final SeasonalCampaignRepository seasonalCampaignRepository;

    private final SpecialDayCampaignRepository specialDayCampaignRepository;

    public CampaignService(SeasonalCampaignRepository seasonalCampaignRepository,
                           SpecialDayCampaignRepository specialDayCampaignRepository) {
        this.seasonalCampaignRepository = seasonalCampaignRepository;
        this.specialDayCampaignRepository = specialDayCampaignRepository;
    }

    public BigDecimal getDiscountedPrice(Product product) {
        Date currentDate = DateTimeUtil.getTruncatedDate();
        return applySpecialDayCampaign(product, currentDate)
                .orElseGet(() ->
                        applySeasonalCampaign(product, currentDate)
                                .orElse(product.getPrice()));
    }

    private Optional<BigDecimal> applySeasonalCampaign(Product product, Date currentDate) {
        return seasonalCampaignRepository.findSeasonalCampaignByProduct(product, currentDate)
                .map(SeasonalCampaign::getPrice);
    }

    private Optional<BigDecimal> applySpecialDayCampaign(Product product, Date currentDate) {
        return specialDayCampaignRepository.findSpecialDayCampaignByBrandAndCampaignDate(product.getBrand(), currentDate)
                .map(specialDayCampaign -> calculateDiscount(product.getPrice(), specialDayCampaign.getDiscountPercentage()));
    }

    private BigDecimal calculateDiscount(BigDecimal price, Integer discountPercentage) {
        return price.subtract(
                price.multiply(BigDecimal.valueOf(discountPercentage)).divide(DIVISOR, RoundingMode.HALF_UP)
        );
    }
}
