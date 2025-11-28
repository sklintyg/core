package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.DefaultCitizenAvailableFunctionsProvider.AVAILABLE_FUNCTION_PRINT_NAME;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFormedlaDiagnos.QUESTION_FORMEDLA_DIAGNOS_ID;

import java.util.ArrayList;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionInformationType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionsProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

public class AG114CitizenAvailableFunctionsProvider implements
    CitizenAvailableFunctionsProvider {

  private static final String AVAILABLE_FUNCTION_CUSTOMIZE_BODY =
      "När du skriver ut ett läkarintyg du ska lämna till din arbetsgivare kan du "
          + "välja om du vill att din diagnos ska visas eller döljas. Ingen annan information kan döljas. ";
  private static final String AVAILABLE_FUNCTION_CUSTOMIZE_TITLE = "Vill du visa eller dölja diagnos?";
  private static final String AVAILABLE_FUNCTION_CUSTOMIZE_DESCRIPTION =
      "Information om diagnos kan vara viktig för din arbetsgivare."
          + " Det kan underlätta anpassning av din arbetssituation. Det kan också göra att du snabbare kommer tillbaka till arbetet.";
  private static final String AVAILABLE_FUNCTION_CUSTOMIZE_NAME = "Anpassa intyget för utskrift";

  private static final String HIDE_DIAGNOSIS_TEXT = "Dölj diagnos";
  private static final String SHOW_DIAGNOSIS_TEXT = "Visa diagnos";


  @Override
  public List<CitizenAvailableFunction> of(Certificate certificate) {
    final var functions = new ArrayList<CitizenAvailableFunction>();

    if (certificate.isReplaced() || certificate.isComplemented()) {
      return List.of();
    }

    if (isDiagnosisIncluded(certificate)) {
      functions.add(
          CitizenAvailableFunction.builder()
              .type(CitizenAvailableFunctionType.CUSTOMIZE_PRINT_CERTIFICATE)
              .enabled(true)
              .title(AVAILABLE_FUNCTION_CUSTOMIZE_TITLE)
              .name(AVAILABLE_FUNCTION_CUSTOMIZE_NAME)
              .body(AVAILABLE_FUNCTION_CUSTOMIZE_BODY)
              .description(AVAILABLE_FUNCTION_CUSTOMIZE_DESCRIPTION)
              .information(
                  List.of(
                      CitizenAvailableFunctionInformation.builder()
                          .type(CitizenAvailableFunctionInformationType.FILENAME)
                          .text(certificate.certificateModel().fileName())
                          .build(),
                      CitizenAvailableFunctionInformation.builder()
                          .type(CitizenAvailableFunctionInformationType.OPTIONS)
                          .text(SHOW_DIAGNOSIS_TEXT)
                          .build(),
                      CitizenAvailableFunctionInformation.builder()
                          .type(CitizenAvailableFunctionInformationType.OPTIONS)
                          .text(HIDE_DIAGNOSIS_TEXT)
                          .id(QUESTION_DIAGNOS_ID)
                          .build()
                  ))
              .build()
      );
    } else {
      functions.add(CitizenAvailableFunction.builder()
          .name(AVAILABLE_FUNCTION_PRINT_NAME)
          .type(CitizenAvailableFunctionType.PRINT_CERTIFICATE)
          .information(
              List.of(
                  CitizenAvailableFunctionInformation.builder()
                      .type(CitizenAvailableFunctionInformationType.FILENAME)
                      .text(certificate.certificateModel().fileName())
                      .build()
              )
          )
          .enabled(true)
          .build()
      );
    }

    return functions;
  }

  private boolean isDiagnosisIncluded(Certificate certificate) {
    return getBooleanValue(certificate, QUESTION_FORMEDLA_DIAGNOS_ID);
  }

  private static boolean getBooleanValue(Certificate certificate,
      ElementId elementId) {
    final var element = certificate.getElementDataById(elementId);

    if (element.isEmpty()) {
      return false;
    }

    if (element.get().value() instanceof ElementValueBoolean booleanValue) {
      return booleanValue.value() != null && booleanValue.value();
    }

    throw new IllegalStateException("Element value is not of type ElementValueBoolean");
  }
}
