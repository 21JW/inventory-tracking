package com.shopify.inventory.service;

import com.shopify.inventory.common.PageResponse;
import com.shopify.inventory.model.dto.InventoryInDTO;

import java.io.IOException;
import java.util.List;

/**
 * @author sing-fung
 * @since 1/10/2022
 */

public interface InventoryInService
{
    void save(InventoryInDTO dto);

    void update(InventoryInDTO dto);

    void deleteByIds(List<String> ids);

    PageResponse<InventoryInDTO> findByPage(Integer page, Integer size) throws Exception;

    void exportCSV() throws IOException;

    List<InventoryInDTO> queryAll() throws Exception;
}
