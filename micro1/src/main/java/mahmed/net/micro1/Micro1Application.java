package mahmed.net.micro1;

import java.lang.annotation.Annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Micro1Application {

	public static void main(String[] args) {
		SpringApplication.run(Micro1Application.class, args);
	}
	
	@Bean
	public RestTemplate getController()
	{
		return new RestTemplate() ; 			
		 
	}

}
