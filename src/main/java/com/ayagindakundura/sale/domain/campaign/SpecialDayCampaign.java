package com.ayagindakundura.sale.domain.campaign;

import com.ayagindakundura.sale.domain.product.Product;
import com.ayagindakundura.sale.domain.product.Brand;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;

@Entity
public class SpecialDayCampaign implements Campaign {

    private static final BigDecimal DIVISOR = BigDecimal.valueOf(100);

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(nullable = false)
    private Integer discountPercentage;

    @Column(nullable = false)
    private String campaignName;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date campaignDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Date getCampaignDate() {
        return campaignDate;
    }

    public void setCampaignDate(Date campaignDate) {
        this.campaignDate = campaignDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialDayCampaign that = (SpecialDayCampaign) o;
        return brand.equals(that.brand) &&
                campaignDate.equals(that.campaignDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, campaignDate);
    }

    @Override
    public BigDecimal applyDiscount(Product product) {
        return calculateDiscount(product.getPrice());
    }

    private BigDecimal calculateDiscount(BigDecimal price) {
        return price.subtract(
                price.multiply(BigDecimal.valueOf(discountPercentage)).divide(DIVISOR, RoundingMode.HALF_UP)
        );
    }
}
