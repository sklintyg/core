package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfValueType;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Service
@RequiredArgsConstructor
public class PdfElementValueGenerator {

  private final List<PdfElementValue> pdfElementValues;

  public List<PdfField> getFields(Certificate certificate) {
    return certificate.certificateModel().pdfSpecification().pdfQuestionFields()
        .stream()
        .map(
            questions -> generate(certificate, questions.questionId(), questions.pdfFieldId(),
                questions.pdfValueType())
        )
        .flatMap(List::stream)
        .toList();
  }

  private List<PdfField> generate(Certificate certificate, ElementId questionId, String pdfFieldId,
      PdfValueType pdfValueType) {
    return pdfElementValues.stream()
        .filter(types -> types.getType().equals(pdfValueType))
        .findFirst()
        .map(pdfValue -> pdfValue.generate(certificate, questionId, pdfFieldId))
        .orElseThrow(() -> new IllegalStateException(
            String.format(
                "Could not find value generator for pdf value type: '%s'", pdfValueType
            ))
        );
  }
}
