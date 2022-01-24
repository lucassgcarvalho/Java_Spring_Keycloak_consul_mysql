package com.essential.app.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderDto implements Serializable {
    @NotEmpty
    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;
}
