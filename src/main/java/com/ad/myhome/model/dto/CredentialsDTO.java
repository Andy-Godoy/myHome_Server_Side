package com.ad.myhome.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsDTO {

    private Long userId;
    private String userEmail;
    private String userPassword;
    private String userName;

}
