package com.ayagindakundura.sale.domain.campaign;

import com.ayagindakundura.sale.domain.product.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CampaignService {

    private CampaignFactory campaignFactory;

    public CampaignService(CampaignFactory campaignFactory) {
        this.campaignFactory = campaignFactory;
    }

    public BigDecimal getDiscountedPrice(Product product) {
        return campaignFactory.getApplicableCampaign(product)
                .applyDiscount(product);
    }
}
