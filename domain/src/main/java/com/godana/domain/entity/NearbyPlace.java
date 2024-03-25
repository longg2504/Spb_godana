package com.godana.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="nearby_places")
@Accessors(chain = true)
public class NearbyPlace extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "place_id" , referencedColumnName = "id" , nullable = false)
    private Place place;

    //Place nearPlace
    @ManyToOne
    @JoinColumn(name = "near_by_place_id" , referencedColumnName = "id" , nullable = false)
    private Place nearByPlace;

}
