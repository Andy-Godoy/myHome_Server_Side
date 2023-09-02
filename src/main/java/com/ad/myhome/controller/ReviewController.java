package com.ad.myhome.controller;

import com.ad.myhome.model.dto.ReviewDTO;
import com.ad.myhome.model.entity.ReviewEntity;
import com.ad.myhome.service.ReviewService;
import com.ad.myhome.utils.CommonConstants;
import com.ad.myhome.utils.CommonFunctions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/reviews")
public class ReviewController {

    private final String CLASS_NAME = this.getClass().getSimpleName();
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(value = "/test")
    public String test(){
        return CLASS_NAME.concat(" esta funcionando ok!");
    }

    @GetMapping(value = "")
    public List<ReviewEntity> getReviews(@RequestParam(name = "agencyId") Long agencyId)
        throws ResponseStatusException{
        if (CommonFunctions.isMissing(agencyId)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    CommonConstants.BADREQUEST_MISSINGPARAMETER);
        }
        return reviewService.getReviews(agencyId);
    }

    @PostMapping(value = "")
    public ReviewEntity saveReview(
            @RequestParam(name = "agencyId") Long agencyId,
            @RequestParam(name = "userId") Long userId,
            @RequestBody ReviewDTO body) throws ResponseStatusException{
        if(CommonFunctions.isMissing(agencyId) || CommonFunctions.isMissing(userId) || CommonFunctions.isMissing(body.getReviewScore())){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                CommonConstants.BADREQUEST_MISSINGPARAMETER);
        }
        return reviewService.saveReview(agencyId, userId, body);
    }

}
