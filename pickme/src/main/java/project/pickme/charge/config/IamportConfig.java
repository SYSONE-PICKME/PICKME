package project.pickme.charge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.siot.IamportRestClient.IamportClient;

@Configuration
public class IamportConfig {
	@Value("${secret.iamport.api-key}")
	private String API_KEY;

	@Value("${secret.iamport.api-secret-key}")
	private String API_SECRET_KEY;

	@Bean
	public IamportClient iamportClient() {
		return new IamportClient(API_KEY, API_SECRET_KEY);
	}
}
