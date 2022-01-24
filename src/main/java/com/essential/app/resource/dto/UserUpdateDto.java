package com.essential.app.resource.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto implements Serializable {

    @JsonIgnore
    private Long id;

    @Size(min = 2, message = "user name should have at least 2 characters")
    private String name;

    @Range(min=0, max=150)
    private Integer age;

    private GenderDto gender;

    @Email
    private String email;
}
