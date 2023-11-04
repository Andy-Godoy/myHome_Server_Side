package com.ad.myhome.repository;

import com.ad.myhome.model.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    @Query("SELECT e FROM AddressEntity e " +
            "WHERE e.addressName = :addressName AND e.addressNumber = :addressNumber AND e.addressFloor = :addressFloor AND e.addressUnit = :addressUnit " +
            "AND e.addressCity = :addressCity AND e.addressState = :addressState AND e.addressCountry = :addressCountry")
    AddressEntity findAddressEntitiesByAddressDTO(
            @Param("addressName") String addressName, @Param("addressNumber") Integer addressNumber, @Param("addressFloor") Integer addressFloor, @Param("addressUnit") String addressUnit,
            @Param("addressCity") String addressCity, @Param("addressState") String addressState, @Param("addressCountry") String addressCountry);

    AddressEntity findAddressEntitiesByAddressId(Long propertyId);
}
