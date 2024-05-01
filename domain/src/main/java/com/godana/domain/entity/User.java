package com.godana.domain.entity;

import com.godana.domain.dto.user.UserDTO;
import com.godana.domain.dto.user.UserResDTO;
import com.godana.domain.enums.EUserStatus;
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
@Table(name="users")
@Accessors(chain = true)
public class  User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", length = 50)
    private String fullName;

    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @OneToOne
    @JoinColumn(name = "user_avatar_id" , referencedColumnName = "id")
    private UserAvatar userAvatar;

    @Enumerated(EnumType.STRING)
    private EUserStatus status;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    @OneToMany(mappedBy = "user")
    private List<Like> likeList;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Place> places;

    public UserDTO toUserDTO(){
        return new UserDTO()
                .setId(id)
                .setUsername(username)
                .setRole(role.toRoleDTO())
                .setAvatar(userAvatar)
                ;
    }

    public UserResDTO toUserResDTO(){
        return new UserResDTO()
                .setId(id)
                .setEmail(email)
                .setFullName(fullName)
                .setRole(role.toRoleDTO())
                .setAvatar(userAvatar)
                .setUsername(username)
                .setStatus(String.valueOf(status))
                ;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", fullname= '" + fullName + '\'' +
                ", email= '" + email + '\'' +
                '}';
    }

}
