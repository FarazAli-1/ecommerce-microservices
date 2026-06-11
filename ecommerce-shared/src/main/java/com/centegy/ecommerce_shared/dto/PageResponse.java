package com.centegy.ecommerce_shared.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse <T> {

    private java.util.List<T> content;
    private int totalPages;
    private int pageSize;
    private int page;
    private java.lang.Long totalElements;
    private boolean last;

}