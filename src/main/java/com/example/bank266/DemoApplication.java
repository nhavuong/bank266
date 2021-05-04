
package com.example.bank266;

import com.example.bank266.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DemoApplication implements CommandLineRunner {

	 @Autowired
	 private UserInfoService userInfoService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// List<String> ret = jdbcTemplate.query("SELECT \n    name\nFROM \n    sqlite_master",
		//		(resultSet, rowNum) -> resultSet.getString("name"));
		// System.out.println("######## Bank266 in DemoApplication.run " + ret.get(0));

		System.out.println("######## Bank266 in DemoApplication.run 3");
		for (UserInfo userInfo: userInfoService.searchUserByName("admin")) {
			System.out.println("bank266 name: " + userInfo.getName());
			System.out.println("bank266 password: " + userInfo.getPassword());
		}
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "Nina") String name) {
		return String.format("Hello %s!", name);
	}

	@RequestMapping("/")
	public String index() {
		return "login";
	}

	@PostMapping("/login")
	public void login(@RequestParam(value = "username", defaultValue = "") String username,
						@RequestParam(value = "password", defaultValue = "") String password) {
		System.out.println(username + " : " + password);
	}
}
