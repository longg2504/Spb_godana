package com.godana.domain.dto.place;

import com.godana.domain.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PlaceUpReqDTO {
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
    public LocationRegion toLocationRegion(Long id) {
        return new LocationRegion()
                .setId(id)
                .setProvinceId(provinceId)
                .setProvinceName(provinceName)
                .setDistrictId(districtId)
                .setDistrictName(districtName)
                .setWardId(wardId)
                .setWardName(wardName)
                .setAddress(address)
                ;
    }
    public Contact toContact(Long id){
        return new Contact()
                .setId(id)
                .setEmail(email)
                .setWebsite(website)
                .setPhone(phone)
                .setOpenTime(LocalTime.parse(openTime))
                .setCloseTime(LocalTime.parse(closeTime))
                ;

    }
    public Place toPlace(Long id, Category category, LocationRegion locationRegion, User user, Contact contact){
        return new Place()
                .setId(id)
                .setTitle(placeTitle)
                .setContent(content)
                .setLongitude(longitude)
                .setLatitude(latitude)
                .setCategory(category)
                .setLocationRegion(locationRegion)
                .setUser(user)
                .setContact(contact);
    }
}
