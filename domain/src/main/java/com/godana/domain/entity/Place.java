package com.godana.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name ="places")
@Accessors(chain = true)
public class Place extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(length = 50)
    private String longitude;

    @Column(length = 50)
    private String latitude;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name="location_region_id", referencedColumnName = "id" , nullable = false)
    private LocationRegion locationRegion;

    @OneToMany(mappedBy = "place")
    private List<Rating> ratingList;

    @OneToMany(mappedBy = "place")
    private List<NearbyPlace> nearbyPlaceList;

}
