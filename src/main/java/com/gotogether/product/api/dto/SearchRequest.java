package com.gotogether.product.api.dto;

public record SearchRequest(
        String q,
        Integer page,
        Integer size,
        String sort
) {
    public int pageOrDefault() { return page == null ? 0 : Math.max(page, 0); }
    public int sizeOrDefault() { return size == null ? 20 : Math.max(size, 1); }
    public boolean isAnd() { return Boolean.TRUE.equals(andMatch); }
}
