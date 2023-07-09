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
public class InventoryOut extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = -2618263337139638157L;

    private String inventory_in_id;
    private double num;
}
