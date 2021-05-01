package com.example.bank266;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {

    @GetMapping("/account")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        System.out.println("######### Yukan in account");
        return "account.html";
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public String processFeed(
            @RequestParam(value = "amount", required = false) String amount,
            Model model,
            HttpServletRequest httpRequest) {
        System.out.println("######### Yukan in account post " + amount);
        return "account.html";
    }

}
