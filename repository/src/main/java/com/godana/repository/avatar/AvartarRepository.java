package com.godana.repository.avatar;

import com.godana.domain.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvartarRepository extends JpaRepository<Avatar, String> {
}
