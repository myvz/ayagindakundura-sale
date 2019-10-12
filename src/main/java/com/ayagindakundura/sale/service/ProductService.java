package com.ayagindakundura.sale.service;

import com.ayagindakundura.sale.converter.ProductConverter;
import com.ayagindakundura.sale.domain.Product;
import com.ayagindakundura.sale.dto.ProductDto;
import com.ayagindakundura.sale.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private ProductConverter productConverter;

    private CampaignService campaignService;

    public ProductService(ProductRepository productRepository,
                          ProductConverter productConverter,
                          CampaignService campaignService) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.campaignService = campaignService;
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
}
