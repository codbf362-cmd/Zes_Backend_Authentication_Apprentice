package com.zes.authentication.structure.hs.user;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hs_user_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HS_userInfo {

    @Id
    @Column(name = "id", length = 50, nullable = false)
    private String id;

    @Column(name = "company_code", length = 50, nullable = false)
    private String companyCode;

    @Column(name = "authorities_code", length = 50, nullable = false)
    private String authoritiesCode;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "employee_number", length = 50)
    private String employeeNumber;

    @Column(name = "user_position", length = 50)
    private String userPosition;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "profile_image", length = 255)
    private String profileImage;

    @Column(name = "notice", length = 255)
    private String notice;

    @Column(name = "modified_date", length = 20, nullable = false)
    private String modifiedDate;

    @Column(name = "statement", length = 20, nullable = false)
    private String statement;
}