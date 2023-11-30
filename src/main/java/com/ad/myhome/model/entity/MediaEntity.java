package com.ad.myhome.model.entity;

import com.ad.myhome.utils.enums.SourceType;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mediaid", nullable = false)
    private Long mediaId;
    @Basic
    @Column(name = "mediasourceid", nullable = false)
    private Long mediaSourceId;
    @Enumerated(EnumType.STRING)
    @Column(name = "mediasourcetype", nullable = true, length = 100)
    private SourceType mediaSourceType;
    @Basic
    @Column(name = "mediaurl", nullable = true, length = -1)
    private String mediaUrl;

    public MediaEntity(Long sourceId, SourceType sourceType, String url) {
        this.mediaSourceId = sourceId;
        this.mediaSourceType = sourceType;
        this.mediaUrl = url;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Long getMediaSourceId() {
        return mediaSourceId;
    }

    public void setMediaSourceId(Long mediaSourceId) {
        this.mediaSourceId = mediaSourceId;
    }

    public SourceType getMediaSourceType() {
        return mediaSourceType;
    }

    public void setMediaSourceType(SourceType mediaSourceType) {
        this.mediaSourceType = mediaSourceType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

}
