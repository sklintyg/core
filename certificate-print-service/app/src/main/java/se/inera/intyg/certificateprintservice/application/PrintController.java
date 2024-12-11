package se.inera.intyg.certificateprintservice.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/print")
public class PrintController {

  @GetMapping
  String hello() {
    return "Hello print service";
  }

}
