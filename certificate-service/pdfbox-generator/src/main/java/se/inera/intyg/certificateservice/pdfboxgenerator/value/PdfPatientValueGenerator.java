package se.inera.intyg.certificateservice.pdfboxgenerator.value;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfPatientValueGenerator {

  public List<PdfField> generate(Certificate certificate, String id) {
    final var patientId = certificate.certificateMetaData().patient().id().id();
    return List.of(
        PdfField.builder()
            .id(id)
            .value(patientId)
            .build()
    );
  }
}
