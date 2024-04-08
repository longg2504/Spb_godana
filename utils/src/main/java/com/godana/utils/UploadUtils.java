package com.godana.utils;

import com.godana.domain.entity.PostAvatar;

import com.godana.domain.entity.PlaceAvatar;
import com.godana.domain.entity.UserAvatar;
import com.godana.exception.DataInputException;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UploadUtils {
    public static final String IMAGE_UPLOAD_FOLDER = "postImage";
    public static final String IMAGE_UPLOAD_FOLDER_USER = "user";

    public static final String IMAGE_UPLOAD_FOLDER_PLACE = "place";



    public Map buildImageUploadParams(PostAvatar postImage) {
        if (postImage == null || postImage.getId() == null)
            throw new DataInputException("Không thể upload hình ảnh chưa được lưu");

        String publicId = String.format("%s/%s", IMAGE_UPLOAD_FOLDER, postImage.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }


    public Map buildImageUploadParamsUser(UserAvatar userAvatar) {
        if (userAvatar == null || userAvatar.getId() == null)
            throw new DataInputException("Không thể upload hình ảnh chưa được lưu");

        String publicId = String.format("%s/%s", IMAGE_UPLOAD_FOLDER_USER, userAvatar.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

    public Map buildImageUploadParamsPlace(PlaceAvatar placeAvatar) {
        if (placeAvatar == null || placeAvatar.getId() == null)
            throw new DataInputException("Không thể upload hình ảnh chưa được lưu");

        String publicId = String.format("%s/%s", IMAGE_UPLOAD_FOLDER_PLACE, placeAvatar.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }


//    public Map buildImageDestroyParams(Product product, String publicId) {
//        if (product == null || product.getId() == null)
//            throw new DataInputException("Không thể destroy hình ảnh của sản phẩm không xác định");
//
//        return ObjectUtils.asMap(
//                "public_id", publicId,
//                "overwrite", true,
//                "resource_type", "image"
//        );
//    }

}