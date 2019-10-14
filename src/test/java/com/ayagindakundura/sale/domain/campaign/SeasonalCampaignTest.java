package com.ayagindakundura.sale.domain.campaign;

import com.ayagindakundura.sale.domain.product.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


public class SeasonalCampaignTest {

    @Test
    public void should_apply_discount() {
        //Given
        BigDecimal listingPrice = BigDecimal.valueOf(150);
        SpecialDayCampaign specialDayCampaign = new SpecialDayCampaign();
        specialDayCampaign.setDiscountPercentage(10);

        Product product = new Product();
        product.setPrice(listingPrice);
        BigDecimal discountedPrice = specialDayCampaign.applyDiscount(product);

        assertThat(discountedPrice).isEqualByComparingTo(BigDecimal.valueOf(135));
    }

}