package com.shopify.inventory.model.entity;

import lombok.*;
import java.util.Date;

/**
 * @author sing-fung
 * @since 1/16/2022
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseEntity
{
    protected String id;
    private String note;
    private Date created_time;
    private Date ts;
}
