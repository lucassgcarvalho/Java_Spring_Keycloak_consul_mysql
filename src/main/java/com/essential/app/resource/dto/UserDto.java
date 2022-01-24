package com.essential.app.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    @Null
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String name;

    @NotNull
    @Range(min=0, max=150)
    private int age;

    @NotNull
    private GenderDto gender;

    @NotEmpty
    @Email
    private String email;

    private List<ScheduleDto> listSchedule;

    @Null
    private LocalDateTime createdAt;

    @Null
    private LocalDateTime updatedAt;
}
