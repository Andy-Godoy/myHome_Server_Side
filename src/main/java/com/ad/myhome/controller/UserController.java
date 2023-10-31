package com.ad.myhome.controller;

import com.ad.myhome.model.dto.BasicCredentialsDTO;
import com.ad.myhome.model.dto.GoogleCredentialsDTO;
import com.ad.myhome.model.dto.UserDTO;
import com.ad.myhome.model.entity.UserEntity;
import com.ad.myhome.service.UserService;
import com.ad.myhome.utils.common.CommonConstants;
import com.ad.myhome.utils.common.CommonFunctions;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final String CLASS_NAME = this.getClass().getSimpleName();
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/test")
    public String test(){
        return CLASS_NAME.concat(" esta funcionando ok!");
    }

    @GetMapping(value = "/{userId}")
    public UserDTO getUser(
            @PathVariable(name = "userId", required = true) Long userId) throws ResponseStatusException {
        return userService.getOneUser(userId);
    }

    @PostMapping(value = "/logins")
    public UserDTO loginUser(
            @RequestBody Map<String, Object> body) {
        if(body != null && body.containsKey(CommonConstants.PARAMETER_EMAIL) && body.containsKey(CommonConstants.PARAMETER_PASSWORD)){
            BasicCredentialsDTO credentials = new BasicCredentialsDTO(
                    body.get(CommonConstants.PARAMETER_EMAIL).toString(), body.get(CommonConstants.PARAMETER_PASSWORD).toString());
            return userService.logins(credentials);
        } else if (body != null && body.containsKey(CommonConstants.PARAMETER_EMAIL) && body.containsKey(CommonConstants.PARAMETER_USERID) && body.containsKey(CommonConstants.PARAMETER_NAME) && body.containsKey(CommonConstants.PARAMETER_IMAGE) ) {
            GoogleCredentialsDTO credentials = new GoogleCredentialsDTO(
                    body.get(CommonConstants.PARAMETER_USERID).toString(), body.get(CommonConstants.PARAMETER_EMAIL).toString(), body.get(CommonConstants.PARAMETER_NAME).toString(), body.get(CommonConstants.PARAMETER_IMAGE).toString());
            return userService.logins(credentials);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
    }

    @PostMapping(value = "/passwords")
    public UserDTO resetPassword(
            @RequestBody BasicCredentialsDTO body) {

        if(!StringUtils.hasText(body.getUserEmail()) || !StringUtils.hasText(body.getUserPassword())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
        return userService.resetPassword(body);
    }


    @PutMapping(value = "/{userId}")
    public UserDTO updateUser(
            @PathVariable(name = "userId", required = true) Long userId,
            @RequestBody UserEntity body) throws ResponseStatusException {
        if(CommonFunctions.isMissing(userId) || body == null){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
        return userService.updateUser(userId, body);
    }

    @PostMapping(value = "")
    public UserDTO registerUser(
            @RequestBody BasicCredentialsDTO body) {
        return userService.registerUser(body);
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(
            @PathVariable(name = "userId") Long userId) throws ResponseStatusException {
        if(CommonFunctions.isMissing(userId)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER
            );
        }
        userService.deleteUser(userId);
    }

}
