package com.ad.myhome.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "media", schema = "public", catalog = "myhome")
public class MediaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "mediaid", nullable = false)
    private int mediaid;
    @Basic
    @Column(name = "mediasourceid", nullable = false)
    private int mediasourceid;
    @Basic
    @Column(name = "mediatype", nullable = true, length = 100)
    private String mediatype;
    @Basic
    @Column(name = "mediafile", nullable = true, length = -1)
    private String mediafile;

}
