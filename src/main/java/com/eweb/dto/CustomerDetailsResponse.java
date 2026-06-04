package com.eweb.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailsResponse {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String address;
    private LocalDate joinedDate;
    private String status;
    private String city;
    private String state;
    private String pinCode;

    private List<OrderHistoryDto> orderHistory;
}
