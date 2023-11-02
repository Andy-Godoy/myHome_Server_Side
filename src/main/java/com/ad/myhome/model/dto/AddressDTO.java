package com.ad.myhome.model.dto;

import com.ad.myhome.model.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String addressName;
    private int addressNumber;
    private Integer addressFloor;
    private String addressUnit;
    private String addressNeighbourhood;
    private String addressCity;
    private String addressState;
    private String addressCountry;
    private double addressLatitude;
    private double addressLongitude;

    public AddressDTO(AddressEntity address) {
        this.addressName = address.getAddressName();
        this.addressNumber = address.getAddressNumber();
        this.addressFloor = address.getAddressFloor();
        this.addressUnit = address.getAddressUnit();
        this.addressNeighbourhood = address.getAddressNeighbourhood();
        this.addressCity = address.getAddressCity();
        this.addressState = address.getAddressState();
        this.addressCountry = address.getAddressCountry();
        this.addressLatitude = address.getAddressLatitude();
        this.addressLongitude = address.getAddressLongitude();
    }
}
