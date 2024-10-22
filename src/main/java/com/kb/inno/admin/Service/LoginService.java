package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.LoginDAO;
import com.kb.inno.admin.DTO.LoginDTO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private LoginDAO loginDAO;

    public LoginDTO login(LoginDTO userInfo) {
        Optional<User> loginUser = loginDAO.login(userInfo.getLoginId());
        if(loginUser.isPresent()) {
            return null;
        }

        User user = loginUser.get();

        if(!user.getPassword().equals(userInfo.getPassword())) {
            return null;
        }

        return userInfo;
    }
}
