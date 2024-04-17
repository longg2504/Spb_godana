package com.godana.repository.user;



import com.godana.domain.dto.user.UserResDTO;
import com.godana.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    User getByUsername(String username);
    @Query("SELECT NEW com.godana.domain.dto.user.UserResDTO " +
            "(" +
            "u.id, " +
            "u.username," +
            "u.email," +
            "u.fullName, " +
            "u.role, " +
            "u.status, " +
            "u.userAvatar " +
            ") " +
            "FROM User AS u " +
            "WHERE (:search IS NULL OR u.fullName LIKE %:search% " +
            "OR u.email LIKE %:search% " +
            "OR u.username LIKE %:search%) " +
            "AND u.deleted = false " +
            "AND u.role.id = 2 "
    )
    Page<UserResDTO> findUserBySearch(@Param("search") String search, Pageable pageable);

    @Query("SELECT NEW com.godana.domain.dto.user.UserResDTO " +
            "(" +
            "u.id, " +
            "u.username," +
            "u.email," +
            "u.fullName, " +
            "u.role, " +
            "u.status, " +
            "u.userAvatar " +
            ") " +
            "FROM User AS u " +
            "WHERE (:search IS NULL OR u.fullName LIKE %:search% " +
            "OR u.email LIKE %:search% " +
            "OR u.username LIKE %:search%) " +
            "AND u.deleted = true " +
            "AND u.role.id = 2 "
    )
    Page<UserResDTO> findUserBanBySearch(@Param("search") String search, Pageable pageable);
}
