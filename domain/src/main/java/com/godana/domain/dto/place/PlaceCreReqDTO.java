package com.godana.domain.dto.place;

import com.godana.domain.dto.user.UserReqUpDTO;
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
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PlaceCreReqDTO implements Validator {
    @NotBlank(message = "Vui lòng nhập tên địa điểm")
    private String placeTitle;
    private String content;
    @NotBlank(message = "Vui lòng nhập kinh độ")
    private String longitude;
    @NotBlank(message = "Vui lòng nhập Vĩ độ")
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

    @Override
    public boolean supports(Class<?> clazz) {
        return PlaceCreReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PlaceCreReqDTO placeCreReqDTO = (PlaceCreReqDTO) target;
        String placeTitle = placeCreReqDTO.placeTitle;
        String longitude = placeCreReqDTO.longitude;
        String latitude = placeCreReqDTO.latitude;
        if (placeTitle.isEmpty()) {
            errors.rejectValue("placeTitle", "placeTitle.null", "Tên địa điểm không được phép rỗng");
            return;
        }


        if (longitude.isEmpty()) {
            errors.rejectValue("longitude", "longitude.null", "Kinh độ không được phép rỗng");

        }
        if (latitude.isEmpty()) {
            errors.rejectValue("latitude", "latitude.null", "Vĩ độ không được phép rỗng");

        }
    }
}