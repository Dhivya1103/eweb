package com.eweb.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentDto {
    private Long id;

    private String agentName;

    private String mobile;

    private String email;

    private String address;

    private String vehicleType;

    private String assignedArea;

    private String status;

    private LocalDateTime createdDate;
}
