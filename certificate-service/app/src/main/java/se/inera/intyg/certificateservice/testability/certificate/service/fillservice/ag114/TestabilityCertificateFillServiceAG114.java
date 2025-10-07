package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.ag114;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.CertificateModelFactoryAG114.AG114_V2_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionAngeVadAnnatAr.QUESTION_ANGE_VAD_ANNAT_AR_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionArbetsformaga.QUESTION_ARBETSFORMAGA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionBeskrivArbetsformagan.QUESTION_BESKRIV_ARBETSFORMAGAN_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFinnsArbetsformaga.QUESTION_FINNS_ARBETSFORMAGA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFormedlaDiagnos.QUESTION_FORMEDLA_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionKontakt.QUESTION_KONTAKT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionKontaktBeskrivning.QUESTION_KONTAKT_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionOvrigt.QUESTION_OVRIGT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodBedomning.QUESTION_PERIOD_BEDOMNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodProcentBedomning.QUESTION_PERIOD_PROCENT_BEDOMNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_ID;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.EMPTY;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.MAXIMAL;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.MINIMAL;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueInteger;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceAG114 implements TestabilityCertificateFillService {

  private static final List<ElementId> MAXIMAL_IDS = List.of(
      QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
      QUESTION_ANGE_VAD_ANNAT_AR_ID,
      QUESTION_SYSSELSATTNING_ID,
      QUESTION_FORMEDLA_DIAGNOS_ID,
      QUESTION_DIAGNOS_ID,
      QUESTION_ARBETSFORMAGA_ID,
      QUESTION_FINNS_ARBETSFORMAGA_ID,
      QUESTION_BESKRIV_ARBETSFORMAGAN_ID,
      QUESTION_PERIOD_PROCENT_BEDOMNING_ID,
      QUESTION_PERIOD_BEDOMNING_ID,
      QUESTION_OVRIGT_ID,
      QUESTION_KONTAKT_ID,
      QUESTION_KONTAKT_BESKRIVNING_ID
  );

  private static final List<ElementId> MINIMAL_IDS = List.of(
      QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
      QUESTION_SYSSELSATTNING_ID,
      QUESTION_FORMEDLA_DIAGNOS_ID,
      QUESTION_ARBETSFORMAGA_ID,
      QUESTION_FINNS_ARBETSFORMAGA_ID,
      QUESTION_PERIOD_PROCENT_BEDOMNING_ID,
      QUESTION_PERIOD_BEDOMNING_ID
  );

  private static final Map<ElementId, String> TEXT_QUESTION_MOCKS = Map.ofEntries(
      Map.entry(QUESTION_ANGE_VAD_ANNAT_AR_ID, "Patienten har fått vård via videolänk."),
      Map.entry(QUESTION_SYSSELSATTNING_ID,
          "Kontorsarbetare, ansvarar för administrativt stöd och kundkontakt."),
      Map.entry(QUESTION_ARBETSFORMAGA_ID,
          "Ryggsymptom begränsar förmågan att sitta längre perioder och lyfta tunga föremål."),
      Map.entry(QUESTION_BESKRIV_ARBETSFORMAGAN_ID,
          "Kan utföra mindre krävande administrativa uppgifter under kortare perioder med regelbundna pauser."),
      Map.entry(QUESTION_OVRIGT_ID,
          "Patienten behöver flexibla arbetstider och möjlighet att arbeta hemifrån vissa dagar."),
      Map.entry(QUESTION_KONTAKT_BESKRIVNING_ID,
          "Behöver diskutera arbetsanpassningar med HR-avdelningen.")
  );

  @Override
  public List<CertificateModelId> certificateModelIds() {
    return List.of(AG114_V2_0);
  }

  @Override
  public List<ElementData> fill(CertificateModel certificateModel,
      TestabilityFillTypeDTO fillType) {
    return fillType == EMPTY
        ? Collections.emptyList()
        : fillWithValues(certificateModel, fillType);
  }

  private static List<ElementData> fillWithValues(CertificateModel certificateModel,
      TestabilityFillTypeDTO fillType) {
    final var elementIds = fillType == MAXIMAL ? MAXIMAL_IDS : MINIMAL_IDS;

    return elementIds.stream()
        .map(certificateModel::elementSpecification)
        .toList()
        .stream()
        .map(element -> fill(element, fillType))
        .filter(Objects::nonNull)
        .toList();
  }

  private static ElementData fill(ElementSpecification elementSpecification,
      TestabilityFillTypeDTO fillType) {
    final var value = elementSpecification.configuration().emptyValue();

    if (value instanceof ElementValueBoolean elementValueBoolean) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(elementValueBoolean.withValue(getBoolean(fillType)))
          .build();
    }

    if (value instanceof ElementValueText elementValueText) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(elementValueText.withText(TEXT_QUESTION_MOCKS.get(elementSpecification.id())))
          .build();
    }

    if (value instanceof ElementValueDateList elementValueDateList) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(
              elementValueDateList.withDateList(
                  List.of(
                      ElementValueDate.builder()
                          .dateId(new FieldId(getCode(elementSpecification.id(), fillType).code()))
                          .date(LocalDate.now())
                          .build()
                  )
              )
          )
          .build();
    }

    if (value instanceof ElementValueDiagnosisList elementValueDiagnosisList) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(
              elementValueDiagnosisList.withDiagnoses(List.of(
                      ElementValueDiagnosis.builder()
                          .code("A78")
                          .description("Q-feber")
                          .terminology(CodeSystemIcd10Se.terminology().id())
                          .id(new FieldId("huvuddiagnos"))
                          .build()
                  )
              )
          )
          .build();
    }

    if (value instanceof ElementValueDateRange elementValueDateRange) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(
              ElementValueDateRange.builder()
                  .fromDate(LocalDate.now())
                  .toDate(LocalDate.now().plusDays(7))
                  .id(elementValueDateRange.id())
                  .build()
          )
          .build();
    }

    if (value instanceof ElementValueInteger) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(
              ElementValueInteger.builder()
                  .value(25)
                  .integerId(elementSpecification.configuration().id())
                  .build()
          )
          .build();
    }

    throw new IllegalStateException("No matching fill for element: " + elementSpecification.id());
  }

  private static Code getCode(ElementId elementId, TestabilityFillTypeDTO fillType) {
    if (elementId.equals(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID)) {
      if (fillType == MINIMAL) {
        return CodeSystemKvFkmu0001.FYSISKUNDERSOKNING;
      }
      return CodeSystemKvFkmu0001.ANNAT;
    }

    throw new IllegalStateException("Unexpected code for elementId: " + elementId);
  }

  private static boolean getBoolean(TestabilityFillTypeDTO fillType) {
    return fillType == MAXIMAL;
  }
}
