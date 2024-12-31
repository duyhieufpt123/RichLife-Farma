package richlife.gateway.RichLife_Pharma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RichLifePharmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RichLifePharmaApplication.class, args);
	}

}
