package com.shopify.inventory.controller;

import com.shopify.inventory.common.PageResponse;
import com.shopify.inventory.common.Result;
import com.shopify.inventory.exception.BusinessException;
import com.shopify.inventory.model.dto.IdsDTO;
import com.shopify.inventory.model.dto.InventoryInDTO;
import com.shopify.inventory.service.InventoryInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;

/**
 * @author sing-fung
 * @since 1/12/2022
 */

@RestController
@RequestMapping(value = "/api/inventory-in")
@Slf4j
@Validated
public class InventoryInController
{
    private InventoryInService inventoryInService;

    @Autowired
    public InventoryInController(InventoryInService inventoryInService)
    { this.inventoryInService = inventoryInService; }

    @PostMapping
    public ResponseEntity<Result<Object>> save(@RequestBody @Validated(InventoryInDTO.Insert.class) InventoryInDTO dto)
    {
        if(dto.getInitial_num() <= 0)
        { throw new BusinessException("initial_num should be larger than 0"); }

        if(dto.getCost() <= 0)
        { throw new BusinessException("cost should be larger than 0"); }

        dto.setAvailable_num(dto.getInitial_num());

        inventoryInService.save(dto);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success());
    }

    @DeleteMapping
    public ResponseEntity<Result<Object>> deleteByIds(@RequestBody @Validated IdsDTO dto)
    {
        inventoryInService.deleteByIds(dto.getIds());
        return ResponseEntity.status(HttpStatus.OK).body(Result.success());
    }

    @PutMapping
    public ResponseEntity<Result<Object>> update(@RequestBody @Validated(InventoryInDTO.Update.class) InventoryInDTO dto)
    {
        if(dto.getItem_code() != null)
        { throw new BusinessException("item code could not be modified"); }

        if(dto.getInitial_num() != 0)
        { throw new BusinessException("initial_num could not be modified"); }

        if(dto.getAvailable_num() != 0)
        { throw new BusinessException("available_num could not be modified"); }

        if(dto.getCost() != 0)
        { throw new BusinessException("cost could not be modified"); }

        inventoryInService.update(dto);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success());
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Result<PageResponse<InventoryInDTO>>> queryPage(
            @RequestParam(value = "page") @Min(value = 1, message = "page number should be larger than 0") Integer page,
            @RequestParam(value = "size") @Min(value = 1, message = "page size should be larger than 0") Integer size) throws Exception
    {
        PageResponse<InventoryInDTO> response = inventoryInService.findByPage(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success(response));
    }

    @GetMapping(value = "/csv")
    public void exportCSV(HttpServletResponse response) throws IOException
    {
        inventoryInService.exportCSV();
    }

    @GetMapping
    public List<InventoryInDTO> queryAll() throws Exception
    {
        return inventoryInService.queryAll();
    }
}
