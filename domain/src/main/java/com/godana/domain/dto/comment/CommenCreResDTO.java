package com.godana.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CommenCreResDTO {
    private Long commentId;
    private String content;
    private Long commentParentId;
    private Long userId;
    private Long postId;
    private Date createdAt;
}
