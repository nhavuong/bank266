package com.example.bank266;

import com.example.bank266.services.UserInfoService;
import org.apache.commons.lang3.Validate;
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

        model.addAttribute("comment", new Comment());

        String username = findUserName(httpRequest);
        if (username == null)
            return Utils.redirect("/");
        // System.out.println("######## Yukan in AccountController.greeting ");
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
            @ModelAttribute Comment comment,
            Model model,
            HttpServletRequest httpRequest) {

        String username = findUserName(httpRequest);
        UserInfo userInfo = userInfoService.searchUserByName(username).get(0);

        ValidAmount validAmount = new ValidAmount(amount);
        if (validAmount.getAmount() != null) {
            Double amountNumber = validAmount.getAmount();
            double delta = (deposit != null) ? amountNumber : ((withdraw != null) ? -amountNumber : 0);
            if (userInfo.getBalance() + delta < 0.0) {
                model.addAttribute("warning", "Overdraft is not allowed");
            } else {
                userInfo.setBalance(userInfo.getBalance() + delta);
                userInfoService.save(userInfo);
            }
        } else {
            model.addAttribute("warning", "Your input was invalid_input: \"" + amount + "\"");
        }
        // System.out.printf("######### Yukan in account post, amount: %d, balance: %d\n", amount, balance);

        updatesModel(model, userInfo);

        if ((comment.getMessage() != null) && (comment.getMessage().length() > 0)) {
            System.out.println("######### Yukan in account post: " + comment.getMessage());
            // CWE-80 cross-site scripting mitigation
            // Remove <script>.*</script> in comment
            comment.removeJS();
            System.out.println("######### Yukan in account post after removing JS: " + comment.getMessage());
            model.addAttribute("lastComment", "You comment: \"" + comment.getMessage() + "\" has been submitted");
            comment.setMessage(null);
        }
        return "account";
    }

}
