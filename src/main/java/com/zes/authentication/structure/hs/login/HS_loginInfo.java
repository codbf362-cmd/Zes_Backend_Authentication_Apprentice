package com.zes.authentication.structure.hs.login;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hs_login_info")
@Getter
@Setter
@NoArgsConstructor  
@AllArgsConstructor
@Builder
public class HS_loginInfo {

    @Id
    @Column(name = "id", length = 50, nullable = false)
    private String id;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "company_code", length = 50, nullable = false)
    private String companyCode;

    @Column(name = "authority", length = 50, nullable = false)
    private String authority;

    @Column(name = "modified_date", nullable = false)
    private String modifiedDate;

    @Column(name = "statement", length = 20, nullable = false)
    private String statement;
}