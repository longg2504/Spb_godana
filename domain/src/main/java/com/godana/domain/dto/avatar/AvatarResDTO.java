package com.godana.domain.dto.avatar;

import com.godana.domain.entity.Avatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AvatarResDTO {
    private String id;
    private String fileName;
    private String fileFolder;
    private String fileUrl;
    private String cloudId;
    private Integer width;
    private Integer height;

    public Avatar toAvatar() {
        return new Avatar()
                .setId(id)
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl)
                .setCloudId(cloudId)
                .setWidth(width)
                .setHeight(height);
    }
}
