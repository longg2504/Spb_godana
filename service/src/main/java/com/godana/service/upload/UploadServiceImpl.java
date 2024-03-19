package com.godana.service.upload;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class UploadServiceImpl implements IUploadService {
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map uploadImage(MultipartFile multipartFile, Map options) throws IOException {
        return cloudinary.uploader().upload(multipartFile.getBytes(), options);
    }

    @Override
    public List<Map> uploadImages(List<MultipartFile> multipartFiles, Map options) throws IOException {
        List<Map> uploadResults = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), options);
            uploadResults.add(uploadResult);
        }

        return uploadResults;
    }

    @Override
    public Map destroyImage(String publicId, Map options) throws IOException {
        return cloudinary.uploader().destroy(publicId, options);
    }



}
