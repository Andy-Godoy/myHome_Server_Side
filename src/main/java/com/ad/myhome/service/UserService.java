package com.ad.myhome.service;

import com.ad.myhome.model.dto.UserDTO;
import com.ad.myhome.model.entity.UserEntity;
import com.ad.myhome.repository.UserRepository;
import com.ad.myhome.utils.common.CommonConstants;
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
        return new UserDTO(user.getUserId(), user.getUserName(), user.getUserEmail(), user.getUserCurrencyPreference(), user.getUserRole());
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

}
