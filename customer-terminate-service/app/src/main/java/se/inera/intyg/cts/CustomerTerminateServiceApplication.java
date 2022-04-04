package se.inera.intyg.cts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CustomerTerminateServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CustomerTerminateServiceApplication.class, args);
  }
}
