package com.godana.api;

import com.godana.domain.dto.category.CategoryCreReqDTO;
import com.godana.domain.dto.category.CategoryCreResDTO;
import com.godana.domain.dto.category.CategoryDTO;
import com.godana.domain.dto.rating.RatingCreReqDTO;
import com.godana.domain.entity.Category;
import com.godana.exception.DataInputException;
import com.godana.exception.EmailExistsException;
import com.godana.exception.ResourceNotFoundException;
import com.godana.service.category.ICategoryService;
import com.godana.utils.AppUtils;
import com.godana.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryAPI {
    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        List<CategoryDTO> categoryDTOList = iCategoryService.findAllCategoryDTO();
        if(categoryDTOList.isEmpty()){
            throw new ResourceNotFoundException("Không có danh mục nào vui lòng kiểm tra lại hệ thống");
        }
        return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getByIdCategory(@PathVariable("categoryId") String categoryIdStr) {

        if (!validateUtils.isNumberValid(categoryIdStr)) {
            throw new DataInputException("Mã danh mục không hợp lệ");
        }
        Long categoryId = Long.parseLong(categoryIdStr);

        Category category = iCategoryService.findByIdAndDeletedFalse(categoryId).orElseThrow(() -> {
            throw new DataInputException("Mã danh mục không tồn tại");
        });

        CategoryDTO categoryDTO = category.toCategoryDTO();
        return new ResponseEntity<>(categoryDTO,HttpStatus.OK);

    }

    @PostMapping()
    public ResponseEntity<?> createCategory(@RequestBody CategoryCreReqDTO categoryCreReqDTO, BindingResult bindingResult){
        new CategoryCreReqDTO().validate(categoryCreReqDTO, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Boolean existsBytitle = iCategoryService.existsByTitle(categoryCreReqDTO.getTitle());

        if(existsBytitle){
            throw new EmailExistsException("Danh mục đã tồn tại vui lòng xem lại!!!");
        }

        CategoryCreResDTO categoryCreResDTO = iCategoryService.createCategory(categoryCreReqDTO);
        return new ResponseEntity<>(categoryCreResDTO, HttpStatus.OK);
    }

}
