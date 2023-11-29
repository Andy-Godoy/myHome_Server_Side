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
    private String localidad;
    private String provincia;
    private String pais;
    private int cantidadBanios;
    private int cantidadAmbientes;
    private int cantidadCuatros;
    private int precioMax;
    private int precioMin;
    private String[] propertyAmenities;
    private String propertyType;
    private String propertyStatus;
    private String propertyAge;
}
