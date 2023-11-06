package com.ad.myhome.service;

import com.ad.myhome.model.dto.BasicCredentialsDTO;
import com.ad.myhome.model.dto.GoogleCredentialsDTO;
import com.ad.myhome.model.dto.UserDTO;
import com.ad.myhome.model.entity.AgencyEntity;
import com.ad.myhome.model.entity.UserEntity;
import com.ad.myhome.repository.AgencyRepository;
import com.ad.myhome.repository.UserRepository;
import com.ad.myhome.utils.common.CommonConstants;
import com.ad.myhome.utils.enums.CurrencyType;
import com.ad.myhome.utils.enums.RoleType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AgencyRepository agencyRepository;

    public UserService(UserRepository userRepository, AgencyRepository agencyRepository) {
        this.userRepository = userRepository;
        this.agencyRepository = agencyRepository;
    }

    public UserDTO getOneUser(Long userId) throws ResponseStatusException {
        UserEntity user = userRepository.findUserEntityByUserId(userId);
        if(user == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    CommonConstants.NOTFOUND_USER_DOESNT_EXISTS
            );
        }
        return new UserDTO(user);
    }

    public UserDTO updateUser(Long userId, UserEntity body) {
        UserEntity user = userRepository.findUserEntityByUserId(userId);
        if(user == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    CommonConstants.NOTFOUND_USER_DOESNT_EXISTS
            );
        }
        if(body.getUserName()!=null && !body.getUserName().isBlank()) user.setUserName(body.getUserName());
        user.setUserCurrencyPreference(body.getUserCurrencyPreference());
        return new UserDTO(userRepository.save(user));
    }

    public void deleteUser(Long userId) {
        UserEntity user = userRepository.findUserEntityByUserId(userId);
        if(user == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    CommonConstants.NOTFOUND_USER_DOESNT_EXISTS
            );
        }
        userRepository.deleteById(userId);
    }

    public UserDTO registerUser(BasicCredentialsDTO body) {
        UserEntity user = userRepository.findUserEntityByUserEmail(body.getUserEmail());
        if(user != null){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    CommonConstants.FORBIDDEN_USER_ALREDY_EXISTS
            );
        }
        user = new UserEntity();
        user.setUserName(body.getUserEmail().substring(0,body.getUserEmail().indexOf("@")));
        user.setUserEmail(body.getUserEmail());
        user.setUserPassword(body.getUserPassword());
        user.setUserRole(RoleType.AGENCY);
        user.setUserCurrencyPreference(CurrencyType.USD);
        userRepository.save(user);

        AgencyEntity agency = new AgencyEntity(user.getUserId(), user.getUserName().concat(" Propiedades"), user.getUserEmail());
        agencyRepository.save(agency);

        return new UserDTO(user);
    }

    public UserDTO logins(GoogleCredentialsDTO body) {
        UserEntity user = userRepository.findUserEntityByUserEmail(body.getUserEmail());
        if(user == null){
            user = new UserEntity();
            user.setUserName(body.getUserName());
            user.setUserEmail(body.getUserEmail());
            user.setUserPassword("");
            user.setUserImage(body.getUserImage());
            user.setUserRole(RoleType.CONSUMER);
            user.setUserCurrencyPreference(CurrencyType.USD);
            userRepository.save(user);
        }
        return new UserDTO(user);
    }

    public UserDTO logins(BasicCredentialsDTO body) {
        UserEntity user = userRepository.findUserEntityByUserEmail(body.getUserEmail());
        if(user == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    CommonConstants.NOTFOUND_USER_DOESNT_EXISTS
            );
        }
        if(!user.getUserPassword().equals(body.getUserPassword()) && user.getUserRole().equals(RoleType.AGENCY)){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    CommonConstants.FORBIDDEN_USER_INVALID_CREDENTIALS
            );
        }
        AgencyEntity agency = agencyRepository.findAgencyEntityByUserId(user.getUserId());
        UserDTO userDTO = new UserDTO(user);
        userDTO.setAgencyId(agency.getAgencyId());
        return userDTO;
    }

    public UserDTO resetPassword(BasicCredentialsDTO body) {
        UserEntity user = userRepository.findUserEntityByUserEmail(body.getUserEmail());
        if(user == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    CommonConstants.NOTFOUND_USER_DOESNT_EXISTS
            );
        }
        user.setUserPassword(body.getUserPassword());
        userRepository.save(user);
        return new UserDTO(user);
    }
}
