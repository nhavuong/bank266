package com.example.bank266;

import com.example.bank266.services.UserInfoService;
import org.apache.commons.lang3.math.NumberUtils;
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
            System.out.println("Bank266:AccountController User is not Logged In - go back to login page ...");
            return null;
        }
        return username;
    }

    private void updatesModel(Model model, UserInfo userInfo) {
        model.addAttribute("username", userInfo.getName());
        model.addAttribute("balance", String.format("%.2f", userInfo.getBalance()));
    }

    @GetMapping("/account")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Model model,
            HttpServletRequest httpRequest) {

        String username = findUserName(httpRequest);
        if (username == null)
            return Utils.redirect("/");
        System.out.println("######## Yukan in AccountController.greeting ");
        UserInfo userInfo = userInfoService.searchUserByName(username).get(0);
        updatesModel(model, userInfo);
        // System.out.println("######### Yukan in account");
        return "account";
    }

    @PostMapping(value = "/account")
    public String processFeed(
            @RequestParam(value = "amount", required = false) String amount,
            @RequestParam(value = "deposit", required = false) String deposit,
            @RequestParam(value = "withdraw", required = false) String withdraw,
            Model model,
            HttpServletRequest httpRequest) {

        String username = findUserName(httpRequest);
        UserInfo userInfo = userInfoService.searchUserByName(username).get(0);

        if (NumberUtils.isCreatable(amount)) {
            Double amountNumber = NumberUtils.toDouble(amount);
            double delta = (deposit != null) ? amountNumber : ((withdraw != null) ? -amountNumber : 0);
            userInfo.setBalance(userInfo.getBalance() + delta);
            userInfoService.save(userInfo);
        } else if (amount != null) {
            model.addAttribute("warning", "Your input was ill-formatted: " + amount);
        }
        // System.out.printf("######### Yukan in account post, amount: %d, balance: %d\n", amount, balance);

        updatesModel(model, userInfo);
        return "account";
    }

}
