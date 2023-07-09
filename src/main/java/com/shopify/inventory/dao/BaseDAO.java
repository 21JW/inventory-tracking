package com.shopify.inventory.dao;

import com.shopify.inventory.common.QueryWrapper;

import java.util.List;

/**
 * @author sing-fung
 * @since 1/15/2022
 */

public interface BaseDAO<T>
{
    List<T> selectWithQuery(QueryWrapper wrapper);

    int countWithQuery(QueryWrapper wrapper);

    int insert(T t);

    int update(T t);

    List<T> queryAll();
}
