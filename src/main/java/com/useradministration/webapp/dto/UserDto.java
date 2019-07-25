package com.useradministration.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String usernameQ;
    private String passwordQ;
    private String firstNameQ;
    private String lastNameQ;
    private String statusQ;
    private String roleQ;

}