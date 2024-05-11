package com.godana.domain.dto.post;

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
public class PostUpReqDTO {
    private String postTitle;
    private String content;
    private Long categoryId;
    private List<String> listIdAvatarCurrrent;
    private List<MultipartFile> images;
}
