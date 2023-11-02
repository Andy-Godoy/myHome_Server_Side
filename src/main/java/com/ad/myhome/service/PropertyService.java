package com.ad.myhome.service;

import com.ad.myhome.model.dto.PropertyDTO;
import com.ad.myhome.model.entity.AddressEntity;
import com.ad.myhome.model.entity.PropertyEntity;
import com.ad.myhome.repository.AddressRepository;
import com.ad.myhome.repository.PropertyRepository;
import org.springframework.stereotype.Service;

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

}
