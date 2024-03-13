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
@Table(name = "comments")
@Accessors(chain = true)
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id" , nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "comment")
    private List<Reply> replies;
}
