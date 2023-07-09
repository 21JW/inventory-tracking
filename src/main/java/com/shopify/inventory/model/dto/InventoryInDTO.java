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
public class InventoryInDTO extends BaseDTO
{
    @NotBlank(message = "id could not be null", groups = Update.class)
    private String id;
    @NotBlank(message = "item code could not be null", groups = Insert.class)
    private String item_code;
    private double initial_num;
    private double available_num;
    @NotBlank(message = "unit could not be null", groups = Insert.class)
    private String unit;
    private double cost;
    @NotBlank(message = "currency could not be null", groups = Insert.class)
    private String currency;

    public interface Update {}
    public interface Insert {}
}
