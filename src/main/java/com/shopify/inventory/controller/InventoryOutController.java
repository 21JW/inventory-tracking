package com.shopify.inventory.controller;

import com.shopify.inventory.common.PageResponse;
import com.shopify.inventory.common.Result;
import com.shopify.inventory.exception.BusinessException;
import com.shopify.inventory.model.dto.IdsDTO;
import com.shopify.inventory.model.dto.InventoryOutDTO;
import com.shopify.inventory.service.InventoryOutService;
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
@RequestMapping(value = "/api/inventory-out")
@Slf4j
@Validated
public class InventoryOutController
{
    private InventoryOutService inventoryOutService;

    @Autowired
    public InventoryOutController(InventoryOutService inventoryOutService)
    { this.inventoryOutService = inventoryOutService; }

    @PostMapping
    public ResponseEntity<Result<Object>> save(@RequestBody @Validated(InventoryOutDTO.Insert.class) InventoryOutDTO dto)
    {
        if(dto.getNum() <= 0)
        { throw new BusinessException("num should be larger than 0"); }

        inventoryOutService.save(dto);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success());
    }

    @DeleteMapping
    public ResponseEntity<Result<Object>> deleteByIds(@RequestBody @Validated IdsDTO dto)
    {
        inventoryOutService.deleteByIds(dto.getIds());
        return ResponseEntity.status(HttpStatus.OK).body(Result.success());
    }

    @PutMapping
    public ResponseEntity<Result<Object>> update(@RequestBody @Validated(InventoryOutDTO.Update.class) InventoryOutDTO dto)
    {
        if(dto.getInventory_in_id() != null && dto.getInventory_in_id().length() > 0)
        { throw new BusinessException("inventory_in_id could not be modified"); }

        if(dto.getNum() != 0)
        { throw new BusinessException("num could not be modified"); }

        inventoryOutService.update(dto);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success());
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Result<PageResponse<InventoryOutDTO>>> queryPage(
            @RequestParam(value = "page") @Min(value = 1, message = "page number should be larger than 0") Integer page,
            @RequestParam(value = "size") @Min(value = 1, message = "page size should be larger than 0") Integer size) throws Exception
    {
        PageResponse<InventoryOutDTO> response = inventoryOutService.findByPage(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success(response));
    }

    @GetMapping(value = "/csv")
    public void exportCSV(HttpServletResponse response) throws IOException
    {
        inventoryOutService.exportCSV();
    }

    @GetMapping
    public List<InventoryOutDTO> queryAll() throws Exception
    {
        return inventoryOutService.queryAll();
    }
}
