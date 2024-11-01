/**
 * 파일명     : LoginService.java
 * 화면명     : 로그인
 * 설명       : 관리자 화면 접속시 로그인 및 세션 생성
 *              로그아웃 세션 삭제
 * 최초개발일 : 2024.10.31
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.LoginDAO;
import com.kb.inno.admin.DTO.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginDAO loginDAO;

    /**
     * 로그인 한 관리자의 계정이 있는지 확인 후 세션을 생성한다.
     * @param memberId
     * @return
     */
    public LoginDTO memberSelect(String memberId) {
        LoginDTO loginDTO = loginDAO.memberSelect(memberId);

        if (loginDTO == null) {
            return null;
        }

        return loginDTO;
    }
}
