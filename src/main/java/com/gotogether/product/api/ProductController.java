package com.gotogether.product.api;

import com.gotogether.product.api.product.dto.*;
import com.gotogether.product.common.ApiResponse;
import com.gotogether.product.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/search")
    public ApiResponse<Page<ProductSummaryDto>> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort
    ) {
        SearchRequest req = new SearchRequest(q, page, size, sort);
        return ApiResponse.ok(service.search(req));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductDetailDto> detail(@PathVariable Long id) {
        return ApiResponse.ok(service.getDetail(id));
    }
}
