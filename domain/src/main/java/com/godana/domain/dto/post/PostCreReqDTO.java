package com.godana.domain.dto.post;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PostCreReqDTO {
    private String postTitle;
    private String content;
    private Long userId;
    private Long categoryId;
    private List<MultipartFile> images;
}
