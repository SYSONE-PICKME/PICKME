package project.pickme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PickmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PickmeApplication.class, args);
	}

}
