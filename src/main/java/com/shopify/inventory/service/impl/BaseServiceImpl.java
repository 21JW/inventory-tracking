package com.shopify.inventory.service.impl;

import com.shopify.inventory.common.PageResponse;
import com.shopify.inventory.common.QueryWrapper;
import com.shopify.inventory.common.QueryWrapper.QueryWrapperBuilder;
import com.shopify.inventory.dao.BaseDAO;
import com.shopify.inventory.model.dto.BaseDTO;
import com.shopify.inventory.model.entity.BaseEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.BeanUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author sing-fung
 * @since 1/15/2022
 */

public class BaseServiceImpl<T>
{
    protected void exportCSV(String fileName, String[] fields, List<Object[]> cellList) throws IOException
    {
        CSVPrinter printer = new CSVPrinter(new FileWriter(fileName), CSVFormat.DEFAULT);
        printer.printRecord(fields);

        for (Object[] item : cellList)
        { printer.printRecord(item); }

        printer.flush();
        printer.close();
    }

    protected PageResponse<BaseDTO> findByPage(Integer page, Integer size, String column, String order, BaseDAO baseDAO, String dtoClassName) throws Exception
    {
        QueryWrapperBuilder builder = new QueryWrapperBuilder();

        builder.sort(column, order);

        builder.page((page - 1) * size, size);
        QueryWrapper wrapper = builder.build();

        List<T> entities = baseDAO.selectWithQuery(wrapper);
        Integer total = baseDAO.countWithQuery(wrapper);

        List<BaseDTO> dtoList = copyEntitiesToDtos(entities, dtoClassName);

        return new PageResponse<>(total, dtoList);
    }

    protected void save(BaseDTO dto, BaseEntity entity, BaseDAO dao)
    {
        BeanUtils.copyProperties(dto, entity);
        entity.setId(UUID.randomUUID().toString());
        entity.setCreated_time(new Date());
        entity.setTs(new Date());
        dao.insert(entity);
    }

    protected void update(BaseDTO dto, BaseEntity entity, BaseDAO dao)
    {
        BeanUtils.copyProperties(dto, entity);
        entity.setTs(new Date());
        dao.update(entity);
    }

    protected List<BaseDTO> queryAll(BaseDAO dao, String dtoClassName) throws Exception
    {
        List<T> entities = dao.queryAll();

        List<BaseDTO> dtoList = copyEntitiesToDtos(entities, dtoClassName);

        return dtoList;
    }

    private List<BaseDTO> copyEntitiesToDtos(List<T> entities, String dtoClassName) throws Exception
    {
        Class dtoClass = Class.forName(dtoClassName);
        List<BaseDTO> dtoList = new ArrayList<>();

        for (T entity : entities)
        {
            BaseDTO dto = (BaseDTO) dtoClass.newInstance();
            BeanUtils.copyProperties(entity, dto);
            dtoList.add(dto);
        }

        return dtoList;
    }
}
