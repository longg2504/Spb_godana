package com.godana.domain.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyCreReqDTO {
    private String content;
    private String postId;
    private String userId;
}
