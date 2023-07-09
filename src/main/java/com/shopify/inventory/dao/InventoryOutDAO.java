package com.shopify.inventory.dao;

import com.shopify.inventory.common.QueryWrapper;
import com.shopify.inventory.model.entity.InventoryOut;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sing-fung
 * @since 1/10/2022
 */

@Repository
public interface InventoryOutDAO extends BaseDAO
{
    List<InventoryOut> selectWithQuery(QueryWrapper wrapper);

    int insert(InventoryOut inventoryOut);

    int update(InventoryOut inventoryOut);

    int batchDelete(List<String> ids);

    int countWithQuery(QueryWrapper wrapper);

    List<InventoryOut> queryAll();

    InventoryOut queryById(String id);
}
