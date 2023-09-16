package com.ad.myhome.repository;

import com.ad.myhome.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserEntityByUserId(Long userId);
    UserEntity findUserEntityByUserNameAndUserEmail(String userName, String userEmail);
}