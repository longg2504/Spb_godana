package com.godana.service.category;

import com.godana.domain.dto.category.CategoryCountDTO;
import com.godana.domain.dto.category.CategoryCreReqDTO;
import com.godana.domain.dto.category.CategoryCreResDTO;
import com.godana.domain.dto.category.CategoryDTO;
import com.godana.domain.dto.report.ICountPlaceByCateReportDTO;
import com.godana.domain.entity.Category;
import com.godana.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CategoryServiceImpl implements ICategoryService{
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDTO> findAllCategoryDTO() {
        return categoryRepository.findAllCategoryDTO();
    }

    @Override
    public Optional<Category> findByIdAndDeletedFalse(Long id) {
        return categoryRepository.findByIdAndDeletedFalse(id);
    }

    @Override
    public Boolean existsByTitle(String title) {
        return categoryRepository.existsByTitle(title);
    }

    @Override
    public CategoryCreResDTO createCategory(CategoryCreReqDTO categoryCreReqDTO) {
        Category category = categoryCreReqDTO.toCategory();

        categoryRepository.save(category);

        category.setTitlenumericalOrder(category.getId());
        categoryRepository.save(category);

        CategoryCreResDTO categoryCreResDTO = category.toCategoryCreResDTO();
        categoryCreResDTO.setId(category.getId());
        categoryCreResDTO.setTitleNumericalOrder(category.getTitlenumericalOrder());
        categoryCreResDTO.setSvg(category.getSvg());
        return categoryCreResDTO;
    }

    @Override
    public CategoryCountDTO countCategory() {
        return categoryRepository.countCategory();
    }

    @Override
    public List<ICountPlaceByCateReportDTO> getCountPlaceByCategory() {
        return categoryRepository.getCountPlaceByCategory();
    }
}
