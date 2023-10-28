package com.ad.myhome.model.entity;

import com.ad.myhome.utils.enums.CurrencyType;
import com.ad.myhome.utils.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public", catalog = "myhome")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userid", nullable = false)
    private int userId;
    @Basic
    @Column(name = "username", nullable = true, length = 100)
    private String userName;
    @Basic
    @Column(name = "useremail", nullable = false, length = 100)
    private String userEmail;
    @Basic
    @Column(name = "userpassword", nullable = true, length = 20)
    private String userPassword;
    @Basic
    @Column(name = "userimage", nullable = true, length = 20)
    private String userImage;
    @Enumerated(EnumType.STRING)
    @Column(name = "usercurrencypreference", nullable = false, length = 3)
    private CurrencyType userCurrencyPreference;
    @Enumerated(EnumType.STRING)
    @Column(name = "userrole", nullable = false)
    private RoleType userRole;
}
