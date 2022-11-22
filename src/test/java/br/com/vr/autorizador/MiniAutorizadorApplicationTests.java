package br.com.vr.autorizador;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = {MiniAutorizadorApplicationTests.Initializer.class})
public class MiniAutorizadorApplicationTests {

	public static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
			.withDatabaseName("autorizador")
			.withUsername("autorizadorUser")
			.withPassword("teste@123");
	
	static {
		mysql.start();
	}
	
	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			TestPropertyValues.of(
				"spring.datasource.url=" + mysql.getJdbcUrl() + "?useSSL=false",
				"spring.datasource.username=" + mysql.getUsername(),
				"spring.datasource.password=" + mysql.getPassword()
			).applyTo(applicationContext.getEnvironment());
		}
	}

}
