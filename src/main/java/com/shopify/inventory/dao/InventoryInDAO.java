package com.shopify.inventory.dao;

import com.shopify.inventory.common.QueryWrapper;
import com.shopify.inventory.model.entity.InventoryIn;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sing-fung
 * @since 1/10/2022
 */

@Repository
public interface InventoryInDAO extends BaseDAO
{
    List<InventoryIn> selectWithQuery(QueryWrapper wrapper);

    int insert(InventoryIn inventoryIn);

    int update(InventoryIn inventoryIn);

    int batchDelete(List<String> ids);

    int countWithQuery(QueryWrapper wrapper);

    List<InventoryIn> queryAll();

    int setAvailableNum(InventoryIn inventoryIn);

    int batchSetAvailableNum(List<InventoryIn> inventoryIns);

    List<InventoryIn> queryByItemCode(String item_code);

    InventoryIn queryById(String id);
}
