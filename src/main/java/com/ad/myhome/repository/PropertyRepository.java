package com.ad.myhome.repository;

import com.ad.myhome.model.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

    PropertyEntity findPropertyEntityByPropertyId(Long propertyId);

}
