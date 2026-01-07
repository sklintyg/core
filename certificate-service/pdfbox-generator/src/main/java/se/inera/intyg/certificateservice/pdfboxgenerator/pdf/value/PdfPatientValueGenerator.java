package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfPatientValueGenerator {

  public List<PdfField> generate(Certificate certificate, List<PdfFieldId> ids) {
    final var patientId = certificate.getMetadataForPrint().patient().id().idWithoutDash();
    return ids.stream()
        .map(id ->
            PdfField.builder()
                .id(id.id())
                .value(patientId)
                .patientField(true)
                .build())
        .toList();
  }
}
