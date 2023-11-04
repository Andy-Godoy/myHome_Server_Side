package com.ad.myhome.model.entity;

import com.ad.myhome.model.dto.AddressDTO;
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
    private Long addressId;
    @Basic
    @Column(name = "propertyid", nullable = false)
    private int propertyId;
    @Basic
    @Column(name = "addressname", nullable = false, length = 100)
    private String addressName;
    @Basic
    @Column(name = "addressnumber", nullable = false)
    private int addressNumber;
    @Basic
    @Column(name = "addressfloor", nullable = true)
    private Integer addressFloor;
    @Basic
    @Column(name = "addressunit", nullable = true, length = 5)
    private String addressUnit;
    @Basic
    @Column(name = "addressneighbourhood", nullable = true, length = 100)
    private String addressNeighbourhood;
    @Basic
    @Column(name = "addresscity", nullable = true, length = 100)
    private String addressCity;
    @Basic
    @Column(name = "addressstate", nullable = true, length = 100)
    private String addressState;
    @Basic
    @Column(name = "addresscountry", nullable = true, length = 100)
    private String addressCountry;
    @Basic
    @Column(name = "addresslatitude", nullable = false, precision = 0)
    private double addressLatitude;
    @Basic
    @Column(name = "addresslongitude", nullable = false, precision = 0)
    private double addressLongitude;

    public AddressEntity(AddressDTO propertyAddress) {
        this.addressName = propertyAddress.getAddressName();
        this.addressNumber = propertyAddress.getAddressNumber();
        this.addressFloor = propertyAddress.getAddressFloor();
        this.addressUnit = propertyAddress.getAddressUnit();
        this.addressNeighbourhood = propertyAddress.getAddressNeighbourhood();
        this.addressCity = propertyAddress.getAddressCity();
        this.addressState = propertyAddress.getAddressState();
        this.addressCountry = propertyAddress.getAddressCountry();
        this.addressLatitude = propertyAddress.getAddressLatitude();
        this.addressLongitude = propertyAddress.getAddressLongitude();
    }

    public void update(AddressDTO propertyAddress) {
        this.addressName = propertyAddress.getAddressName();
        this.addressNumber = propertyAddress.getAddressNumber();
        this.addressFloor = propertyAddress.getAddressFloor();
        this.addressUnit = propertyAddress.getAddressUnit();
        this.addressNeighbourhood = propertyAddress.getAddressNeighbourhood();
        this.addressCity = propertyAddress.getAddressCity();
        this.addressState = propertyAddress.getAddressState();
        this.addressCountry = propertyAddress.getAddressCountry();
        this.addressLatitude = propertyAddress.getAddressLatitude();
        this.addressLongitude = propertyAddress.getAddressLongitude();
    }

}
