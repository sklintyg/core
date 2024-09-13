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

    return new ArrayList<>(elementValueDiagnosisList.diagnoses().stream()
        .map(diagnose -> getFields(diagnose, pdfConfiguration))
        .flatMap(Collection::stream)
        .toList());
  }

  private List<PdfField> getFields(ElementValueDiagnosis diagnosis,
      PdfConfigurationDiagnoses configuration) {
    final var pdfConfigurationDiagnosis = configuration.diagnoses().get(diagnosis.id());
    if (pdfConfigurationDiagnosis == null) {
      throw new IllegalArgumentException("Diagnosis " + diagnosis.id() + " not found");
    }

    final var diagnoseNameId = pdfConfigurationDiagnosis.pdfNameFieldId();
    final var diagnoseCodeIds = pdfConfigurationDiagnosis.pdfCodeFieldIds();

    return Stream.of(
            getDiagnosisNameValues(configuration, diagnosis, diagnoseNameId.id()),
            getDiagnosisCodeValues(diagnosis, diagnoseCodeIds)
        )
        .flatMap(Collection::stream)
        .toList();
  }

  private static List<PdfField> getDiagnosisNameValues(PdfConfigurationDiagnoses pdfConfiguration,
      ElementValueDiagnosis diagnosis, String pdfFieldId) {
    if (pdfConfiguration.maxLength() != null && isDiagnosisDescriptionOverflowing(pdfConfiguration,
        diagnosis)) {
      final var truncatedText = PdfValueGeneratorUtil.splitByLimit(pdfConfiguration.maxLength(),
          diagnosis.description(), false);
      return List.of(
          PdfField.builder()
              .id(pdfFieldId)
              .value(truncatedText.get(0))
              .appearance(pdfConfiguration.appearance())
              .build()
      );
    }

    return List.of(
        PdfField.builder()
            .id(pdfFieldId)
            .value(diagnosis.description())
            .appearance(pdfConfiguration.appearance())
            .build()
    );
  }

  private static boolean isDiagnosisDescriptionOverflowing(
      PdfConfigurationDiagnoses pdfConfiguration,
      ElementValueDiagnosis diagnosis) {
    return pdfConfiguration.maxLength() != null
        && pdfConfiguration.maxLength() < diagnosis.description().length();
  }

  private static List<PdfField> getDiagnosisCodeValues(ElementValueDiagnosis diagnose,
      List<PdfFieldId> codeIds) {
    final var fields = new ArrayList<PdfField>();
    final var codes = diagnose.code().toCharArray();

    for (var i = 0; i < codes.length; i++) {
      fields.add(
          PdfField.builder()
              .id(codeIds.get(i).id())
              .value(String.valueOf(codes[i]))
              .build()
      );
    }

    return fields;
  }
}
