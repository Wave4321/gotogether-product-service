package com.gotogether.product.service.product;

import com.gotogether.product.api.product.dto.*;
import com.gotogether.product.domain.product.Product;
import com.gotogether.product.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    @Override
    public Page<ProductSummaryDto> search(SearchRequest req) {
        Pageable pageable = PageRequest.of(req.pageOrDefault(), req.sizeOrDefault(), Sort.by(Sort.Direction.DESC, "createdAt"));
        String q = req.q() == null ? "" : req.q().trim();
        Page<Product> page = repo.findByNameContainingIgnoreCaseOrCountryContainingIgnoreCaseOrRegionContainingIgnoreCase(
                q, q, q, pageable
        );
        return page.map(p -> new ProductSummaryDto(
                p.getId(), p.getName(), p.getCountry(), p.getRegion(), p.getPrice(), p.getRating()
        ));
    }

    @Override
    public ProductDetailDto getDetail(Long id) {
        Product p = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
        return new ProductDetailDto(
                p.getId(), p.getName(), p.getCountry(), p.getRegion(), p.getPrice(), p.getRating(), p.getCreatedAt()
        );
    }
}