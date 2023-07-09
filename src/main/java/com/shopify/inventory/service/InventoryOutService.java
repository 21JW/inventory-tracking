package com.shopify.inventory.service;

import com.shopify.inventory.common.PageResponse;
import com.shopify.inventory.model.dto.InventoryOutDTO;

import java.io.IOException;
import java.util.List;

/**
 * @author sing-fung
 * @since 1/12/2022
 */

public interface InventoryOutService
{
    void save(InventoryOutDTO dto);

    void update(InventoryOutDTO dto);

    void deleteByIds(List<String> ids);

    PageResponse<InventoryOutDTO> findByPage(Integer page, Integer size) throws Exception;

    void exportCSV() throws IOException;

    List<InventoryOutDTO> queryAll() throws Exception;
}
