package com.adobe.tech.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserLoginDTO {
    private String email;
    private String password;
    private String latitude;
    private String longitude;
}