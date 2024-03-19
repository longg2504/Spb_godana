package com.godana.domain.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.annotation.processing.SupportedSourceVersion;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CategoryCreResDTO {
    private Long id;
    private String titlte;
    private int titleNumericalOrder;
}
