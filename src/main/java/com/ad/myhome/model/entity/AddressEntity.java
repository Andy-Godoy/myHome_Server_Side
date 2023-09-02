package com.ad.myhome.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses", schema = "public", catalog = "myhome")
public class AddressEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "addressid", nullable = false)
    private int addressid;
    @Basic
    @Column(name = "propertyid", nullable = false)
    private int propertyid;
    @Basic
    @Column(name = "addressname", nullable = false, length = 100)
    private String addressname;
    @Basic
    @Column(name = "addressnumber", nullable = false)
    private int addressnumber;
    @Basic
    @Column(name = "addressfloor", nullable = true)
    private Integer addressfloor;
    @Basic
    @Column(name = "addressunit", nullable = true, length = 5)
    private String addressunit;
    @Basic
    @Column(name = "addressneighbourhood", nullable = true, length = 100)
    private String addressneighbourhood;
    @Basic
    @Column(name = "addresscity", nullable = true, length = 100)
    private String addresscity;
    @Basic
    @Column(name = "addressstate", nullable = true, length = 100)
    private String addressstate;
    @Basic
    @Column(name = "addresscountry", nullable = true, length = 100)
    private String addresscountry;
    @Basic
    @Column(name = "addresslatitude", nullable = false, precision = 0)
    private double addresslatitude;
    @Basic
    @Column(name = "addresslongitude", nullable = false, precision = 0)
    private double addresslongitude;

}
