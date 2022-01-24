package com.essential.app.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto implements Serializable {

    private Long id;

    private String name;

    private String description;

    private ScheduleStatusDto scheduleStatus;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
