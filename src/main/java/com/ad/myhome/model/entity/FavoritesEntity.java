package com.ad.myhome.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favorites", schema = "public", catalog = "myhome")
public class FavoritesEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "favoritesid", nullable = false)
    private long favoriteId;

    @Basic
    @Column(name = "userid", nullable = false)
    private long userId;
    @Basic
    @Column(name = "propertyid", nullable = false)
    private long propertyId;

    public FavoritesEntity(Long userId, Long propertyId) {
        this.userId = userId;
        this.propertyId = propertyId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

}
