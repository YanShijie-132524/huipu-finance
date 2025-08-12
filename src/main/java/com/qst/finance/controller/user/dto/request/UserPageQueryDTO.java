package com.qst.finance.controller.user.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserPageQueryDTO {

    private Integer current;

    private Integer size;

    private String name;

    private String phone;

    private String address;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate[] daterange;

    private String status;

}