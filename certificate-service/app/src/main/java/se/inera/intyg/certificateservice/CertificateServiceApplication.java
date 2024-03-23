package se.inera.intyg.certificateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class CertificateServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CertificateServiceApplication.class, args);
  }

}
