package com.shopify.inventory.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * @author sing-fung
 * @since 1/17/2022
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseDTO
{
    protected String note;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-5")
    protected Date created_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-5")
    protected Date ts;
}
