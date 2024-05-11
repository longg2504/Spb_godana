package com.godana.repository.rating;

import com.godana.domain.dto.place.PlaceCountDTO;
import com.godana.domain.dto.rating.RatingCountDTO;
import com.godana.domain.dto.rating.RatingDTO;
import com.godana.domain.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT NEW com.godana.domain.dto.rating.RatingDTO (" +
            "r.id, " +
            "r.content, " +
            "r.rating, " +
            "r.user," +
            "r.createdAt " +
            ") " +
            "FROM Rating AS r " +
            "WHERE r.place.id = :placeId " +
            "AND r.deleted = false "
    )
    List<RatingDTO> findAllByPlaceId(@Param("placeId") Long placeId);


    @Query("SELECT NEW com.godana.domain.dto.rating.RatingCountDTO (" +
            "count(r.id)" +
            ") " +
            "FROM Rating AS r " +
            "WHERE r.deleted = false "
    )
    RatingCountDTO countRating();

}
