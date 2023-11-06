package com.ad.myhome.repository;

import com.ad.myhome.model.entity.AgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {

    AgencyEntity findAgencyEntityByUserId(Long userId);
    AgencyEntity findAgencyEntityByAgencyId(Long agencyId);
    AgencyEntity findAgencyEntityByAgencyNameAndAgencyEmail(String agencyName, String agencyEmail);

}
