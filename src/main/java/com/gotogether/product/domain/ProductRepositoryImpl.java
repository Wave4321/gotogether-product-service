package com.gotogether.product.domain.product;

import com.gotogether.product.api.product.dto.ProductSummaryDto;
import com.gotogether.product.api.product.dto.SearchRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory qf;
    private static final QProduct p = QProduct.product;

    @Override
    public Page<ProductSummaryDto> search(SearchRequest req) {


        String q = req.q() == null ? "" : req.q().trim();
        String[] tokens = ...
        BooleanBuilder where = ...

        int page = Math.max(0, req.pageOrDefault());
        int size = Math.min(Math.max(1, req.sizeOrDefault()), 100);
        Pageable pageable = PageRequest.of(page, size);

        OrderSpecifier<?>[] orders = mapSort(req.sort());

        List<ProductSummaryDto> content = qf.select(
                        Projections.constructor(ProductSummaryDto.class,
                                p.id, p.name, p.country, p.region, p.price, p.rating
                        )
                ).from(p)
                .where(where)
                .orderBy(orders)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = qf.select(p.count())
                .from(p)
                .where(where)
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    private OrderSpecifier<?>[] mapSort(String sortKey) {
        if (sortKey == null || sortKey.isBlank()) {
            return new OrderSpecifier[]{
                    p.createdAt.desc(),
                    p.id.desc()  // 동일 createdAt 시 안정성 확보
            };
        }

        // 입력값 소문자로 변환
        String key = sortKey.toLowerCase();

        switch (key) {

            // 가격 오름차순
            case "price":
            case "priceasc":
                return new OrderSpecifier[]{
                        p.price.asc(),
                        p.id.desc()
                };

            // 가격 내림차순
            case "pricedesc":
                return new OrderSpecifier[]{
                        p.price.desc(),
                        p.id.desc()
                };

            // 평점순 (높은순)
            case "rating":
                return new OrderSpecifier[]{
                        p.rating.desc(),
                        p.id.desc()
                };

            // 최신순
            case "latest":
            default:
                return new OrderSpecifier[]{
                        p.createdAt.desc(),
                        p.id.desc()
                };
        }
    }
}
