package se.inera.intyg.certificateservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class CertificateServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CertificateServiceApplication.class, args);
  }

}
