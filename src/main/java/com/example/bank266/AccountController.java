package com.example.bank266;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {

    private int balance = 0;

    @GetMapping("/account")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Model model) {
        model.addAttribute("balance", balance);
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

        if (amount != null) {
            balance += (deposit != null) ? amount : ((withdraw != null) ? -amount : 0);
        }
        // System.out.printf("######### Yukan in account post, amount: %d, balance: %d\n", amount, balance);
        model.addAttribute("balance", balance);
        return "account";
    }

}
