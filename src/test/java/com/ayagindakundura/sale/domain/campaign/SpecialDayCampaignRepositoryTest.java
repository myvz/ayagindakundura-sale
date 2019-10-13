package com.ayagindakundura.sale.domain.campaign;

import com.ayagindakundura.sale.date.DateTimeUtil;
import com.ayagindakundura.sale.domain.campaign.SpecialDayCampaignRepository;
import com.ayagindakundura.sale.domain.product.Brand;
import com.ayagindakundura.sale.domain.campaign.SpecialDayCampaign;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SpecialDayCampaignRepositoryTest {

    @Autowired
    private SpecialDayCampaignRepository specialDayCampaignRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void it_should_get_specialDayCampaign() {
        //Given
        Brand adidas = createSampleBrand("Adidas");
        Date now = DateUtil.truncateTime(DateTimeUtil.now());
        SpecialDayCampaign sampleCampaign = createSampleCampaign(adidas, now, 10);

        //When
        Optional<SpecialDayCampaign> found = specialDayCampaignRepository.findSpecialDayCampaignByBrandAndCampaignDate(adidas, now);

        //Then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get()).isEqualTo(sampleCampaign);
    }


    private SpecialDayCampaign createSampleCampaign(Brand sampleBrand, Date campaignDate, int discountPercentage) {
        SpecialDayCampaign campaign = new SpecialDayCampaign();
        campaign.setBrand(sampleBrand);
        campaign.setCampaignDate(campaignDate);
        campaign.setCampaignName("Kış");
        campaign.setDiscountPercentage(discountPercentage);
        campaign = testEntityManager.persist(campaign);
        testEntityManager.flush();
        testEntityManager.clear();
        return campaign;
    }

    private Brand createSampleBrand(String name) {
        Brand brand = new Brand();
        brand.setName(name);
        brand = testEntityManager.persist(brand);
        return brand;
    }
}