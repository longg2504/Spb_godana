package com.godana.domain.dto.place;

import com.godana.domain.entity.Contact;
import com.godana.domain.entity.LocationRegion;
import com.godana.domain.entity.Place;
import com.godana.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.cglib.core.Local;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PlaceCreReqDTO {
    private String placeTitle;
    private String content;
    private String longitude;
    private String latitude;
    private List<MultipartFile> placeAvatar;
    private Long categoryId;
    private String provinceId;
    private String provinceName;
    private String districtId;
    private String districtName;
    private String wardId;
    private String wardName;
    private String address;
    private String email;
    private String phone;
    private String website;
    private String openTime;
    private String closeTime;
    private Long userId;

    public LocationRegion toLocationRegion() {
        return new LocationRegion()
                .setProvinceId(provinceId)
                .setProvinceName(provinceName)
                .setDistrictId(districtId)
                .setDistrictName(districtName)
                .setWardId(wardId)
                .setWardName(wardName)
                .setAddress(address)
                ;
    }
    public Contact toContact(){
        return new Contact()
                .setEmail(email)
                .setWebsite(website)
                .setPhone(phone)
                .setOpenTime(LocalTime.parse(openTime))
                .setCloseTime(LocalTime.parse(closeTime))
                ;

    }
}