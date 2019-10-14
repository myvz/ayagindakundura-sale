package com.ayagindakundura.sale.domain.product;

import com.ayagindakundura.sale.domain.campaign.CampaignService;
import com.ayagindakundura.sale.domain.stock.OutOfStockException;
import com.ayagindakundura.sale.domain.stock.StockChangedEvent;
import com.ayagindakundura.sale.domain.stock.StockChangedEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private CampaignService campaignService;

    private StockChangedEventPublisher stockChangedEventPublisher;

    public ProductService(ProductRepository productRepository,
                          CampaignService campaignService,
                          StockChangedEventPublisher stockChangedEventPublisher) {
        this.productRepository = productRepository;
        this.campaignService = campaignService;
        this.stockChangedEventPublisher = stockChangedEventPublisher;
    }

    public Optional<ProductDto> findProduct(Long id) {
        return productRepository.findById(id)
                .map(this::convert);
    }

    public List<ProductDto> findProductByBrandName(String brandName) {
        return productRepository.findByBrand_NameStartingWithIgnoreCase(brandName)
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public void reduceStock(Long productId, Integer quantity) {
        int count = productRepository.reduceStock(productId, quantity);
        if (count == 0) {
            throw new OutOfStockException();
        }
        stockChangedEventPublisher.publish(new StockChangedEvent(productId));
    }

    public ProductDto convert(Product product) {
        BigDecimal discountedPrice = campaignService.getDiscountedPrice(product);
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setBrand(product.getBrand().getName());
        dto.setColor(product.getColor());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setDiscountedPrice(discountedPrice);
        return dto;
    }
}
