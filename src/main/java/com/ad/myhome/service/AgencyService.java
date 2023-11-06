package com.ad.myhome.service;

import com.ad.myhome.model.dto.AgencyDTO;
import com.ad.myhome.model.dto.AgencyResponseDTO;
import com.ad.myhome.model.entity.AgencyEntity;
import com.ad.myhome.repository.AgencyRepository;
import com.ad.myhome.repository.ReviewRepository;
import com.ad.myhome.utils.common.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AgencyService {

    private final AgencyRepository agencyRepository;
    private final ReviewRepository reviewRepository;

    public AgencyService(AgencyRepository agencyRepository, ReviewRepository reviewRepository) {
        this.agencyRepository = agencyRepository;
        this.reviewRepository = reviewRepository;
    }

    public AgencyResponseDTO getOneAgency(Long agencyId){
        Float agencyRating = reviewRepository.getAverageScoreByAgencyId(agencyId);
        AgencyEntity agency = agencyRepository.findAgencyEntityByAgencyId(agencyId);
        return new AgencyResponseDTO(agency.getAgencyId(), agency.getUserId(), agency.getAgencyName(), agency.getAgencyEmail(), agencyRating);
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

    public AgencyEntity updateOneAgency(Long agencyId, Long userId, AgencyDTO body) {
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

    public void deleteOneAgency(Long agencyId, Long userId) {
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

