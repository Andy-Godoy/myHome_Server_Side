package com.ad.myhome.service;

import com.ad.myhome.model.dto.FiltersDTO;
import com.ad.myhome.model.dto.PropertyDTO;
import com.ad.myhome.model.dto.PropertySummaryDTO;
import com.ad.myhome.model.entity.AddressEntity;
import com.ad.myhome.model.entity.FavoritesEntity;
import com.ad.myhome.model.entity.MediaEntity;
import com.ad.myhome.model.entity.PropertyEntity;
import com.ad.myhome.repository.*;
import com.ad.myhome.utils.common.CommonConstants;
import com.ad.myhome.utils.common.CommonFunctions;
import com.ad.myhome.utils.enums.SourceType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final AddressRepository addressRepository;
    private final MediaRepository mediaRepository;
    private final FavoritesRepository favoritesRepository;

    public PropertyService(PropertyRepository propertyRepository, AddressRepository addressRepository, MediaRepository mediaRepository, FavoritesRepository favoritesRepository) {
        this.propertyRepository = propertyRepository;
        this.addressRepository = addressRepository;
        this.mediaRepository = mediaRepository;
        this.favoritesRepository = favoritesRepository;
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
            List<MediaEntity> propertyImagesList = new ArrayList<>();
            for (String url : urls) {
                propertyImagesList.add(new MediaEntity(property.getPropertyId(), SourceType.PROPERTY, url));
            }
            mediaRepository.saveAll(propertyImagesList);
        }
        return new PropertyDTO(property, address, urls, false, null);
    }

    public PropertyDTO getProperty(Long propertyId, Long userId) {
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
        MediaEntity media = mediaRepository.findMediaEntityByMediaSourceIdAndMediaSourceType(property.getAgencyId(), SourceType.PROFILE);
        String agencyImage = (media!=null) ? media.getMediaUrl() : null;
        FavoritesEntity favorite = favoritesRepository.findFavoritesEntityByUserIdAndPropertyId(userId, propertyId);
        boolean isFavorite = (favorite!=null);
        return new PropertyDTO(property, (address == null) ? new AddressEntity() : address, urls, isFavorite, agencyImage);
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
        return new PropertyDTO(property, address, urls, false, null);
    }

    public List<PropertySummaryDTO> getProperties(FiltersDTO filters) {
        List<PropertyEntity> propertyList = propertyRepository.findAll();
        List<PropertyEntity> filteredList;
        if(filters.getIsFavorite() != null && filters.getIsFavorite()){
            List<Long> favoriteProperties = favoritesRepository.findFavoritesEntitiesByUserId(filters.getUserId())
                    .stream().map(fp -> fp.getPropertyId()).toList();
            filteredList = propertyList.stream().filter(p -> favoriteProperties.contains(p.getPropertyId())).toList();
            propertyList = filteredList;
        } else {
            if(Boolean.FALSE.equals(CommonFunctions.isMissing(filters.getAgencyId()))){
                filteredList = propertyList.stream().filter(p -> p.getAgencyId().equals(filters.getAgencyId())).toList();
                propertyList = filteredList;
            }
            if(Boolean.FALSE.equals(CommonFunctions.isMissing(filters.getCantidadBanios()))){
                filteredList = propertyList.stream().filter(p -> p.getPropertyBathroomQuantity().equals(filters.getCantidadBanios())).toList();
                propertyList = filteredList;
            }
            if(Boolean.FALSE.equals(CommonFunctions.isMissing(filters.getCantidadAmbientes()))){
                filteredList = propertyList.stream().filter(p -> p.getPropertyRoomQuantity().equals(filters.getCantidadAmbientes())).toList();
                propertyList = filteredList;
            }
            if(Boolean.FALSE.equals(CommonFunctions.isMissing(filters.getCantidadCuartos()))){
                filteredList = propertyList.stream().filter(p -> (p.getPropertyBedroomQuantity().equals(filters.getCantidadCuartos()))).toList();
                propertyList = filteredList;
            }
            if(Boolean.FALSE.equals(CommonFunctions.isMissing(filters.getPrecioMin()))){
                filteredList = propertyList.stream().filter(p -> (p.getPropertyPrice() >= filters.getPrecioMin())).toList();
                propertyList = filteredList;
            }
            if(Boolean.FALSE.equals(CommonFunctions.isMissing(filters.getPrecioMax()))){
                filteredList = propertyList.stream().filter(p -> p.getPropertyPrice() <= filters.getPrecioMax()).toList();
                propertyList = filteredList;
            }
            if(Boolean.FALSE.equals(!StringUtils.hasText(filters.getPropertyType()))){
                filteredList = propertyList.stream().filter(p -> p.getPropertyType().equals(filters.getPropertyType())).toList();
                propertyList = filteredList;
            }
            if(Boolean.FALSE.equals(!StringUtils.hasText(filters.getPropertyStatus()))){
                filteredList = propertyList.stream().filter(p -> p.getPropertyStatus().equals(filters.getPropertyStatus())).toList();
                propertyList = filteredList;
            }
            if(Boolean.FALSE.equals(!StringUtils.hasText(filters.getPropertyAge()))){
                filteredList = propertyList.stream().filter(p -> p.getPropertyAge().equals(filters.getPropertyAge())).toList();
                propertyList = filteredList;
            }
            if(Boolean.TRUE.equals(filters.getPropertyAmenities()!=null)){
                for(String amenity : filters.getPropertyAmenities()){
                    filteredList = propertyList.stream().filter(p -> p.getPropertyAmenities().contains(amenity)).toList();
                    propertyList = filteredList;
                }
            }

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
            MediaEntity media = mediaRepository.findMediaEntityByMediaSourceIdAndMediaSourceType(property.getAgencyId(), SourceType.PROFILE);
            String mediaUrls = (media != null) ? media.getMediaUrl() : "";

            PropertySummaryDTO ps = new PropertySummaryDTO(property, address, urls, mediaUrls);
            ps.setPropertyDistance(Math.sqrt(
                Math.pow(filters.getUserLatitude()-address.getAddressLatitude(),2) +
                Math.pow(filters.getUserLongitude()-address.getAddressLongitude(),2)
            ));

            properties.add(ps);
        }
        List<PropertySummaryDTO> filteredSummaryList;
        if(Boolean.TRUE.equals(StringUtils.hasText(filters.getLocalidad()))){
            filteredSummaryList = properties.stream().filter(p -> p.getPropertyCity().equals(filters.getLocalidad())).toList();
            properties = filteredSummaryList;
        }
        if(Boolean.TRUE.equals(StringUtils.hasText(filters.getProvincia()))){
            filteredSummaryList = properties.stream().filter(p -> p.getPropertyState().equals(filters.getProvincia())).toList();
            properties = filteredSummaryList;
        }
        if(Boolean.TRUE.equals(StringUtils.hasText(filters.getPais()))){
            filteredSummaryList = properties.stream().filter(p -> p.getPropertyCountry().equals(filters.getPais())).toList();
            properties = filteredSummaryList;
        }

        if(properties.size()>1){
            properties.sort(Comparator.comparingDouble(PropertySummaryDTO::getPropertyDistance));
        }

        return properties;

    }

    @Transactional
    public void updateFavorite(Long userId, Long propertyId) {
        FavoritesEntity favorite = favoritesRepository.findFavoritesEntityByUserIdAndPropertyId(userId, propertyId);
        if(favorite == null){
            favorite = new FavoritesEntity(userId, propertyId);
            favoritesRepository.save(favorite);
        }else{
            favoritesRepository.delete(favorite);
        }
    }

    @Transactional
    public void reserveProperty(Long propertyId) {
        PropertyEntity property = propertyRepository.findPropertyEntityByPropertyId(propertyId);
        if(property == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    CommonConstants.NOTFOUND_PROPERTY_DOESNT_EXISTS
            );
        }
        property.setPropertyStatus("Reservada");
        propertyRepository.save(property);
    }
}
