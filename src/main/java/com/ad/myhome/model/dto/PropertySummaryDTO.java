package com.ad.myhome.model.dto;

import com.ad.myhome.model.entity.AddressEntity;
import com.ad.myhome.model.entity.PropertyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertySummaryDTO {

    private Long propertyId;
    private Long agencyId;
    private String agencyImage;
    private Integer propertyPrice;
    private Integer propertyDimension;
    private Integer propertyBedroomQuantity;
    private String propertyDescription;
    private String propertyAddress;
    private String propertyNeighbourhood;
    private String propertyCity;
    private String propertyState;
    private String propertyCountry;
    private String[] propertyImages;
    private double propertyDistance;

    public PropertySummaryDTO(PropertyEntity property, AddressEntity address, String[] urls, String agencyImage) {
        this.propertyId = property.getPropertyId();
        this.agencyId = property.getAgencyId();
        this.agencyImage = agencyImage;
        this.propertyPrice = property.getPropertyPrice();
        this.propertyBedroomQuantity = property.getPropertyBedroomQuantity();
        this.propertyDescription = property.getPropertyDescription();
        this.propertyDimension =
                ((property.getPropertyCoveredM2() == null) ? 0 : property.getPropertyCoveredM2()) +
                ((property.getPropertySemiCoveredM2() == null) ? 0 : property.getPropertySemiCoveredM2()) +
                ((property.getPropertyUncoveredM2() == null) ? 0 : property.getPropertyUncoveredM2());
        this.propertyAddress = address.getAddressName().concat(" ").concat(String.valueOf(address.getAddressNumber()));
        this.propertyNeighbourhood = address.getAddressNeighbourhood();
        this.propertyCity = address.getAddressCity();
        this.propertyState = address.getAddressState();
        this.propertyCountry = address.getAddressCountry();
        this.propertyImages = urls;
        this.propertyDistance = 0;
    }

}
