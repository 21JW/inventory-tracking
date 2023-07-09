package com.shopify.inventory.service.impl;

import com.shopify.inventory.common.PageResponse;
import com.shopify.inventory.dao.InventoryInDAO;
import com.shopify.inventory.dao.ItemDAO;
import com.shopify.inventory.exception.BusinessException;
import com.shopify.inventory.model.dto.ItemDTO;
import com.shopify.inventory.model.entity.InventoryIn;
import com.shopify.inventory.model.entity.Item;
import com.shopify.inventory.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.shopify.inventory.common.QueryWrapper.QueryWrapperBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class ItemServiceImpl extends BaseServiceImpl implements ItemService
{
    private ItemDAO itemDAO;
    private InventoryInDAO inventoryInDAO;

    public ItemServiceImpl(ItemDAO itemDAO, InventoryInDAO inventoryInDAO)
    {
        this.itemDAO = itemDAO;
        this.inventoryInDAO = inventoryInDAO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ItemDTO dto)
    {
        Item i = itemDAO.queryByCode(dto.getCode());
        if (i != null)
        { throw new BusinessException("Item code is already used"); }

        save(dto, new Item(), itemDAO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ItemDTO dto)
    {
        Item item = itemDAO.queryById(dto.getId());
        if (item == null)
        { throw new BusinessException("item does not exist"); }

        if (!item.getCode().equals(dto.getCode()))
        {
            List<InventoryIn> inventoryIns = inventoryInDAO.queryByItemCode(item.getCode());
            if (!CollectionUtils.isEmpty(inventoryIns))
            { throw new BusinessException("item code cannot be modified since it has been referenced"); }
        }

        QueryWrapperBuilder builder = new QueryWrapperBuilder();
        builder.neq("id", dto.getId());
        builder.eq("code", dto.getCode());
        List<Item> items = itemDAO.selectWithQuery(builder.build());
        if (!CollectionUtils.isEmpty(items))
        { throw new BusinessException("item code is already used"); }

        update(dto, new Item(), itemDAO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids)
    {
        if (!CollectionUtils.isEmpty(ids))
        {
            QueryWrapperBuilder builder = new QueryWrapperBuilder();
            builder.in("id", ids);
            List<Item> itemList = itemDAO.selectWithQuery(builder.build());

            List<String> codes = new ArrayList<>();
            for(Item item : itemList)
            { codes.add(item.getCode()); }

            builder = new QueryWrapperBuilder();
            builder.in("item_code", codes);
            List<InventoryIn> inventoryInList = inventoryInDAO.selectWithQuery(builder.build());

            if (!CollectionUtils.isEmpty(inventoryInList))
            { throw new BusinessException("it cannot be deleted since it is referenced in inventory_in"); }

            itemDAO.batchDelete(ids);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageResponse<ItemDTO> findByPage(Integer page, Integer size) throws Exception
    {
        return findByPage(page, size, "code", "asc", itemDAO, "com.shopify.inventory.model.dto.ItemDTO");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ItemDTO> queryAll() throws Exception
    {
        return queryAll(itemDAO, "com.shopify.inventory.model.dto.ItemDTO");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exportCSV() throws IOException
    {
        List<Item> itemList = itemDAO.queryAll();
        List<Object[]> cellList = toCellList(itemList);

        String[] fields = {"id","name","code","note","created_time","ts"};
        String fileName = "csv\\item.csv";

        exportCSV(fileName, fields, cellList);
    }

    private List<Object[]> toCellList(List<Item> itemList)
    {
        List<Object[]> result = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");

        for(Item item : itemList)
        {
            Object[] obj = new Object[6];
            obj[0] = item.getId();
            obj[1] = item.getName();
            obj[2] = item.getCode();
            obj[3] = item.getNote();
            obj[4] = df.format(item.getCreated_time());
            obj[5] = df.format(item.getTs());

            result.add(obj);
        }

        return result;
    }
}