package com.ad.myhome.service;

import com.ad.myhome.model.dto.ReviewDTO;
import com.ad.myhome.model.entity.ReviewEntity;
import com.ad.myhome.model.entity.UserEntity;
import com.ad.myhome.repository.ReviewRepository;
import com.ad.myhome.repository.UserRepository;
import com.ad.myhome.utils.common.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public List<ReviewDTO> getReviews(Long agencyId){
       List<ReviewEntity> reviews = reviewRepository.findReviewEntitiesByAgencyId(agencyId);
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
       for (ReviewEntity r : reviews) {
            UserEntity user = userRepository.findUserEntityByUserId(r.getUserId());
            reviewDTOList.add(new ReviewDTO(
                    r.getReviewScore(),
                    r.getReviewComment(),
                    (user==null)?"Usuario no disponible":user.getUserName(),
                    (user==null)?null:user.getUserImage()));
        }
        return reviewDTOList;
    }

    public ReviewEntity saveReview(Long agencyId, Long userId, ReviewDTO body)  {
        if(reviewRepository.findReviewEntityByAgencyIdAndUserId(agencyId, userId)!=null){
           throw new ResponseStatusException(HttpStatus.FORBIDDEN, CommonConstants.FORBIDDEN_REVIEW_ALREDY_SAVED);
        }
        ReviewEntity newReview = new ReviewEntity(userId, agencyId, body.getReviewScore(), body.getReviewComment());
        return reviewRepository.save(newReview);

    }

}
