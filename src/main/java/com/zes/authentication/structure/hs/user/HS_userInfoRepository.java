package com.zes.authentication.structure.hs.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface HS_userInfoRepository extends JpaRepository<HS_userInfo, String> {

    Optional<HS_userInfo> findByIdAndStatement(String id, String statement);
    Page<HS_userInfo> findAllByStatement(String statement, Pageable pageable);
    Page<HS_userInfo> findAllByStatementAndCompanyCode(String statement, String companyCode, Pageable pageable);

    @Query(
            value =
                    "select u.id " +
                            "from hs_user_info u " +
                            "where u.statement = 'active' " +
                            "and (:companyCode is null or :companyCode = '' or u.company_code = :companyCode) " +
                            "and (:keyword is null or :keyword = '' " +
                            "     or u.id like concat('%', :keyword, '%') " +
                            "     or u.name like concat('%', :keyword, '%'))",
            nativeQuery = true
    )
    List<String> systemUserSearchIds(
            @Param("keyword") String keyword,
            @Param("companyCode") String companyCode
    );
}
