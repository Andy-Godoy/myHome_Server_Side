package com.ad.myhome.controller;

import com.ad.myhome.model.dto.AgencyDTO;
import com.ad.myhome.model.entity.AgencyEntity;
import com.ad.myhome.service.AgencyService;
import com.ad.myhome.utils.CommonConstants;
import com.ad.myhome.utils.CommonFunctions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @GetMapping(value = "")
    public List<AgencyEntity> getAgencies(@RequestParam(name = "userId", required = true) Long userId){
        return agencyService.getAgencies(userId);
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
    public AgencyEntity getOneAgency(@PathVariable(name = "agencyId") Long agencyId){
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
        return agencyService.UpdateOneAgency(agencyId, userId, body);
    }

    @DeleteMapping(value = "/{agencyId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void DeleteOneAgency(
            @PathVariable(name = "agencyId") Long agencyId,
            @RequestParam(name = "userId") Long userId) throws ResponseStatusException {
        if(CommonFunctions.isMissing(userId) || CommonFunctions.isMissing(agencyId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
        agencyService.DeleteOneAgency(agencyId, userId);
    }

}
