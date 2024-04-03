package com.godana.domain.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ContactDTO {
    private Long id;
    private String email;
    private String phone;
    private String website;
    private LocalTime openTime;
    private LocalTime closeTime;
}
