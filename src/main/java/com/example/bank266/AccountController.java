package com.example.bank266;

import com.example.bank266.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {

    @Autowired
    private UserInfoService userInfoService;

    private String findUserName(HttpServletRequest httpRequest) {
        String username = (String) httpRequest.getSession().getAttribute("username");
        if (username == null) {
            System.out.println("Bank266:AccountController User is not Logged In - use admin ...");
            return "admin";
        }
        return username;
    }

    @GetMapping("/account")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Model model,
            HttpServletRequest httpRequest) {

        String username = findUserName(httpRequest);
        System.out.println("######## Yukan in AccountController.greeting ");
        UserInfo userInfo = userInfoService.searchUserByName(username).get(0);

        model.addAttribute("username", userInfo.getName());
        model.addAttribute("balance", userInfo.getBalance());
        // System.out.println("######### Yukan in account");
        return "account";
    }

    @PostMapping(value = "/account")
    public String processFeed(
            @RequestParam(value = "amount", required = false) Integer amount,
            @RequestParam(value = "deposit", required = false) String deposit,
            @RequestParam(value = "withdraw", required = false) String withdraw,
            Model model,
            HttpServletRequest httpRequest) {

        String username = findUserName(httpRequest);
        UserInfo userInfo = userInfoService.searchUserByName(username).get(0);

        if (amount != null) {
            double delta = (deposit != null) ? amount : ((withdraw != null) ? -amount : 0);
            userInfo.setBalance(userInfo.getBalance() + delta);
            userInfoService.save(userInfo);
        }
        // System.out.printf("######### Yukan in account post, amount: %d, balance: %d\n", amount, balance);
        model.addAttribute("username", userInfo.getName());
        model.addAttribute("balance", userInfo.getBalance());
        return "account";
    }

}
