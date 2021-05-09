package com.example.bank266;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;

@Controller
public class LoginController {

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

    @PostMapping("/login")
    public String login(@RequestParam(value = "username", defaultValue = "") String username,
                      @RequestParam(value = "password", defaultValue = "") String password,
                      HttpServletRequest httpRequest) throws SQLException {

        HttpSession session = httpRequest.getSession();
        String sessionUsername = (String)session.getAttribute("username");
        if(sessionUsername != null){
            System.out.println("Already logged in as " + sessionUsername);
            return Utils.redirect("account");
        }
        else{
            Connection con = connectDB();
            if(con != null){
                System.out.println("connect to db");

                String loginSql = "Select name, password from user_info where name = '" + username + "' and password = '" + password + "';";
                Statement sqlStatement = con.createStatement();
                ResultSet resultSet = sqlStatement.executeQuery(loginSql);

                if(resultSet.next()){
                    System.out.println("User found");
                    session.setAttribute("username", resultSet.getString("name"));
                    con.close();
                    sqlStatement.close();
                    return Utils.redirect("account");
                }
                else{
                    System.out.println("User not found or password not match");
                    sqlStatement.close();
                    con.close();
                    return Utils.redirect("/");
                }
            }
            return Utils.redirect("account");
        }
    }
}
