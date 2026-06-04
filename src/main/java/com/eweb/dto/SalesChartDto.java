package com.eweb.dto;

import org.hibernate.internal.build.AllowSysOut;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SalesChartDto {
	  private String date;
	    private Double sales;
}
