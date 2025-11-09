package com.gotogether.product.api.dto;

import java.math.BigDecimal;

public record ProductSummaryDto(
        Long id,
        String name,
        String country,
        String region,
        BigDecimal price,
        Double rating
) {}