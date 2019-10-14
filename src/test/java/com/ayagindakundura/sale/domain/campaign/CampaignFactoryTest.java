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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CampaignFactoryTest {

    @InjectMocks
    private CampaignFactory campaignFactory;

    @Mock
    private SeasonalCampaignRepository seasonalCampaignRepository;

    @Mock
    private SpecialDayCampaignRepository specialDayCampaignRepository;

    @Before
    public void setUp() {
        DateTimeUtil.freezeDate();
    }

    @After
    public void tearDown() {
        DateTimeUtil.unFreezeDate();
    }

    @Test
    public void it_should_get_special_day_campaign_when_available() {
        //Given
        Brand brand = new Brand();
        Product product = new Product();
        product.setBrand(brand);
        Date truncatedDate = DateTimeUtil.getTruncatedDate();
        SpecialDayCampaign value = new SpecialDayCampaign();
        when(specialDayCampaignRepository.findSpecialDayCampaignByBrandAndCampaignDate(brand, truncatedDate))
                .thenReturn(Optional.of(value));

        //When
        Campaign applicableCampaign = campaignFactory.getApplicableCampaign(product);

        //Then
        assertThat(applicableCampaign).isEqualTo(value);
    }

    @Test
    public void it_should_get_seasonal_day_campaign_when_available() {
        //Given
        Brand brand = new Brand();
        Product product = new Product();
        product.setBrand(brand);
        Date truncatedDate = DateTimeUtil.getTruncatedDate();
        SeasonalCampaign value = new SeasonalCampaign();
        when(specialDayCampaignRepository.findSpecialDayCampaignByBrandAndCampaignDate(brand, truncatedDate))
                .thenReturn(Optional.empty());

        when(seasonalCampaignRepository.findSeasonalCampaignByProduct(product, truncatedDate))
                .thenReturn(Optional.of(value));
        //When
        Campaign applicableCampaign = campaignFactory.getApplicableCampaign(product);

        //Then
        assertThat(applicableCampaign).isEqualTo(value);
    }

    @Test
    public void it_should_get_empty_campaign_when_available() {
        //Given
        Brand brand = new Brand();
        Product product = new Product();
        product.setBrand(brand);
        product.setPrice(BigDecimal.ONE);
        Date truncatedDate = DateTimeUtil.getTruncatedDate();
        when(specialDayCampaignRepository.findSpecialDayCampaignByBrandAndCampaignDate(brand, truncatedDate))
                .thenReturn(Optional.empty());

        when(seasonalCampaignRepository.findSeasonalCampaignByProduct(product, truncatedDate))
                .thenReturn(Optional.empty());
        //When
        Campaign applicableCampaign = campaignFactory.getApplicableCampaign(product);

        Campaign expectedCampaign = Product::getPrice;

        //Then
        assertThat(applicableCampaign.applyDiscount(product)).isEqualByComparingTo(expectedCampaign.applyDiscount(product));
    }

}