package se.inera.intyg.certificateprintservice.print;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class GeneratorService {

  private final TemplateEngine templateEngine;

  public String generate() {
    var context = new Context();
    context.setVariable("name", "tsBas");
    return templateEngine.process("certificateTemplate", context);
  }

}
