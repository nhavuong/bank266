package com.example.bank266;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.Map;
import java.util.regex.Pattern;


@Controller
public class RegisterController {

    private Connection connectDB() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:bank266.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return connection;
    }

    @GetMapping("/register")
    public String index(RedirectAttributes redirAttrs) {
        return "signup";
    }


    @PostMapping("/registerPost")
    public String registerPost(@RequestParam(value = "username", defaultValue = "") String username,
                           @RequestParam(value = "password", defaultValue = "") String password,
                           @RequestParam(value = "initialValue", defaultValue = "") String number,
                           RedirectAttributes redirAttrs, HttpServletRequest httpRequest) throws SQLException {
        boolean isValid = true;
        for(char c: username.toCharArray()){
            if(!String.valueOf(c).matches("[_\\\\-\\\\.0-9a-z]")){
                isValid = false;
                break;
            }
        }
        for(char c: password.toCharArray()){
            if(!String.valueOf(c).matches("[_\\\\-\\\\.0-9a-z]")){
                isValid = false;
                break;
            }
        }

        if(number.contains(".")){
            String[] arry = number.split("[.]");
            if(arry.length != 2) isValid = false;
            else {
                String integer = number.split("[.]")[0];
                String fractional = number.split("[.]")[1];
                if (fractional.length() > 2 || integer.length() < 1) isValid = false;
                else {
                    if (integer.toCharArray()[0] == '0') isValid = false;
                    else {
                        if (!isNum(integer)) isValid = false;
                        if (!isNum(fractional)) isValid = false;
                    }
                }
            }
        }
        else{
            if(!isNum(number)) isValid = false;
        }

        if(!isValid || username.length() > 127 || username.length() < 1
                || password.length() > 127 || password.length() < 1)
        {
            redirAttrs.addFlashAttribute("message", "invalid_input");
            return Utils.redirect("register");
        }
//        System.out.println(username + password + number);
        Connection con = connectDB();
        if(con != null){
            String Sql = "INSERT INTO user_info (name, password, balance) VALUES (\'" + username + "','" + password+"','" + number + "');";
            Statement sqlStatement = con.createStatement();
            int res = sqlStatement.executeUpdate(Sql);
            if(res > 0){
                con.close();
                sqlStatement.close();
                return Utils.redirect("/");
            }
            else{
                redirAttrs.addFlashAttribute("message", "register failed, please try again");
                return Utils.redirect("register");
            }
        }
        return Utils.redirect("/");
    }

    private boolean isNum(String string){
        for(char c: string.toCharArray()){
            if(!Character.isDigit(c)) return false;
        }
        return true;
    }

}