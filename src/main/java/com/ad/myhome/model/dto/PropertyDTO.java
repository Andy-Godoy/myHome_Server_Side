package com.ad.myhome.model.dto;

import com.ad.myhome.model.entity.AddressEntity;
import com.ad.myhome.model.entity.PropertyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {

    private Long propertyId;
    private Long agencyId;
    private String agencyImage;
    private String propertyType;
    private String propertyStatus;
    private Integer propertyPrice;
    private Integer propertyExpenses;
    private Integer propertyRoomQuantity;
    private Integer propertyBedroomQuantity;
    private Integer propertyBathroomQuantity;
    private Integer propertyGarageQuantity;
    private Boolean propertyHasGarage;
    private Boolean propertyHasBalcony;
    private Boolean propertyHasStorage;
    private Boolean propertyHasTerrace;
    private String propertyPosition;
    private String propertyOrientation;
    private String propertyAge;
    private String[] propertyAmenities;
    private String propertyDescription;
    private Integer propertyCoveredM2;
    private Integer propertySemiCoveredM2;
    private Integer propertyUncoveredM2;
    private String[] propertyImages;
    private AddressDTO propertyAddress;
    private boolean propertyIsFavorite;

    public PropertyDTO(PropertyEntity property, AddressEntity address, String[] urls, boolean isFavorite, String aganecyImage) {
        this.propertyId = property.getPropertyId();
        this.agencyId = property.getAgencyId();
        this.agencyImage =aganecyImage;
        this.propertyType = property.getPropertyType();
        this.propertyStatus = property.getPropertyStatus();
        this.propertyPrice = property.getPropertyPrice();
        this.propertyExpenses = property.getPropertyExpenses();
        this.propertyRoomQuantity = property.getPropertyRoomQuantity();
        this.propertyBedroomQuantity = property.getPropertyBedroomQuantity();
        this.propertyBathroomQuantity = property.getPropertyBathroomQuantity();
        this.propertyGarageQuantity = property.getPropertyGarageQuantity();
        this.propertyHasGarage = property.getPropertyHasGarage();
        this.propertyHasBalcony = property.getPropertyHasBalcony();
        this.propertyHasStorage = property.getPropertyHasStorage();
        this.propertyHasTerrace = property.getPropertyHasTerrace();
        this.propertyPosition = property.getPropertyPosition();
        this.propertyOrientation = property.getPropertyOrientation();
        this.propertyAge = property.getPropertyAge();
        this.propertyAmenities = property.getPropertyAmenities().split(",\\s*");
        this.propertyImages = urls;
        this.propertyDescription = property.getPropertyDescription();
        this.propertyCoveredM2 = property.getPropertyCoveredM2();
        this.propertySemiCoveredM2 = property.getPropertySemiCoveredM2();
        this.propertyUncoveredM2 = property.getPropertyUncoveredM2();
        this.propertyAddress = new AddressDTO(address);
        this.propertyIsFavorite = isFavorite;
    }
}
