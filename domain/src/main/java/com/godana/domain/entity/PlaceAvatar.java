package com.godana.domain.entity;

import com.godana.domain.dto.avatar.AvatarReqDTO;
import com.godana.domain.dto.avatar.AvatarResDTO;
import com.godana.domain.dto.placeAvatar.PlaceAvatarDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="place_avatar")
@Accessors(chain = true)
public class PlaceAvatar {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_folder")
    private String fileFolder;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "cloud_id")
    private String cloudId;

    private  Integer width;

    private Integer height;

    @OneToOne
    @JoinColumn(name="place_id" , referencedColumnName = "id" )
    private Place place;


    public PlaceAvatarDTO toPlaceAvatarDTO() {
        return new PlaceAvatarDTO()
                .setId(id)
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl)
                .setCloudId(cloudId)
                .setWidth(width)
                .setHeight(height);
    }

    public AvatarResDTO toPlaceAvatarResDTO() {
        return new AvatarResDTO()
                .setId(id)
                .setFileName(fileName)
                .setFileFolder(fileFolder)
                .setFileUrl(fileUrl)
                .setCloudId(cloudId)
                .setWidth(width)
                .setHeight(height)
                ;
    }
}
