package com.ayagindakundura.sale.domain.campaign;

import com.ayagindakundura.sale.date.DateTimeUtil;
import com.ayagindakundura.sale.domain.product.Brand;
import com.ayagindakundura.sale.domain.product.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CampaignServiceTest {

    @Mock
    private CampaignFactory campaignFactory;

    @InjectMocks
    private CampaignService campaignService;

    @Before
    public void setUp() throws Exception {
        DateTimeUtil.freezeDate();
    }

    @After
    public void tearDown() throws Exception {
        DateTimeUtil.unFreezeDate();
    }

    @Test
    public void should_calculate_discounted_price_when_current_date_is_in_campaign_season() {
        //Given
        Date now = DateTimeUtil.getTruncatedDate();
        BigDecimal listingPrice = BigDecimal.valueOf(250);
        BigDecimal discountedPrice = BigDecimal.valueOf(140);

        Product product = new Product();
        product.setId(1L);
        product.setPrice(listingPrice);

        SeasonalCampaign campaign = new SeasonalCampaign();
        campaign.setPrice(discountedPrice);
        when(campaignFactory.getApplicableCampaign(product)).thenReturn(campaign);

        //when
        BigDecimal price = campaignService.getDiscountedPrice(product);

        //Then
        assertThat(price).isEqualByComparingTo(discountedPrice);
    }

    @Test
    public void should_calculate_discounted_price_when_current_date_is_on_special_day() {
        //Given
        Date now = DateTimeUtil.getTruncatedDate();
        BigDecimal listingPrice = BigDecimal.valueOf(250);
        BigDecimal discountedPrice = BigDecimal.valueOf(225);

        Product product = new Product();
        product.setId(1L);
        Brand brand = new Brand();
        brand.setName("Adidas");
        product.setBrand(brand);
        product.setPrice(listingPrice);

        SpecialDayCampaign specialDayCampaign = new SpecialDayCampaign();
        specialDayCampaign.setDiscountPercentage(10);
        when(campaignFactory.getApplicableCampaign(product)).thenReturn(specialDayCampaign);

        //when
        BigDecimal price = campaignService.getDiscountedPrice(product);

        //Then
        assertThat(price).isEqualByComparingTo(discountedPrice);
    }
}