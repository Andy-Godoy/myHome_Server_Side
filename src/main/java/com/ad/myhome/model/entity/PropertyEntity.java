package com.ad.myhome.model.entity;

import com.ad.myhome.model.dto.PropertyDTO;
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
    private Long propertyId;
    @Basic
    @Column(name = "agencyid", nullable = false)
    private Long agencyId;
    @Basic
    @Column(name = "propertytype", nullable = false, length = 20)
    private String propertyType;
    @Basic
    @Column(name = "propertystatus", nullable = true, length = 50)
    private String propertyStatus;
    @Basic
    @Column(name = "propertyprice", nullable = true)
    private Integer propertyPrice;
    @Basic
    @Column(name = "propertyexpenses", nullable = true)
    private Integer propertyExpenses;
    @Basic
    @Column(name = "propertyroomquantity", nullable = true)
    private Integer propertyRoomQuantity;
    @Basic
    @Column(name = "propertybedroomquantity", nullable = true)
    private Integer propertyBedroomQuantity;
    @Basic
    @Column(name = "propertybathroomquantity", nullable = true)
    private Integer propertyBathroomQuantity;
    @Basic
    @Column(name = "propertygaragequantity", nullable = true)
    private Integer propertyGarageQuantity;
    @Basic
    @Column(name = "propertyhasgarage", nullable = true)
    private Boolean propertyHasGarage;
    @Basic
    @Column(name = "propertyhasbalcony", nullable = true)
    private Boolean propertyHasBalcony;
    @Basic
    @Column(name = "propertyhasstorage", nullable = true)
    private Boolean propertyHasStorage;
    @Basic
    @Column(name = "propertyposition", nullable = true, length = 50)
    private String propertyPosition;
    @Basic
    @Column(name = "propertyorientation", nullable = true, length = 2)
    private String propertyOrientation;
    @Basic
    @Column(name = "propertyage", nullable = true, length = 100)
    private String propertyAge;
    @Basic
    @Column(name = "propertyamenities", nullable = true)
    private String propertyAmenities;
    @Basic
    @Column(name = "propertydescription", nullable = true, length = -1)
    private String propertyDescription;
    @Basic
    @Column(name = "propertycoveredm2", nullable = true)
    private Integer propertyCoveredM2;
    @Basic
    @Column(name = "propertysemicoveredm2", nullable = true)
    private Integer propertySemiCoveredM2;
    @Basic
    @Column(name = "propertyuncoveredm2", nullable = true)
    private Integer propertyUncoveredM2;
    @Basic
    @Column(name = "propertyaddressid", nullable = true)
    private Long propertyAddressId;

    public PropertyEntity(PropertyDTO body) {
        this.propertyType = body.getPropertyType();
        this.propertyStatus = body.getPropertyStatus();
        this.propertyPrice = body.getPropertyPrice();
        this.propertyExpenses = body.getPropertyExpenses();
        this.propertyRoomQuantity = body.getPropertyRoomQuantity();
        this.propertyBedroomQuantity = body.getPropertyBedroomQuantity();
        this.propertyBathroomQuantity = body.getPropertyBathroomQuantity();
        this.propertyGarageQuantity = body.getPropertyGarageQuantity();
        this.propertyHasGarage = body.getPropertyHasGarage();
        this.propertyHasBalcony = body.getPropertyHasBalcony();
        this.propertyHasStorage = body.getPropertyHasStorage();
        this.propertyPosition = body.getPropertyPosition();
        this.propertyOrientation = body.getPropertyOrientation();
        this.propertyAge = body.getPropertyAge();
        this.propertyAmenities = String.join(", ", body.getPropertyAmenities());
        this.propertyDescription = body.getPropertyDescription();
        this.propertyCoveredM2 = body.getPropertyCoveredM2();
        this.propertySemiCoveredM2 = body.getPropertySemiCoveredM2();
        this.propertyUncoveredM2 = body.getPropertyUncoveredM2();
    }

    public void update(PropertyDTO body) {
        this.propertyType = body.getPropertyType();
        this.propertyStatus = body.getPropertyStatus();
        this.propertyPrice = body.getPropertyPrice();
        this.propertyExpenses = body.getPropertyExpenses();
        this.propertyRoomQuantity = body.getPropertyRoomQuantity();
        this.propertyBedroomQuantity = body.getPropertyBedroomQuantity();
        this.propertyBathroomQuantity = body.getPropertyBathroomQuantity();
        this.propertyGarageQuantity = body.getPropertyGarageQuantity();
        this.propertyHasGarage = body.getPropertyHasGarage();
        this.propertyHasBalcony = body.getPropertyHasBalcony();
        this.propertyHasStorage = body.getPropertyHasStorage();
        this.propertyPosition = body.getPropertyPosition();
        this.propertyOrientation = body.getPropertyOrientation();
        this.propertyAge = body.getPropertyAge();
        this.propertyAmenities = String.join(", ", body.getPropertyAmenities());
        this.propertyDescription = body.getPropertyDescription();
        this.propertyCoveredM2 = body.getPropertyCoveredM2();
        this.propertySemiCoveredM2 = body.getPropertySemiCoveredM2();
        this.propertyUncoveredM2 = body.getPropertyUncoveredM2();
    }
}
