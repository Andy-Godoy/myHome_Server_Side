package com.ad.myhome.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "properties", schema = "public", catalog = "myhome")
public class PropertyEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "propertyid", nullable = false)
    private int propertyid;
    @Basic
    @Column(name = "agencyid", nullable = false)
    private int agencyid;
    @Basic
    @Column(name = "propertytype", nullable = false, length = 20)
    private String propertytype;
    @Basic
    @Column(name = "propertystatus", nullable = true, length = 50)
    private String propertystatus;
    @Basic
    @Column(name = "propertyprice", nullable = true)
    private Integer propertyprice;
    @Basic
    @Column(name = "propertyexpenses", nullable = true)
    private Integer propertyexpenses;
    @Basic
    @Column(name = "propertyroomquantity", nullable = true)
    private Integer propertyroomquantity;
    @Basic
    @Column(name = "propertybedroomquantity", nullable = true)
    private Integer propertybedroomquantity;
    @Basic
    @Column(name = "propertybathroomquantity", nullable = true)
    private Integer propertybathroomquantity;
    @Basic
    @Column(name = "propertygaragequantity", nullable = true)
    private Integer propertygaragequantity;
    @Basic
    @Column(name = "propertyhasgarage", nullable = true)
    private Boolean propertyhasgarage;
    @Basic
    @Column(name = "propertyhasbalcony", nullable = true)
    private Boolean propertyhasbalcony;
    @Basic
    @Column(name = "propertyhasstorage", nullable = true)
    private Boolean propertyhasstorage;
    @Basic
    @Column(name = "propertyposition", nullable = true, length = 50)
    private String propertyposition;
    @Basic
    @Column(name = "propertyorientation", nullable = true, length = 2)
    private String propertyorientation;
    @Basic
    @Column(name = "propertyage", nullable = true, length = 100)
    private String propertyage;
    @Basic
    @Column(name = "propertyamenities", nullable = true)
    private Object propertyamenities;
    @Basic
    @Column(name = "propertydescription", nullable = true, length = -1)
    private String propertydescription;
    @Basic
    @Column(name = "propertycoveredm2", nullable = true)
    private Integer propertycoveredm2;
    @Basic
    @Column(name = "propertysemicoveredm2", nullable = true)
    private Integer propertysemicoveredm2;
    @Basic
    @Column(name = "propertyuncoveredm2", nullable = true)
    private Integer propertyuncoveredm2;
    @Basic
    @Column(name = "propertyaddressid", nullable = true)
    private Integer propertyaddressid;

}
