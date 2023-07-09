package com.shopify.inventory.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageResponse<T>
{
    private Integer total;
    private List<T> list;
}
