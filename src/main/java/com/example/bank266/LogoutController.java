package com.example.bank266;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest httpRequest){
        HttpSession session = httpRequest.getSession();
        String username = (String) session.getAttribute("username");
        if(username != null){
            System.out.println("Log out ~!!!!!" + username);
            session.removeAttribute("username");
            return Utils.redirect("/");
        }
        return "index";
    }
}

