package com.gotogether.product.domain;

import com.gotogether.product.api.product.dto.ProductSummaryDto;
import com.gotogether.product.api.product.dto.SearchRequest;
import org.springframework.data.domain.Page;

public interface ProductRepositoryCustom {
    Page<ProductSummaryDto> search(SearchRequest req);
}