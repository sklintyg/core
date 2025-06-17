package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.ANNAN_KROPPSILIG_FUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.INTELLEKTUELL_FUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.KOMMUNIKATION_SOCIAL_INTERAKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.KOORDINATION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.PSYKISK_FUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.SINNESFUNKTION_V2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning.UPPMARKSAMHET;

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

public class QuestionFunktionsnedsattning {

  public static final ElementId FUNKTIONSNEDSATTNING_ID = new ElementId("funktionsnedsattning");
  public static final FieldId FUNKTIONSNEDSATNING_FIELD_ID = new FieldId("funktionsnedsattning");
  public static final FieldId FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID = new FieldId("8.2");
  public static final FieldId FUNKTIONSNEDSATTNING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID = new FieldId(
      "9.2");
  public static final FieldId FUNKTIONSNEDSATTNING_UPPMAKRMSAHET_ID = new FieldId("10.2");
  public static final FieldId FUNKTIONSNEDSATTNING_PSYKISK_FUNKTION_ID = new FieldId("11.2");
  public static final FieldId FUNKTIONSNEDSATTNING_SINNESFUNKTION_ID = new FieldId("12.2");
  public static final FieldId FUNKTIONSNEDSATTNING_KOORDINATION_ID = new FieldId("13.2");
  public static final FieldId FUNKTIONSNEDSATTNING_ANNAN_KROPPSILIG_FUNKTION_ID = new FieldId(
      "14.2");

  private QuestionFunktionsnedsattning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionFunktionsnedsattning() {
    final var checkboxes = List.of(
        getCodeConfig(FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID, INTELLEKTUELL_FUNKTION),
        getCodeConfig(FUNKTIONSNEDSATTNING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID,
            KOMMUNIKATION_SOCIAL_INTERAKTION),
        getCodeConfig(FUNKTIONSNEDSATTNING_UPPMAKRMSAHET_ID, UPPMARKSAMHET),
        getCodeConfig(FUNKTIONSNEDSATTNING_PSYKISK_FUNKTION_ID, PSYKISK_FUNKTION),
        getCodeConfig(FUNKTIONSNEDSATTNING_SINNESFUNKTION_ID, SINNESFUNKTION_V2),
        getCodeConfig(FUNKTIONSNEDSATTNING_KOORDINATION_ID, KOORDINATION),
        getCodeConfig(FUNKTIONSNEDSATTNING_ANNAN_KROPPSILIG_FUNKTION_ID, ANNAN_KROPPSILIG_FUNKTION)
    );

    return ElementSpecification.builder()
        .id(FUNKTIONSNEDSATTNING_ID)
        .includeInXml(false)
        .includeForCitizen(false)
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(FUNKTIONSNEDSATNING_FIELD_ID)
                .name("Välj alternativ att fylla i för att visa fritextfält. Välj minst ett:")
                .elementLayout(ElementLayout.COLUMNS)
                .list(checkboxes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    FUNKTIONSNEDSATTNING_ID,
                    List.of(
                        FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID,
                        FUNKTIONSNEDSATTNING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID,
                        FUNKTIONSNEDSATTNING_UPPMAKRMSAHET_ID,
                        FUNKTIONSNEDSATTNING_PSYKISK_FUNKTION_ID,
                        FUNKTIONSNEDSATTNING_SINNESFUNKTION_ID,
                        FUNKTIONSNEDSATTNING_KOORDINATION_ID,
                        FUNKTIONSNEDSATTNING_ANNAN_KROPPSILIG_FUNKTION_ID
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