package com.ad.myhome.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltersDTO {
    private Boolean isFavorite;
    private Long agencyId;
    private Long userId;
    private Double userLatitude;
    private Double userLongitude;

    private String localidad; //falta este
    private String provincia; //falta este
    private String pais; //falta este
    private int cantidadBanios;
    private int cantidadAmbientes;
    private int cantidadCuartos;
    private int precioMax;
    private int precioMin;
    private String[] propertyAmenities;
    private String propertyType;
    private String propertyStatus;
    private String propertyAge;
}
