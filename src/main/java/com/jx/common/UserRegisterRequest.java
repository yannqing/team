package com.jx.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    private String userAccount;
    private String planetCode;
    private String userPassword;
    private String checkPassword;
}
