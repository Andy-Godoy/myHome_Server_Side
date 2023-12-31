package com.ad.myhome.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews", schema = "public", catalog = "myhome")
public class ReviewEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "reviewid", nullable = false)
    private Long reviewId;
    @Basic
    @Column(name = "userid", nullable = false)
    private Long userId;
    @Basic
    @Column(name = "agencyid", nullable = false)
    private Long agencyId;
    @Basic
    @Min(value = 1, message = "El valor minimo de una reseña es 1.")
    @Max(value = 5, message = "El valor minimo de una reseña es 5.")
    @Column(name = "reviewscore", nullable = false)
    private int reviewScore;
    @Basic
    @Column(name = "reviewcomment", nullable = true, length = -1)
    private String reviewComment;

    public ReviewEntity(Long userId, Long agencyId, int reviewScore, String reviewComment) {
        this.userId = userId;
        this.agencyId = agencyId;
        this.reviewScore = reviewScore;
        this.reviewComment = reviewComment;
    }
}
