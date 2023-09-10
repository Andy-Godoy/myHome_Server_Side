package com.ad.myhome.model.dto;

import com.ad.myhome.model.entity.UserEntity;
import com.ad.myhome.utils.enums.CurrencyType;
import com.ad.myhome.utils.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private int userId;
    private String userName;
    private String userEmail;
    private CurrencyType userCurrencyPreference;
    private RoleType userRole;

    public UserDTO(UserEntity user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.userCurrencyPreference = user.getUserCurrencyPreference();
        this.userRole = user.getUserRole();
    }
}
