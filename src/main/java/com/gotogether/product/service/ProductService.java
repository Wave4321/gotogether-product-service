package com.gotogether.product.service;

import com.gotogether.product.api.product.dto.*;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<ProductSummaryDto> search(SearchRequest req);
    ProductDetailDto getDetail(Long id);
}