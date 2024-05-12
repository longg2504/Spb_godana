package com.godana.repository.user;



import com.godana.domain.dto.place.PlaceCountDTO;
import com.godana.domain.dto.report.I6MonthAgoReportDTO;
import com.godana.domain.dto.user.UserCountDTO;
import com.godana.domain.dto.user.UserResDTO;
import com.godana.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

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
            "ua " +
            ") " +
            "FROM User AS u " +
            "LEFT JOIN UserAvatar AS ua " +
            "ON ua.id = u.userAvatar.id " +
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
            "ua " +
            ") " +
            "FROM User AS u " +
            "LEFT JOIN UserAvatar AS ua " +
            "ON ua.id = u.userAvatar.id " +
            "WHERE (:search IS NULL OR u.fullName LIKE %:search% " +
            "OR u.email LIKE %:search% " +
            "OR u.username LIKE %:search%) " +
            "AND u.deleted = true " +
            "AND u.role.id = 2 "
    )
    Page<UserResDTO> findUserBanBySearch(@Param("search") String search, Pageable pageable);

    @Query("SELECT NEW com.godana.domain.dto.user.UserCountDTO (" +
            "count(u.id)" +
            ") " +
            "FROM User AS u " +
            "WHERE u.deleted = false "
    )
    UserCountDTO countUser();

    @Query(value = "SELECT * FROM v_get_users_last_6_months", nativeQuery = true)
    List<I6MonthAgoReportDTO> getUserReport6Months();
}
