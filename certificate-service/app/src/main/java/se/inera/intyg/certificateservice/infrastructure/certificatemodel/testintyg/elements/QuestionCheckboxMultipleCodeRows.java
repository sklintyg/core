package se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationHidden;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;


public class QuestionCheckboxMultipleCodeRows {

  public static final ElementId QUESTION_CODE_ID = new ElementId("5");
  public static final FieldId QUESTION_CODE_FIELD_ID = new FieldId(
      "5");

  private QuestionCheckboxMultipleCodeRows() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionCheckboxMultipleCodeRows() {
    final var checkboxes = List.of(
        getCodeConfig(new FieldId("1"), new Code("1", "Test 1", "Test 1")),
        getCodeConfig(new FieldId("2"), new Code("2", "Test 2", "Test 2")),
        getCodeConfig(new FieldId("3"), new Code("3", "Test 3", "Test 3")),
        getCodeConfig(new FieldId("4"), new Code("4", "Test 4", "Test 4"))
    );

    return ElementSpecification.builder()
        .id(QUESTION_CODE_ID)
        .includeInXml(false)
        .includeForCitizen(false)
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(QUESTION_CODE_FIELD_ID)
                .name("Test av \"CheckboxMultipleCode Columns Layout\"")
                .elementLayout(ElementLayout.COLUMNS)
                .list(checkboxes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_CODE_ID,
                    List.of(
                        new FieldId("1"),
                        new FieldId("2"),
                        new FieldId("3"),
                        new FieldId("4")
                    )
                )
            )
        )
        .validations(
            List.of(
                ElementValidationCodeList.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .pdfConfiguration(
            PdfConfigurationHidden.builder().build()
        )
        .build();
  }

  private static ElementConfigurationCode getCodeConfig(FieldId fieldId, Code code) {
    return new ElementConfigurationCode(
        fieldId,
        code.displayName(),
        code
    );
  }
}
