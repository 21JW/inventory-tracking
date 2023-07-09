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
public class Item extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 2150604803988616475L;

    private String name;
    private String code;
}
