/**
 * 파일명     : LoginService.java
 * 화면명     : 로그인
 * 설명       : 관리자 로그인, 로그아웃
 * 최초개발일  : 2024.10.24
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.LoginDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    // DAO 연결
    private final LoginDAO loginDAO;

}
