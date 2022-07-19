package com.mysite.sbb33.controller;

import com.mysite.sbb33.Ut.Ut;
import com.mysite.sbb33.repository.UserRepository;
import com.mysite.sbb33.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/doJoin")
    @ResponseBody
    public String doJoin(String email, String password, String name) {
        if (Ut.empty(email)) {
            return "이메일을 작성해주세요 :)";
        }

        if (userRepository.existsByEmail(email)) {
            return "이메일이 이미 존재합니다. :)";
        }

        if (Ut.empty(password)) {
            return "비밀번호를 작성해주세요 :)";
        }

        if (Ut.empty(name)) {
            return "이름을 작성해주세요 :)";
        }

        User user = new User();
        user.setRegDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);

        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public String doLogin(String email, String password, HttpSession session){
        boolean isLogin = false;
        long loginedUserId = 0;

        if(session.getAttribute("loginedUserId") != null){
            isLogin = true;
            loginedUserId = (long)session.getAttribute("loginedUserId");
        }

        if(isLogin){
            return "이미 로그인 되어있습니다. :)";
        }

        if (Ut.empty(email)) {
            return "이메일을 작성해주세요 :)";
        }

        if (Ut.empty(password)) {
            return "비밀번호를 작성해주세요 :)";
        }

        if(!userRepository.existsByEmail(email)){
            return "이메일이 존재하지 않습니다.";
        }

        Optional<User> opUser = userRepository.findByEmail(email);
        User user = opUser.get();

        if(user.getPassword().equals(password) == false){
            return "비밀번호가 일치하지 않습니다. :)";
        }

        session.setAttribute("loginedUserId",user.getId());

        return "%s님 환영합니다.".formatted(user.getName());
    }

    @RequestMapping("/doLogout")
    @ResponseBody
    public String doLogout(HttpSession session){
        boolean isLogin = false;
        long loginedUserId = 0;

        if(session.getAttribute("loginedUserId") != null){
            isLogin = true;
            loginedUserId = (long)session.getAttribute("loginedUserId");
        }

        if(isLogin == false){
            return "이미 로그아웃 됐습니다. :)";
        }

        session.removeAttribute("loginedUserId");

        return "로그아웃 되었습니다. :)";
    }

}
