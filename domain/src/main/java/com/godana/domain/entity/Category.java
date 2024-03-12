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
@Table(name = "categories")
@Accessors(chain = true)
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Column(name = "titlenumerical_order")
    private int  titlenumericalOrder;

    @OneToMany(targetEntity = Place.class, fetch = FetchType.EAGER)
    private List<Place> place;

    @OneToMany(mappedBy = "category")
    private List<Post> postList;
}
