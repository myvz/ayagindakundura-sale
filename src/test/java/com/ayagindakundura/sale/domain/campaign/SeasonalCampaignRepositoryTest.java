package com.ayagindakundura.sale.domain.campaign;

import com.ayagindakundura.sale.domain.campaign.SeasonalCampaignRepository;
import com.ayagindakundura.sale.domain.product.Brand;
import com.ayagindakundura.sale.domain.product.Product;
import com.ayagindakundura.sale.domain.campaign.Season;
import com.ayagindakundura.sale.domain.campaign.SeasonalCampaign;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SeasonalCampaignRepositoryTest {

    @Autowired
    private SeasonalCampaignRepository seasonalCampaignRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    public void it_should_get_seasonal_campaign_by_product_and_current_date_between_start_date_and_end_date() {
        Product sampleProduct = createSampleProduct();
        SeasonalCampaign sampleCampaign = createSampleCampaign(sampleProduct, DateUtil.truncateTime(DateUtil.yesterday()), DateUtil.truncateTime(DateUtil.tomorrow()));
        Optional<SeasonalCampaign> found = seasonalCampaignRepository.findSeasonalCampaignByProduct(sampleProduct, DateUtil.now());
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get()).isEqualTo(sampleCampaign);
    }

    private SeasonalCampaign createSampleCampaign(Product sampleProduct, Date startDate, Date endDate) {
        SeasonalCampaign campaign = new SeasonalCampaign();
        campaign.setProduct(sampleProduct);
        campaign.setPrice(BigDecimal.TEN);
        campaign.setStartDate(startDate);
        campaign.setEndDate(endDate);
        campaign.setSeason(Season.AUTUMN);
        campaign = testEntityManager.persist(campaign);
        testEntityManager.flush();
        testEntityManager.clear();
        return campaign;
    }

    private Product createSampleProduct() {
        Brand brand = new Brand();
        brand.setName("Adidas");
        brand = testEntityManager.persist(brand);

        Product product = new Product();
        product.setBrand(brand);
        product.setColor("Yellow");
        product.setImageUrl("/yellow-adidas");
        product.setPrice(BigDecimal.TEN);
        product.setStockQuantity(10L);
        return testEntityManager.persist(product);
    }

}