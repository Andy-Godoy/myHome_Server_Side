package com.ad.myhome.repository;

import com.ad.myhome.model.entity.MediaEntity;
import com.ad.myhome.utils.enums.SourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<MediaEntity, Long> {
    List<MediaEntity> findMediaEntitiesByMediaSourceIdAndMediaSourceType(Long mediaSourceId, SourceType mediaSourceType);
    MediaEntity findMediaEntityByMediaSourceIdAndMediaSourceType(Long mediaSourceId, SourceType mediaSourceType);

    void deleteMediaEntitiesByMediaSourceIdAndMediaSourceType(Long mediaSourceId, SourceType mediaSourceType);
}
