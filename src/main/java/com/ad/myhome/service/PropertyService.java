package com.ad.myhome.service;

import com.ad.myhome.model.dto.FiltersDTO;
import com.ad.myhome.model.dto.PropertyDTO;
import com.ad.myhome.model.dto.PropertySummaryDTO;
import com.ad.myhome.model.entity.AddressEntity;
import com.ad.myhome.model.entity.MediaEntity;
import com.ad.myhome.model.entity.PropertyEntity;
import com.ad.myhome.repository.AddressRepository;
import com.ad.myhome.repository.MediaRepository;
import com.ad.myhome.repository.PropertyRepository;
import com.ad.myhome.utils.common.CommonConstants;
import com.ad.myhome.utils.common.CommonFunctions;
import com.ad.myhome.utils.enums.SourceType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final AddressRepository addressRepository;
    private final MediaRepository mediaRepository;

    public PropertyService(PropertyRepository propertyRepository, AddressRepository addressRepository, MediaRepository mediaRepository) {
        this.propertyRepository = propertyRepository;
        this.addressRepository = addressRepository;
        this.mediaRepository = mediaRepository;
    }

    @Transactional
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

        String[] urls = body.getPropertyImages();
        if(urls.length > 0){
            List<MediaEntity> propertyImagesList = new ArrayList<MediaEntity>();
            for (String url : urls) {
                propertyImagesList.add(new MediaEntity(property.getPropertyId(), SourceType.PROPERTY, url));
            }
            mediaRepository.saveAll(propertyImagesList);
        }
        return new PropertyDTO(property, address, urls);
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
        List<MediaEntity> medias = mediaRepository.findMediaEntitiesByMediaSourceIdAndMediaSourceType(propertyId, SourceType.PROPERTY);
        String[] urls = null;
        if(!medias.isEmpty()){
            urls = new String[medias.size()];
            for (int i = 0; i < medias.size(); i++) {
                urls[i] = medias.get(i).getMediaUrl();
            }
        }
        return new PropertyDTO(property, (address == null) ? new AddressEntity() : address, urls);
    }

    @Transactional
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
        mediaRepository.deleteMediaEntitiesByMediaSourceIdAndMediaSourceType(propertyId, SourceType.PROPERTY);
        propertyRepository.deleteById(propertyId);
    }

    @Transactional
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

        String[] urls = body.getPropertyImages();
        List<MediaEntity> medias = mediaRepository.findMediaEntitiesByMediaSourceIdAndMediaSourceType(propertyId, SourceType.PROPERTY);
        for (String url : urls) {
            if (!medias.stream().map(MediaEntity::getMediaUrl).toList().contains(url)) {
                medias.add(new MediaEntity(propertyId, SourceType.PROPERTY, url));
            }
        }
        medias = mediaRepository.saveAll(medias);
        for (MediaEntity media: medias) {
            if(!Arrays.stream(urls).toList().contains(media.getMediaUrl())){
                mediaRepository.delete(media);
            }
        }
        return new PropertyDTO(property, address, urls);
    }

    public List<PropertySummaryDTO> getProperties(FiltersDTO filters) {
        List<PropertyEntity> propertyList = propertyRepository.findAll();
        List<PropertyEntity> filteredList;
        if(Boolean.FALSE.equals(CommonFunctions.isMissing(filters.getAgencyId()))){
            filteredList = propertyList.stream().filter(p -> p.getAgencyId().equals(filters.getAgencyId())).toList();
            propertyList = filteredList;
        }

        List<PropertySummaryDTO> properties = new ArrayList<>();
        for(PropertyEntity property : propertyList){
            AddressEntity address = addressRepository.findAddressEntitiesByAddressId(property.getPropertyAddressId());
            List<MediaEntity> medias = mediaRepository.findMediaEntitiesByMediaSourceIdAndMediaSourceType(property.getPropertyId(), SourceType.PROPERTY);
            String[] urls = null;
            if(!medias.isEmpty()){
                urls = new String[medias.size()];
                for (int i = 0; i < medias.size(); i++) {
                    urls[i] = medias.get(i).getMediaUrl();
                }
            }

            properties.add(new PropertySummaryDTO(property, address, urls));
        }
        return properties;
    }

}
