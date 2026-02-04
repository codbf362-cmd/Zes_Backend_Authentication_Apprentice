package com.zes.authentication.structure.hs.login;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HS_loginInfoRepository extends JpaRepository<HS_loginInfo, String> {

    Optional<HS_loginInfo> findByIdAndStatement(String id, String statement);
}