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
public class ItemDTO extends BaseDTO
{
    @NotBlank(message = "id could not be null", groups = Update.class)
    private String id;
    @NotBlank(message = "name could not be empty", groups = Insert.class)
    private String name;
    @NotBlank(message = "code could not be empty", groups = Insert.class)
    private String code;

    public interface Update {}
    public interface Insert {}
}
