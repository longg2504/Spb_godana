package com.godana.repository.category;

import com.godana.domain.dto.category.CategoryCountDTO;
import com.godana.domain.dto.category.CategoryDTO;
import com.godana.domain.dto.place.PlaceCountDTO;
import com.godana.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT NEW com.godana.domain.dto.category.CategoryDTO(ca.id,ca.title,ca.titlenumericalOrder,ca.svg) from Category as ca WHERE ca.deleted=false ")
    List<CategoryDTO> findAllCategoryDTO();

    Optional<Category> findByIdAndDeletedFalse(Long id);
    
    Boolean existsByTitle(String title);

    @Query("SELECT NEW com.godana.domain.dto.category.CategoryCountDTO (" +
            "count(c.id)" +
            ") " +
            "FROM Category AS c " +
            "WHERE c.deleted = false "
    )
    CategoryCountDTO countCategory();

}
