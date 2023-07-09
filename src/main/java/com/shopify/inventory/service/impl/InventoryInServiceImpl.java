package com.shopify.inventory.service.impl;

import com.shopify.inventory.common.PageResponse;
import com.shopify.inventory.dao.InventoryInDAO;
import com.shopify.inventory.dao.InventoryOutDAO;
import com.shopify.inventory.dao.ItemDAO;
import com.shopify.inventory.exception.BusinessException;
import com.shopify.inventory.model.dto.InventoryInDTO;
import com.shopify.inventory.model.entity.InventoryIn;
import com.shopify.inventory.model.entity.InventoryOut;
import com.shopify.inventory.service.InventoryInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.shopify.inventory.common.QueryWrapper.QueryWrapperBuilder;
import com.shopify.inventory.model.entity.Item;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sing-fung
 * @since 1/10/2022
 */

@Service
@Slf4j
public class InventoryInServiceImpl extends BaseServiceImpl implements InventoryInService
{
    private InventoryInDAO inventoryInDAO;
    private ItemDAO itemDAO;
    private InventoryOutDAO inventoryOutDAO;

    public InventoryInServiceImpl(InventoryInDAO inventoryInDAO, ItemDAO itemDAO, InventoryOutDAO inventoryOutDAO)
    {
        this.inventoryInDAO = inventoryInDAO;
        this.itemDAO = itemDAO;
        this.inventoryOutDAO = inventoryOutDAO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(InventoryInDTO dto)
    {
        Item item = itemDAO.queryByCode(dto.getItem_code());
        if (item == null)
        { throw new BusinessException("item does not exist"); }

        save(dto, new InventoryIn(), inventoryInDAO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(InventoryInDTO dto)
    {
        InventoryIn i = inventoryInDAO.queryById(dto.getId());
        if (i == null)
        { throw new BusinessException("record does not exist"); }

        update(dto, new InventoryIn(), inventoryInDAO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids)
    {
        if (!CollectionUtils.isEmpty(ids))
        {
            QueryWrapperBuilder builder = new QueryWrapperBuilder();
            builder.in("inventory_in_id", ids);

            List<InventoryOut> inventoryOuts = inventoryOutDAO.selectWithQuery(builder.build());
            if (!CollectionUtils.isEmpty(inventoryOuts))
            { throw new BusinessException("it cannot be deleted since it is referenced in inventory_out"); }

            inventoryInDAO.batchDelete(ids);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageResponse<InventoryInDTO> findByPage(Integer page, Integer size) throws Exception
    {
        return findByPage(page, size, "ts", "desc", inventoryInDAO, "com.shopify.inventory.model.dto.InventoryInDTO");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<InventoryInDTO> queryAll() throws Exception
    {
        return queryAll(inventoryInDAO, "com.shopify.inventory.model.dto.InventoryInDTO");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exportCSV() throws IOException
    {
        List<InventoryIn> inventoryInList = inventoryInDAO.queryAll();
        List<Object[]> cellList = toCellList(inventoryInList);

        String[] fields = {"id","item_code","initial_num","available_num","unit","cost","currency","note","created_time","ts"};
        String fileName = "csv\\inventory_in.csv";

        exportCSV(fileName, fields, cellList);
    }

    private List<Object[]> toCellList(List<InventoryIn> inventoryInList)
    {
        List<Object[]> result = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");

        for(InventoryIn i : inventoryInList)
        {
            Object[] obj = new Object[10];
            obj[0] = i.getId();
            obj[1] = i.getItem_code();
            obj[2] = i.getInitial_num();
            obj[3] = i.getAvailable_num();
            obj[4] = i.getUnit();
            obj[5] = i.getCost();
            obj[6] = i.getCurrency();
            obj[7] = i.getNote();
            obj[8] = df.format(i.getCreated_time());
            obj[9] = df.format(i.getTs());

            result.add(obj);
        }

        return result;
    }
}
