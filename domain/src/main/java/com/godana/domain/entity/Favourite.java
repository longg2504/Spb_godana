package com.godana.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="favourites")
public class Favourite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="place_id" , referencedColumnName = "id", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name="user_id" , referencedColumnName = "id", nullable = false)
    private User user;
}
