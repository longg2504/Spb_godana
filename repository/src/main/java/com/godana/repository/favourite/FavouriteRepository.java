package com.godana.repository.favourite;

import com.godana.domain.dto.favourite.FavouriteDTO;
import com.godana.domain.entity.Favourite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    List<Favourite> findAllByUserId(Long userId);

    Optional<Favourite> findByUserIdAndPlaceId(Long userId, Long placeId);

}
