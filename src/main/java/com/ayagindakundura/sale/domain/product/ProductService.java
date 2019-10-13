package com.ayagindakundura.sale.domain.product;

import com.ayagindakundura.sale.domain.campaign.CampaignService;
import com.ayagindakundura.sale.domain.stock.StockChangedEvent;
import com.ayagindakundura.sale.domain.stock.StockChangedEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private ProductConverter productConverter;

    private CampaignService campaignService;

    private StockChangedEventPublisher stockChangedEventPublisher;

    public ProductService(ProductRepository productRepository,
                          ProductConverter productConverter,
                          CampaignService campaignService,
                          StockChangedEventPublisher stockChangedEventPublisher) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.campaignService = campaignService;
        this.stockChangedEventPublisher = stockChangedEventPublisher;
    }

    public Optional<ProductDto> findProduct(Long id) {
        return productRepository.findById(id)
                .map(this::convert);
    }

    public List<ProductDto> findProductByBrandName(String brandName) {
        return productRepository.findByBrand_Name(brandName)
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private ProductDto convert(Product product) {
        return productConverter.convert(product, campaignService.getDiscountedPrice(product));
    }

    @Transactional
    public void reduceStock(Long productId, Long quantity) {
        productRepository.reduceStock(productId, quantity);
        stockChangedEventPublisher.publish(new StockChangedEvent(productId));
    }
}
