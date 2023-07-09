package com.shopify.inventory.service.impl;

import com.shopify.inventory.common.PageResponse;
import com.shopify.inventory.dao.InventoryInDAO;
import com.shopify.inventory.dao.InventoryOutDAO;
import com.shopify.inventory.exception.BusinessException;
import com.shopify.inventory.model.dto.InventoryOutDTO;
import com.shopify.inventory.model.entity.InventoryIn;
import com.shopify.inventory.model.entity.InventoryOut;
import com.shopify.inventory.service.InventoryOutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.shopify.inventory.common.QueryWrapper.QueryWrapperBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author sing-fung
 * @since 1/12/2022
 */

@Service
@Slf4j
public class InventoryOutServiceImpl extends BaseServiceImpl implements InventoryOutService
{
    private InventoryOutDAO inventoryOutDAO;
    private InventoryInDAO inventoryInDAO;

    public InventoryOutServiceImpl(InventoryOutDAO inventoryOutDAO, InventoryInDAO inventoryInDAO)
    {
        this.inventoryOutDAO = inventoryOutDAO;
        this.inventoryInDAO = inventoryInDAO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(InventoryOutDTO dto)
    {
        InventoryIn inventoryIn = inventoryInDAO.queryById(dto.getInventory_in_id());
        if (inventoryIn == null)
        { throw new BusinessException("inventory_in does not exist"); }

        if (inventoryIn.getAvailable_num() < dto.getNum())
        { throw new BusinessException("num is larger than available_num in inventory"); }

        save(dto, new InventoryOut(), inventoryOutDAO);

        double available_num = inventoryIn.getAvailable_num() - dto.getNum();
        inventoryIn.setAvailable_num(available_num);
        inventoryIn.setTs(new Date());
        inventoryInDAO.setAvailableNum(inventoryIn);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(InventoryOutDTO dto)
    {
        InventoryOut i = inventoryOutDAO.queryById(dto.getId());
        if (i == null)
        { throw new BusinessException("record does not exist"); }

        update(dto, new InventoryOut(), inventoryOutDAO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids)
    {
        if (!CollectionUtils.isEmpty(ids))
        {
            // get all inventory_out records
            QueryWrapperBuilder builder = new QueryWrapperBuilder();
            builder.in("id", ids);
            List<InventoryOut> inventoryOutList = inventoryOutDAO.selectWithQuery(builder.build());
            // ids of inventory_in
            List<String> inventory_in_ids = new ArrayList<>();
            for(InventoryOut out : inventoryOutList)
            { inventory_in_ids.add(out.getInventory_in_id()); }
            // get all relating inventory_in records and put them into a map
            builder = new QueryWrapperBuilder();
            builder.in("id", inventory_in_ids);
            List<InventoryIn> inventoryInList = inventoryInDAO.selectWithQuery(builder.build());
            HashMap<String, InventoryIn> map = new HashMap<>();
            for(InventoryIn in : inventoryInList)
            { map.put(in.getId(), in); }
            // restore inventory_in available_num
            for(InventoryOut out : inventoryOutList)
            {
                String id = out.getInventory_in_id();
                double addNum = out.getNum();
                InventoryIn in = map.get(id);
                in.setAvailable_num(in.getAvailable_num() + addNum);
                in.setTs(new Date());
            }
            // delete inventory_out records
            inventoryOutDAO.batchDelete(ids);
            // batch update inventory_in
            List<InventoryIn> inventoryInUpdateList = new ArrayList<>();
            for(InventoryIn in : map.values())
            { inventoryInUpdateList.add(in); }
            inventoryInDAO.batchSetAvailableNum(inventoryInUpdateList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageResponse<InventoryOutDTO> findByPage(Integer page, Integer size) throws Exception
    {
        return findByPage(page, size, "ts", "desc", inventoryOutDAO, "com.shopify.inventory.model.dto.InventoryOutDTO");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<InventoryOutDTO> queryAll() throws Exception
    {
        return queryAll(inventoryOutDAO, "com.shopify.inventory.model.dto.InventoryOutDTO");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exportCSV() throws IOException
    {
        List<InventoryOut> inventoryOutList = inventoryOutDAO.queryAll();
        List<Object[]> cellList = toCellList(inventoryOutList);

        String[] fields = {"id","inventory_in_id","num","note","created_time","ts"};
        String fileName = "csv\\inventory_out.csv";

        exportCSV(fileName, fields, cellList);
    }

    private List<Object[]> toCellList(List<InventoryOut> inventoryOutList)
    {
        List<Object[]> result = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");
//        yyyy-MM-dd HH:mm:ss

        for(InventoryOut i : inventoryOutList)
        {
            Object[] obj = new Object[6];
            obj[0] = i.getId();
            obj[1] = i.getInventory_in_id();
            obj[2] = i.getNum();
            obj[3] = i.getNote();
            obj[4] = df.format(i.getCreated_time());
            obj[5] = df.format(i.getTs());

            result.add(obj);
        }

        return result;
    }
}