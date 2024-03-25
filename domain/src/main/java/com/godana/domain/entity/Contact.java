package com.godana.domain.entity;

import com.godana.domain.dto.contact.ContactDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="contacts")
@Accessors(chain = true)
public class Contact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    @Column(length = 10)
    private String phone;

    private String website;

    private LocalTime openTime;

    private LocalTime closeTime;

    @OneToOne
    @JoinColumn(name="place_id", referencedColumnName = "id" , nullable = false)
    private Place place;


    public ContactDTO toContactDTO() {
        return new ContactDTO()
                .setId(id)
                .setPhone(phone)
                .setWebsite(website)
                .setEmail(email)
                .setOpenTime(openTime)
                .setCloseTime(closeTime);
    }

}
