package com.shopify.inventory.dao;

import com.shopify.inventory.common.QueryWrapper;
import org.springframework.stereotype.Repository;
import com.shopify.inventory.model.entity.Item;

import java.util.List;

/**
 * @author sing-fung
 * @since 1/11/2022
 */

@Repository
public interface ItemDAO extends BaseDAO
{
    List<Item> selectWithQuery(QueryWrapper wrapper);

    int insert(Item item);

    int update(Item item);

    int batchDelete(List<String> ids);

    int countWithQuery(QueryWrapper wrapper);

    List<Item> queryAll();

    Item queryByCode(String code);

    Item queryById(String id);
}
