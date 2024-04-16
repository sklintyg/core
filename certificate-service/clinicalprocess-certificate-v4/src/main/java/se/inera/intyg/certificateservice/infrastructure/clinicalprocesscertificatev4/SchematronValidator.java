package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import com.helger.schematron.sch.SchematronResourceSCH;
import com.helger.schematron.svrl.SVRLHelper;
import com.helger.schematron.svrl.jaxb.SchematronOutputType;
import java.io.StringReader;
import javax.xml.transform.stream.StreamSource;
import lombok.extern.slf4j.Slf4j;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlSchematronValidator;

@Slf4j
public class SchematronValidator implements XmlSchematronValidator {

  @Override
  public boolean validate(String certificateId, Xml xml, String schematronPath) {
    final var xmlStream = new StreamSource(new StringReader(xml.xml()));
    final var schematronResource = SchematronResourceSCH.fromClassPath(
        schematronPath
    );
    try {
      final var schematronOutput = schematronResource.applySchematronValidationToSVRL(xmlStream);
      return schematronResult(schematronOutput, certificateId);
    } catch (Exception e) {
      log.error("Schematron validation of certificate id {} failed with an exception.",
          certificateId, e);
      return false;
    }
  }

  private boolean schematronResult(SchematronOutputType output, String certificateId) {
    final var failedAssertions = SVRLHelper.getAllFailedAssertions(output);

    if (failedAssertions.isEmpty()) {
      log.info("Schematron validation passed for certificate id {}.", certificateId);
      return true;
    } else {
      final var failures = failedAssertions.stream()
          .map(fail -> "failed: %s, MSG: %s".formatted(fail.getTest(), fail.getText())).toList();
      log.warn("Schematron validation failed for certificate id {}.\n{}", certificateId, failures);
      return false;
    }
  }
}
