package com.ad.myhome.service;

import com.ad.myhome.model.dto.PropertyDTO;
import com.ad.myhome.model.entity.AddressEntity;
import com.ad.myhome.model.entity.PropertyEntity;
import com.ad.myhome.repository.AddressRepository;
import com.ad.myhome.repository.PropertyRepository;
import com.ad.myhome.utils.common.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        PropertyEntity property = propertyRepository.findPropertyEntitiesByPropertyId(propertyId);
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
        PropertyEntity property = propertyRepository.findPropertyEntitiesByPropertyId(propertyId);
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
        PropertyEntity property = propertyRepository.findPropertyEntitiesByPropertyId(propertyId);
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

}
