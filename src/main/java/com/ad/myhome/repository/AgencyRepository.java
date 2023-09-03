package com.ad.myhome.repository;

import com.ad.myhome.model.entity.AgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {

    List<AgencyEntity> findAgencyEntitiesByUserId(Long userId);
    AgencyEntity findAgencyEntityByAgencyId(Long agencyId);
    AgencyEntity findAgencyEntityByAgencyNameAndAgencyEmail(String agencyName, String agencyEmail);

}
