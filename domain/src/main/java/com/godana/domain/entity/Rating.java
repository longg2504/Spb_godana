package com.godana.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="ratings")
@Accessors(chain = true)
public class Rating extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "place_id" ,referencedColumnName = "id", nullable = false)
    private Place place;


    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id" , nullable = false)
    private User user;

    private double rating;

}
