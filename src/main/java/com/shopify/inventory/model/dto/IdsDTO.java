package com.shopify.inventory.model.dto;

import lombok.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author sing-fung
 * @since 1/11/2022
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IdsDTO
{
    @NotEmpty(message = "ids could not be empty")
    private List<String> ids;
}
