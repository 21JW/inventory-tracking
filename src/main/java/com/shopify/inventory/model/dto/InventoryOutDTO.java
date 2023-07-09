package com.shopify.inventory.model.dto;

import lombok.*;
import javax.validation.constraints.*;

/**
 * @author sing-fung
 * @since 1/9/2022
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventoryOutDTO extends BaseDTO
{
    @NotBlank(message = "id could not be null", groups = Update.class)
    private String id;
    @NotBlank(message = "inventory_in_id could not be null", groups = Insert.class)
    private String inventory_in_id;
    private double num; // num that move out from the inventory

    public interface Update {}
    public interface Insert {}
}
