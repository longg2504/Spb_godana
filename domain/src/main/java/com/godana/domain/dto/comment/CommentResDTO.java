package com.godana.domain.dto.comment;

import com.godana.domain.dto.user.UserDTO;
import com.godana.domain.dto.user.UserResDTO;
import com.godana.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class CommentResDTO {
    private Long commentId;
    private String content;
    private UserResDTO user;
    private Date createdAt;
    private Long countReply;

    public CommentResDTO(Long commentId, String content, User user, Date createdAt, Long countReply) {
        this.commentId = commentId;
        this.content = content;
        this.user = user.toUserResDTO();
        this.createdAt = createdAt;
        this.countReply = countReply;
    }
}
