package com.ad.myhome.service;

import com.ad.myhome.model.dto.AgencyDTO;
import com.ad.myhome.model.entity.AgencyEntity;
import com.ad.myhome.repository.AgencyRepository;
import com.ad.myhome.utils.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AgencyService {

    private final AgencyRepository agencyRepository;

    public AgencyService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    public List<AgencyEntity> getAgencies(Long userId) {
        return agencyRepository.findAgencyEntitiesByUserId(userId);
    }

    public AgencyEntity getOneAgency(Long agencyId){
        return agencyRepository.findAgencyEntityByAgencyId(agencyId);
    }

    public AgencyEntity saveOneAgency(Long userId, AgencyDTO body){
        if(agencyRepository.findAgencyEntityByAgencyNameAndAgencyEmail(body.getAgencyName(), body.getAgencyEmail())!=null){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    CommonConstants.FORBIDDEN_AGENCY_ALREDY_EXISTS
            );
        }
        AgencyEntity newAgency = new AgencyEntity(userId, body.getAgencyName(), body.getAgencyEmail());
        return agencyRepository.save(newAgency);
    }

    public AgencyEntity UpdateOneAgency(Long agencyId, Long userId, AgencyDTO body) {
        AgencyEntity agency = agencyRepository.findAgencyEntityByAgencyId(agencyId);
        if(agency==null){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                CommonConstants.NOTFOUND_AGENCY_DOESNT_EXISTS
            );
        }
        if(!agency.getUserId().equals(userId)){
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                CommonConstants.FORBIDDEN_AGENCY_UNEDITABLE
            );
        }
        agency.setAgencyName(body.getAgencyName());
        agency.setAgencyEmail(body.getAgencyEmail());
        return agencyRepository.save(agency);
    }

    public void DeleteOneAgency(Long agencyId, Long userId) {
        AgencyEntity agency = agencyRepository.findAgencyEntityByAgencyId(agencyId);
        if(agency==null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    CommonConstants.NOTFOUND_AGENCY_DOESNT_EXISTS
            );
        }
        if(!agency.getUserId().equals(userId)){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    CommonConstants.FORBIDDEN_AGENCY_UNEDITABLE
            );
        }
        agencyRepository.deleteById(agencyId);
    }

}

