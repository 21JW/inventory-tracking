package com.shopify.inventory.controller;

import com.shopify.inventory.common.PageResponse;
import com.shopify.inventory.common.Result;
import com.shopify.inventory.model.dto.IdsDTO;
import com.shopify.inventory.model.dto.ItemDTO;
import com.shopify.inventory.service.ItemService;
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
 * @since 1/10/2022
 */

@RestController
@RequestMapping(value = "/api/item")
@Slf4j
@Validated
public class ItemController
{
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService)
    { this.itemService = itemService; }

    @PostMapping
    public ResponseEntity<Result<Object>> save(@RequestBody @Validated(ItemDTO.Insert.class) ItemDTO dto)
    {
        itemService.save(dto);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success());
    }

    @DeleteMapping
    public ResponseEntity<Result<Object>> deleteByIds(@RequestBody @Validated IdsDTO dto)
    {
        itemService.deleteByIds(dto.getIds());
        return ResponseEntity.status(HttpStatus.OK).body(Result.success());
    }

    @PutMapping
    public ResponseEntity<Result<Object>> update(@RequestBody @Validated(ItemDTO.Update.class) ItemDTO dto)
    {
        itemService.update(dto);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success());
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Result<PageResponse<ItemDTO>>> queryPage(
            @RequestParam(value = "page") @Min(value = 1, message = "page number should be larger than 0") Integer page,
            @RequestParam(value = "size") @Min(value = 1, message = "page size should be larger than 0") Integer size) throws Exception
    {
        PageResponse<ItemDTO> response = itemService.findByPage(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(Result.success(response));
    }

    @GetMapping(value = "/csv")
    public void exportCSV(HttpServletResponse response) throws IOException
    {
        itemService.exportCSV();
    }

    @GetMapping
    public List<ItemDTO> queryAll() throws Exception
    {
        return itemService.queryAll();
    }
}
