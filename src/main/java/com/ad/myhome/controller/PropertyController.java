package com.ad.myhome.controller;

import com.ad.myhome.model.dto.AddressDTO;
import com.ad.myhome.model.dto.FiltersDTO;
import com.ad.myhome.model.dto.PropertyDTO;
import com.ad.myhome.model.dto.PropertySummaryDTO;
import com.ad.myhome.service.PropertyService;
import com.ad.myhome.utils.common.CommonConstants;
import com.ad.myhome.utils.common.CommonFunctions;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/properties")
public class PropertyController {

    private final String CLASS_NAME = this.getClass().getSimpleName();

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping(value = "/test")
    public String test(){
        return CLASS_NAME.concat(" esta funcionando ok!");
    }

    @PostMapping(value = "")
    public PropertyDTO saveProperty(
            @RequestParam(name = "agencyId") Long agencyId,
            @RequestBody PropertyDTO body) throws ResponseStatusException {
        if(!isValidProperty(body)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
        return propertyService.saveProperty(agencyId, body);
    }

    @PostMapping(value = "/filters")
    public List<PropertySummaryDTO> getProperties(
            @RequestBody FiltersDTO filters) throws ResponseStatusException {
        return propertyService.getProperties(filters);
    }

    @GetMapping(value = "/{propertyId}")
    public PropertyDTO getProperty(
            @PathVariable("propertyId") Long propertyId) throws ResponseStatusException {
        if(CommonFunctions.isMissing(propertyId)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
        return propertyService.getProperty(propertyId);
    }

    @PutMapping(value = "/{propertyId}")
    public PropertyDTO putProperty(
            @PathVariable("propertyId") Long propertyId,
            @RequestParam(name = "agencyId") Long agencyId,
            @RequestBody PropertyDTO body) throws ResponseStatusException {
        if(CommonFunctions.isMissing(propertyId) || !isValidProperty(body)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
        return propertyService.updateProperty(propertyId, agencyId, body);
    }

    @DeleteMapping(value = "/{propertyId}")
    public void deleteProperty(
            @PathVariable("propertyId") Long propertyId,
            @RequestParam(name = "agencyId") Long agencyId) throws ResponseStatusException {
        if(CommonFunctions.isMissing(propertyId) || CommonFunctions.isMissing(agencyId)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
        propertyService.deleteProperty(propertyId, agencyId);
    }

    @PostMapping(value = "/{propertyId}/favorites")
    public void updateFavorite(
            @PathVariable("propertyId") Long propertyId,
            @RequestParam("userId") Long userId) throws ResponseStatusException {
        if(CommonFunctions.isMissing(propertyId) || CommonFunctions.isMissing(userId)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
        propertyService.updateFavorite(userId, propertyId);
    }

    @PostMapping(value = "/{propertyId}/reservations")
    public void reserveProperty(
            @PathVariable("propertyId") Long propertyId) throws ResponseStatusException {
        if(CommonFunctions.isMissing(propertyId)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
        propertyService.reserveProperty(propertyId);
    }

    private Boolean isValidProperty(PropertyDTO property){
        return (
            StringUtils.hasText(property.getPropertyType()) &&
            property.getPropertyStatus() != null &&
            !CommonFunctions.isMissing(property.getPropertyPrice()) &&
            !CommonFunctions.isMissing(property.getPropertyRoomQuantity()) &&
            !CommonFunctions.isMissing(property.getPropertyCoveredM2()) &&
            isValidAddress(property.getPropertyAddress())
        );
    }

    private Boolean isValidAddress(AddressDTO address){
        return (
            StringUtils.hasText(address.getAddressName()) &&
            !CommonFunctions.isMissing(address.getAddressNumber()) &&
            StringUtils.hasText(address.getAddressCity()) &&
            StringUtils.hasText(address.getAddressState()) &&
            StringUtils.hasText(address.getAddressCountry())
        );
    }

}
