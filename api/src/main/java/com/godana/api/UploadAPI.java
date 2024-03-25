package com.godana.api;

import com.godana.service.upload.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/upload")
public class UploadAPI {
    @Autowired
    private IUploadService iUploadService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") List<MultipartFile> multipartFiles,
                                           @RequestParam Map<String, String> options) {
        try {
            List<Map> mapList = new ArrayList<>();

            for (MultipartFile item : multipartFiles) {
                Map result = iUploadService.uploadImage(item, options);
                mapList.add(result);
            }

            return ResponseEntity.ok(mapList);
        } catch (IOException e) {
            // Xử lý lỗi tại đây
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/destroy")
    public ResponseEntity<Map> destroyImage(@RequestParam("publicId") String publicId,
                                            @RequestParam Map<String, String> options) {
        try {
            Map result = iUploadService.destroyImage(publicId, options);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            // Xử lý lỗi tại đây
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

