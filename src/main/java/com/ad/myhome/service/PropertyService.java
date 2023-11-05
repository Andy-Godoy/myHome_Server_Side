package com.ad.myhome.service;

import com.ad.myhome.model.dto.FiltersDTO;
import com.ad.myhome.model.dto.PropertyDTO;
import com.ad.myhome.model.dto.PropertySummaryDTO;
import com.ad.myhome.model.entity.AddressEntity;
import com.ad.myhome.model.entity.PropertyEntity;
import com.ad.myhome.repository.AddressRepository;
import com.ad.myhome.repository.PropertyRepository;
import com.ad.myhome.utils.common.CommonConstants;
import com.ad.myhome.utils.common.CommonFunctions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyService {

    private PropertyRepository propertyRepository;
    private AddressRepository addressRepository;

    public PropertyService(PropertyRepository propertyRepository, AddressRepository addressRepository) {
        this.propertyRepository = propertyRepository;
        this.addressRepository = addressRepository;
    }

    public PropertyDTO saveProperty(Long agencyId, PropertyDTO body){
        AddressEntity address = addressRepository.findAddressEntitiesByAddressDTO(
                body.getPropertyAddress().getAddressName(), body.getPropertyAddress().getAddressNumber(), body.getPropertyAddress().getAddressFloor(), body.getPropertyAddress().getAddressUnit(),
                body.getPropertyAddress().getAddressCity(),body.getPropertyAddress().getAddressState(), body.getPropertyAddress().getAddressCountry()
        );
        if(address == null){
            address = new AddressEntity(body.getPropertyAddress());
            addressRepository.save(address);
        }
        PropertyEntity property = new PropertyEntity(body);
        property.setAgencyId(agencyId);
        property.setPropertyAddressId(address.getAddressId());
        propertyRepository.save(property);

        return new PropertyDTO(property, address);
    }

    public PropertyDTO getProperty(Long propertyId) {
        PropertyEntity property = propertyRepository.findPropertyEntityByPropertyId(propertyId);
        if(property == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    CommonConstants.NOTFOUND_PROPERTY_DOESNT_EXISTS
            );
        }
        AddressEntity address = addressRepository.findAddressEntitiesByAddressId(property.getPropertyAddressId());
        return new PropertyDTO(property, (address == null) ? new AddressEntity() : address);
    }

    public void deleteProperty(Long propertyId, Long agencyId){
        PropertyEntity property = propertyRepository.findPropertyEntityByPropertyId(propertyId);
        if(property == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    CommonConstants.NOTFOUND_PROPERTY_DOESNT_EXISTS
            );
        }
        if(!property.getAgencyId().equals(agencyId)){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    CommonConstants.FORBIDDEN_PROPERTY_UNEDITABLE
            );
        }

        propertyRepository.deleteById(propertyId);
    }

    public PropertyDTO updateProperty(Long propertyId, Long agencyId, PropertyDTO body) {
        PropertyEntity property = propertyRepository.findPropertyEntityByPropertyId(propertyId);
        if(property == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    CommonConstants.NOTFOUND_PROPERTY_DOESNT_EXISTS
            );
        }
        if(!property.getAgencyId().equals(agencyId)){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    CommonConstants.FORBIDDEN_PROPERTY_UNEDITABLE
            );
        }
        AddressEntity address = addressRepository.findAddressEntitiesByAddressId(property.getPropertyAddressId());
        if(address == null){
            address = new AddressEntity(body.getPropertyAddress());
            addressRepository.save(address);
            property.setPropertyAddressId(address.getAddressId());
        } else {
            address.update(body.getPropertyAddress());
        }
        property.update(body);
        propertyRepository.save(property);

        return new PropertyDTO(property, address);
    }

    public List<PropertySummaryDTO> getProperties(FiltersDTO filters) {
        List<PropertyEntity> propertyList = propertyRepository.findAll();
        List<PropertyEntity> filteredList = new ArrayList<>();
        if(!CommonFunctions.isMissing(filters.getAgencyId())){
            filteredList = propertyList.stream().filter(p -> p.getAgencyId().equals(filters.getAgencyId())).toList();
            propertyList = filteredList;
        }

        List<PropertySummaryDTO> properties = new ArrayList<>();
        for(PropertyEntity property : propertyList){
            AddressEntity address = addressRepository.findAddressEntitiesByAddressId(property.getPropertyAddressId());
            properties.add(new PropertySummaryDTO(property, address));
        }
        return properties;
    }

}
