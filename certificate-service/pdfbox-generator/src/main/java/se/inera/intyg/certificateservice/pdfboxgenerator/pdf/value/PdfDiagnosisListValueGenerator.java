package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDiagnoses;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfDiagnosisListValueGenerator implements PdfElementValue<ElementValueDiagnosisList> {

  @Override
  public Class<ElementValueDiagnosisList> getType() {
    return ElementValueDiagnosisList.class;
  }

  @Override
  public List<PdfField> generate(ElementSpecification elementSpecification,
      ElementValueDiagnosisList elementValueDiagnosisList) {
    final var pdfConfiguration = (PdfConfigurationDiagnoses) elementSpecification.pdfConfiguration();
    return elementValueDiagnosisList.diagnoses().stream()
        .map(diagnose -> getFields(diagnose, pdfConfiguration))
        .flatMap(Collection::stream)
        .toList();
  }

  private List<PdfField> getFields(ElementValueDiagnosis diagnose,
      PdfConfigurationDiagnoses configuration) {
    final var pdfConfigurationDiagnosis = configuration.diagnoses().get(diagnose.id());
    if (pdfConfigurationDiagnosis == null) {
      throw new IllegalArgumentException("Diagnosis " + diagnose.id() + " not found");
    }

    final var diagnoseNameId = pdfConfigurationDiagnosis.pdfNameFieldId();
    final var diagnoseCodeIds = pdfConfigurationDiagnosis.pdfCodeFieldIds();

    return Stream.of(
            getDiagnoseNameValues(diagnose, diagnoseNameId.id()),
            getDiagnoseCodeValues(diagnose, diagnoseCodeIds)
        )
        .flatMap(Collection::stream)
        .toList();
  }

  private static List<PdfField> getDiagnoseNameValues(ElementValueDiagnosis diagnose,
      String pdfFieldId) {
    return List.of(
        PdfField.builder()
            .id(pdfFieldId)
            .value(diagnose.description())
            .build()
    );
  }

  private static List<PdfField> getDiagnoseCodeValues(ElementValueDiagnosis diagnose,
      List<PdfFieldId> codeIds) {
    final var fields = new ArrayList<PdfField>();
    final var codes = diagnose.code().toCharArray();

    for (var i = 0; i < codes.length; i++) {
      fields.add(getDiagnoseCode(String.valueOf(codes[i]), codeIds.get(i).id()));
    }

    return fields;
  }

  private static PdfField getDiagnoseCode(String value, String pdfFieldId) {
    return PdfField.builder()
        .id(pdfFieldId)
        .value(value)
        .build();
  }
}
