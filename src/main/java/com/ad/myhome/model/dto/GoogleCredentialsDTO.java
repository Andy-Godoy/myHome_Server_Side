package com.ad.myhome.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleCredentialsDTO {
    private String userId;
    private String userEmail;
    private String userName;
    private String userImage;
}
