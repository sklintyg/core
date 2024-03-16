package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import com.helger.schematron.sch.SchematronResourceSCH;
import com.helger.schematron.svrl.SVRLHelper;
import com.helger.schematron.svrl.jaxb.SchematronOutputType;
import java.io.StringReader;
import javax.xml.transform.stream.StreamSource;
import lombok.extern.slf4j.Slf4j;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlSchematronValidator;

@Slf4j
public class SchematronValidator implements XmlSchematronValidator {

  @Override
  public boolean validate(Certificate certificate) {
    final var certificateId = certificate.id().id();
    final var type = certificate.certificateModel().id().type().type();
    final var version = certificate.certificateModel().id().version().version();
    final var dir = "schematron/%s/".formatted(type);
    final var file = "%s.v%s.sch".formatted(type, version.replaceAll("\\.+.{0,10}", ""));
    final var xmlStream = new StreamSource(new StringReader(certificate.xml().xml()));
    final var schematronResource = SchematronResourceSCH.fromClassPath(dir + file);

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
