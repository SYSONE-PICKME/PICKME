package project.pickme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PickmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PickmeApplication.class, args);
	}
}
