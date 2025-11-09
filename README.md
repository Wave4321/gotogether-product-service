# GoTogether Product Service
Spring Boot 3.5 / Java 21 기반의 상품 검색 서비스입니다.  
예전에 진행했던 여행사 팀 프로젝트 중, 제가 담당했던 **상품 검색·조회 기능**을  
지금 기준 기술 스택으로 다시 정리해 구현한 프로젝트입니다.

---

## 1. 프로젝트 소개
여행 상품 데이터를 대상으로 한 **검색 · 페이징 · 정렬 API**를 제공합니다.  
기능 자체는 단순하지만, 실제 서비스에서 많이 쓰이는 구조(레이어 분리, QueryDSL, DTO 분리 등)를  
작게나마 제대로 담아보는 데에 집중했습니다.

---

## 2. 주요 기능

### 1) 다중 키워드 검색 (QueryDSL)
- 띄어쓰기 기준으로 키워드를 분리해서 검색합니다.
- name / country / region 3개 필드를 대상으로 검색합니다.
- AND / OR 조건을 선택할 수 있게 했습니다.

---

### 2) 페이징 & 정렬
- Spring의 Pageable을 사용합니다.
- size 범위 제한(1~100)을 걸어 불필요한 쿼리 부담을 줄였습니다.
- 정렬 옵션은 화이트리스트 방식으로 관리합니다.

---

### 3) 계층 구조
Controller → Service → Repository 구조로 구성했습니다.  
DTO, 도메인, API 모델을 분리해 레이어 간 책임이 섞이지 않도록 했습니다.

---

### 4) 로컬 실행 & 테스트
- H2 메모리 DB를 사용해 바로 실행해볼 수 있게 했습니다.
- 검색 API에 대한 간단한 통합 테스트(MockMvc) 1개를 포함했습니다.

---

## 3. 기술 스택
- Spring Boot 3.5  
- Java 21  
- JPA + QueryDSL 5  
- MySQL / H2(mem)  
- JUnit5, MockMvc  
- Gradle(Groovy)

---

## 4. 디렉토리 구조
src
├─ api
│ └─ product
│ ├─ ProductController.java
│ └─ dto
├─ service
│ └─ product
├─ domain
│ └─ product
│ ├─ Product.java
│ ├─ ProductRepository.java
│ ├─ ProductRepositoryCustom.java
│ └─ ProductRepositoryImpl.java
└─ resources
├─ application.yml
└─ data.sql

yaml
코드 복사

---

## 5. 검색 로직 요약

### AND / OR 조건 생성 예시
```java
BooleanBuilder where = new BooleanBuilder();

for (String tk : tokens) {
    BooleanExpression expr =
        p.name.containsIgnoreCase(tk)
         .or(p.country.containsIgnoreCase(tk))
         .or(p.region.containsIgnoreCase(tk));

    if (req.isAnd()) where.and(expr);
    else where.or(expr);
}
```
6. 정렬 정책
```java
switch (key) {
    case "price":
    case "priceasc": return new OrderSpecifier[]{ p.price.asc(), p.id.desc() };
    case "pricedesc": return new OrderSpecifier[]{ p.price.desc(), p.id.desc() };
    case "rating": return new OrderSpecifier[]{ p.rating.desc(), p.id.desc() };
    default: return new OrderSpecifier[]{ p.createdAt.desc(), p.id.desc() };
}
```
7. 실행 방법
run
```bash
./gradlew bootRun
예시 요청
```
```bash
GET /api/products/search?q=일본&page=0&size=10&sort=latest
```
8. 앞으로 추가해보고 싶은 것
검색 자동완성 (Prefix 검색)
간단한 캐싱(Redis)
테마/연령대 등 필터 조건
Swagger/OpenAPI 문서화
