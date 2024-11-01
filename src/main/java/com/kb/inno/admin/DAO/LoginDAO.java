/**
 * 파일명     : LoginDAO.java
 * 화면명     : 로그인
 * 설명       : 관리자 로그인시 쿼리 호출
 * 최초개발일 : 2024.10.23
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.LoginDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginDAO {
    LoginDTO memberSelect(String memberId);
}
