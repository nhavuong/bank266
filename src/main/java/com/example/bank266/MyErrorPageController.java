package com.example.bank266;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyErrorPageController implements ErrorController {

    @GetMapping("/error")
    public String handleError() {
        return "error";
    }
    @Override
    public String getErrorPath() {
        return null;
    }
}
