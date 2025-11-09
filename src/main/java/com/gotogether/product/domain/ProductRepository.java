package com.gotogether.product.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    // 간단 contains 검색(AND/OR 고도화는 다음 스텝에서)
    Page<Product> findByNameContainingIgnoreCaseOrCountryContainingIgnoreCaseOrRegionContainingIgnoreCase(
            String name, String country, String region, Pageable pageable
    );
}