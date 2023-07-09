package com.shopify.inventory.service;

import com.shopify.inventory.common.PageResponse;
import com.shopify.inventory.model.dto.ItemDTO;

import java.io.IOException;
import java.util.List;

/**
 * @author sing-fung
 * @since 1/10/2022
 */

public interface ItemService
{
    void save(ItemDTO dto);

    void update(ItemDTO dto);

    void deleteByIds(List<String> ids);

    PageResponse<ItemDTO> findByPage(Integer page, Integer size) throws Exception;

    void exportCSV() throws IOException;

    List<ItemDTO> queryAll() throws Exception;
}
