package com.ad.myhome.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "agencies", schema = "public", catalog = "myhome")
public class AgencyEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "agencyid", nullable = false)
    private Long agencyId;
    @Basic
    @Column(name = "userid", nullable = false)
    private Long userId;
    @Basic
    @Column(name = "agencyname", nullable = true, length = 200)
    private String agencyName;
    @Basic
    @Column(name = "agencyemail", nullable = true, length = 100)
    private String agencyEmail;

    public AgencyEntity(Long userId, String agencyName, String agencyEmail) {
        this.userId = userId;
        this.agencyName = agencyName;
        this.agencyEmail = agencyEmail;
    }
}
