
package com.example.bank266;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
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
