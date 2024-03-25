package com.godana.repository.placeAvatar;

import com.godana.domain.entity.PlaceAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceAvatarRepository extends JpaRepository<PlaceAvatar, String> {
}
