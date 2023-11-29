package com.ad.myhome.repository;

import com.ad.myhome.model.entity.FavoritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository extends JpaRepository<FavoritesEntity, Long> {

    List<FavoritesEntity> findFavoritesEntitiesByUserId(Long userId);

    FavoritesEntity findFavoritesEntityByUserIdAndPropertyId(Long userId, Long propertyId);

}
