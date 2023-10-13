package com.ad.myhome.service;

import com.ad.myhome.model.dto.BasicCredentialsDTO;
import com.ad.myhome.model.dto.CredentialsDTO;
import com.ad.myhome.model.dto.UserDTO;
import com.ad.myhome.model.entity.UserEntity;
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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        user.setUserName(body.getUserEmail());
        user.setUserEmail(body.getUserEmail());
        user.setUserPassword(body.getUserPassword());
        user.setUserRole(RoleType.AGENCY);
        user.setUserCurrencyPreference(CurrencyType.USD);
        return new UserDTO(userRepository.save(user));
    }

    /**
     * Login usuario común o de inmobiliaria
     * El usuario comùn existe o no existe
     * El usuario de google en caso de no existir, se crea
     * @param body Descripción del primer parámetro
     * @return Descripción del valor de retorno
     * @throws ResponseStatusException en caso de no existir usuario común
     */
    public UserDTO logins(CredentialsDTO body) {
        UserEntity user = userRepository.findUserEntityByUserEmail(body.getUserEmail());
        UserDTO userDTO;

        if(user != null){
            userDTO = new UserDTO(user.getUserId(),user.getUserCurrencyPreference(),user.getUserRole());
        }

        else{
            if(body.getUserId() != null){
                BasicCredentialsDTO basicCredentialsDTO = new BasicCredentialsDTO(body.getUserEmail(), body.getUserPassword());
                userDTO = registerUser(basicCredentialsDTO);

                return new UserDTO(userDTO.getUserId(),userDTO.getUserCurrencyPreference(),userDTO.getUserRole());
            }

            else {
                throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    CommonConstants.NOTFOUND_USER_DOESNT_EXISTS
                );
            }
        }

        return userDTO;
    }
}
