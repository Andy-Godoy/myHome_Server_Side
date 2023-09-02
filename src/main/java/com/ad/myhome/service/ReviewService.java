package com.ad.myhome.service;

import com.ad.myhome.model.dto.ReviewDTO;
import com.ad.myhome.model.entity.ReviewEntity;
import com.ad.myhome.repository.ReviewRepository;
import com.ad.myhome.utils.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewEntity> getReviews(Long agencyId){
        return reviewRepository.findReviewEntitiesByAgencyId(agencyId);
    }

    public ReviewEntity saveReview(Long agencyId, Long userId, ReviewDTO body)  {
        if(reviewRepository.findReviewEntityByAgencyIdAndUserId(agencyId, userId)!=null){
           throw new ResponseStatusException(HttpStatus.FORBIDDEN, CommonConstants.FORBIDDEN_REVIEW_ALREDY_SAVED);
        }
        ReviewEntity newReview = new ReviewEntity();
        newReview.setAgencyId(agencyId);
        newReview.setUserId(userId);
        newReview.setReviewScore(body.getReviewScore());
        newReview.setReviewComment(body.getReviewComment());

        return reviewRepository.save(newReview);

    }

}
