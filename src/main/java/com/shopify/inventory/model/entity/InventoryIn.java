package com.shopify.inventory.model.entity;

import lombok.*;
import java.io.Serializable;

/**
 * @author sing-fung
 * @since 1/9/2022
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventoryIn extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = -3994807311412330524L;

    private String item_code;
    private double initial_num;
    private double available_num;
    private String unit;
    private double cost;
    private String currency;
}
