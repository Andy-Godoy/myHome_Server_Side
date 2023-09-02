package com.ad.myhome.model.entity;

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
    private int userid;
    @Basic
    @Column(name = "username", nullable = true, length = 100)
    private String username;
    @Basic
    @Column(name = "useremail", nullable = false, length = 100)
    private String useremail;
    @Basic
    @Column(name = "userpassword", nullable = true, length = 20)
    private String userpassword;
    @Basic
    @Column(name = "usercurrencypreference", nullable = false, length = 3)
    private String usercurrencypreference;

}
