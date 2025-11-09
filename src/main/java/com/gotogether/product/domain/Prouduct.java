package com.gotogether.product.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=150)
    private String name;

    @Column(length=80)
    private String country;

    @Column(length=80)
    private String region;

    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    private Double rating;

    @Column(nullable=false)
    private LocalDateTime createdAt = LocalDateTime.now();

    protected Product() {}
    public Product(String name, String country, String region, BigDecimal price, Double rating) {
        this.name = name; this.country = country; this.region = region;
        this.price = price; this.rating = rating;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getRegion() { return region; }
    public BigDecimal getPrice() { return price; }
    public Double getRating() { return rating; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
