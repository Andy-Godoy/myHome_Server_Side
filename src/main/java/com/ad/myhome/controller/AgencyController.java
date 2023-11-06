package com.ad.myhome.controller;

import com.ad.myhome.model.dto.AgencyDTO;
import com.ad.myhome.model.dto.AgencyResponseDTO;
import com.ad.myhome.model.entity.AgencyEntity;
import com.ad.myhome.service.AgencyService;
import com.ad.myhome.utils.common.CommonConstants;
import com.ad.myhome.utils.common.CommonFunctions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/api/v1/agencies")
public class AgencyController {

    private final String CLASS_NAME = this.getClass().getSimpleName();
    private final AgencyService agencyService;

    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @GetMapping(value = "/test")
    public String test(){
        return CLASS_NAME.concat(" esta funcionando ok!");
    }

    @PostMapping(value = "")
    public AgencyEntity saveOneAgency(
            @RequestParam(name = "userId", required = true) Long userId,
            @RequestBody AgencyDTO body) throws ResponseStatusException {
        if(CommonFunctions.isMissing(userId) || body.getAgencyName().isEmpty() || body.getAgencyEmail().isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
        return agencyService.saveOneAgency(userId, body);
    }

    @GetMapping(value="/{agencyId}")
    public AgencyResponseDTO getOneAgency(@PathVariable(name = "agencyId") Long agencyId){
        return agencyService.getOneAgency(agencyId);
    }

    @PutMapping(value = "/{agencyId}")
    public AgencyEntity updateOneAgency(
            @PathVariable(name = "agencyId") Long agencyId,
            @RequestParam(value = "userId") Long userId,
            @RequestBody AgencyDTO body) throws ResponseStatusException {
        if(CommonFunctions.isMissing(userId) || CommonFunctions.isMissing(agencyId) || body.getAgencyName().isEmpty() || body.getAgencyEmail().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
        return agencyService.updateOneAgency(agencyId, userId, body);
    }

    @DeleteMapping(value = "/{agencyId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOneAgency(
            @PathVariable(name = "agencyId") Long agencyId,
            @RequestParam(name = "userId") Long userId) throws ResponseStatusException {
        if(CommonFunctions.isMissing(userId) || CommonFunctions.isMissing(agencyId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
        agencyService.deleteOneAgency(agencyId, userId);
    }

}
