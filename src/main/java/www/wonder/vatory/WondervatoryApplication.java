package www.wonder.vatory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class WondervatoryApplication {
	// 연동 테스트 확인하고 지워주세요
	public static void main(String[] args) {
		SpringApplication.run(WondervatoryApplication.class, args);
	}

}
