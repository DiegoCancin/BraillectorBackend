package com.braillector.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegisterDto {
    private String firstName;
    private String paLastName;
    private String maLastName;
    private String email;
    private String password;
}
