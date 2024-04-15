package com.godana.repository.place;

import com.godana.domain.dto.place.PlaceDTO;
import com.godana.domain.dto.placeAvatar.PlaceAvatarDTO;
import com.godana.domain.entity.Category;
import com.godana.domain.entity.Place;
import com.godana.domain.entity.PlaceAvatar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public interface PlaceRepository extends JpaRepository<Place,Long> {
    @Query("SELECT NEW com.godana.domain.dto.place.PlaceDTO (" +
            "p.id, " +
            "p.title, " +
            "p.content, " +
            "p.longitude, " +
            "p.latitude, " +
            "p.category, " +
            "p.locationRegion, " +
            "p.contact, " +
            "avg(r.rating)," +
            "count(r.id) " +
            ") " +
            "FROM Place AS p " +
            "JOIN Rating AS r " +
            "ON r.place.id = p.id " +
            "WHERE (:category IS NULL OR p.category = :category) " +
            "AND p.title LIKE %:search% " +
            "AND p.deleted = false " +
            "GROUP BY p.id "
    )
    Page<PlaceDTO> findAllByCategoryAndSearch(@Param("category") Category category, @Param("search") String search, Pageable pageable);

    Optional<Place> findPlaceByIdAndDeletedFalse(Long id);

    Optional<PlaceDTO> findPlaceById(Long id);
}
