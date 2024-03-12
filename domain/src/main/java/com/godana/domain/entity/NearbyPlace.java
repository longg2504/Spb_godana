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
    private long id;

    @Column(name = "nearby_title")
    private String nearbyTitle;

    @Column(name = "nearby_longtitude", length = 50)
    private String nearbyLongtitude;

    @Column(name= "nearby_latitude", length = 50)
    private String nearbyLatitude;

    @ManyToOne
    @JoinColumn(name = "place_id" , referencedColumnName = "id" , nullable = false)
    private Place place;

}
