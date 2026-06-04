package com.eweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentResponseDto {
	private Long id;

    private String agentName;

    private String mobile;

    private String assignedArea;

    private Long todayOrders;

    private String status;
}
