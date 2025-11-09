package com.gotogether.product.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDetailDto(
        Long id,
        String name,
        String country,
        String region,
        BigDecimal price,
        Double rating,
        LocalDateTime createdAt
) {}
