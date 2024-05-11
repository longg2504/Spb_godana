package com.godana.domain.dto.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCountDTO {
    private Long countPost;

    public PostCountDTO(Long countPost) {
        this.countPost = countPost;
    }
}
