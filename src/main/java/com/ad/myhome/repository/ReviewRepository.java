package com.ad.myhome.repository;

import com.ad.myhome.model.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findReviewEntitiesByAgencyId(Long agencyId);
    ReviewEntity findReviewEntityByAgencyIdAndUserId(Long agencyId, Long userId);
    @Query(value = "SELECT AVG(e.reviewScore) FROM ReviewEntity e WHERE e.agencyId = ?1")
    Float getAverageScoreByAgencyId(Long agencyId);

}
