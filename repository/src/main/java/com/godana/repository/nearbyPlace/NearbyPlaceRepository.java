package com.godana.repository.nearbyPlace;

import com.godana.domain.entity.NearbyPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NearbyPlaceRepository extends JpaRepository<NearbyPlace,Long> {
}
