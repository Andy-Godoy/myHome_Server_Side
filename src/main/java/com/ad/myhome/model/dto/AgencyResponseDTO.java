package com.ad.myhome.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyResponseDTO {
    private Long agencyId;
    private Long userId;
    private String agencyName;
    private String agencyEmail;
    private Float agencyRating;
}
