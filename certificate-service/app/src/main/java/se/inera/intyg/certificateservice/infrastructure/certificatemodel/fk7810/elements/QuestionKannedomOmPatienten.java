package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDropdownCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDropdownCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0010;

public class QuestionKannedomOmPatienten {

  public static final ElementId QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_ID = new ElementId("2");
  private static final FieldId QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_FIELD_ID = new FieldId(
      "2.2");

  public static final FieldId INGEN_TIDIGARE = new FieldId(
      CodeSystemKvFkmu0010.INGEN_TIDIGARE.code());
  public static final FieldId MINDRE_AN_ETT_AR = new FieldId(
      CodeSystemKvFkmu0010.MINDRE_AN_ETT_AR.code());
  public static final FieldId MER_AN_ETT_AT_FIELD_ID = new FieldId(
      CodeSystemKvFkmu0010.MER_AN_ETT_AR.code());
  public static final FieldId VALJ_I_LISTAN = new FieldId("");

  public static final PdfFieldId QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_PDF_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].lbx_listVardeUtredningUnderlag1[0]");

  private QuestionKannedomOmPatienten() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKannedomOmPatienten(
      ElementSpecification... children) {
    final var dropdownItems = getDropdownOptions();

    return ElementSpecification.builder()
        .id(QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_ID)
        .includeWhenRenewing(false)
        .configuration(
            ElementConfigurationDropdownCode.builder()
                .id(QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_FIELD_ID)
                .name("Jag har kännedom om patienten sedan")
                .list(dropdownItems)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_ID,
                    dropdownItems.stream().skip(1).map(ElementConfigurationCode::id).toList()
                )
            )
        )
        .validations(
            List.of(
                ElementValidationCode.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .pdfConfiguration(PdfConfigurationDropdownCode.builder()
            .fieldId(QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_PDF_FIELD_ID)
            .codes(PdfConfigurationDropdownCode.fromCodeConfig(dropdownItems))
            .build())
        .includeWhenRenewing(false)
        .children(List.of(children))
        .build();
  }

  private static List<ElementConfigurationCode> getDropdownOptions() {
    return List.of(
        new ElementConfigurationCode(
            VALJ_I_LISTAN,
            "Välj i listan",
            null
        ),
        new ElementConfigurationCode(
            INGEN_TIDIGARE,
            CodeSystemKvFkmu0010.INGEN_TIDIGARE.displayName(),
            CodeSystemKvFkmu0010.INGEN_TIDIGARE
        ),
        new ElementConfigurationCode(
            MINDRE_AN_ETT_AR,
            CodeSystemKvFkmu0010.MINDRE_AN_ETT_AR.displayName(),
            CodeSystemKvFkmu0010.MINDRE_AN_ETT_AR
        ),
        new ElementConfigurationCode(
            MER_AN_ETT_AT_FIELD_ID,
            CodeSystemKvFkmu0010.MER_AN_ETT_AR.displayName(),
            CodeSystemKvFkmu0010.MER_AN_ETT_AR
        )
    );
  }
}