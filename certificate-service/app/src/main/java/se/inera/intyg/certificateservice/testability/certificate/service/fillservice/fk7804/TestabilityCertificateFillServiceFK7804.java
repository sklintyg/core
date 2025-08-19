package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.fk7804;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.CertificateModelFactoryFK7804.FK7804_V2_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAktivitetsbegransningar.QUESTION_AKTIVITETSBEGRANSNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAngeVarforDuVillHaKontakt.QUESTION_VARFOR_KONTAKT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAnnanGrundForMedicinsktUnderlag.QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAntalManader.QUESTION_ANTAL_MANADER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAntalManader.QUESTION_ANTAL_MANADER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionArbetsformagaLangreAnBeslutsstod.QUESTION_ARBETFORMAGA_LANGRE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAtgarderSomKanFramjaAtergang.QUESTION_ATGARDER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionFunktionsnedsattningar.QUESTION_FUNKTIONSNEDSATTNINGAR_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionGrundForBedomning.QUESTION_GRUND_FOR_BEDOMNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionKontakt.QUESTION_KONTAKT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionMedicinskBehandling.QUESTION_MEDICINSK_BEHANDLING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionMedicinskaSkalForSvarareAtergang.QUESTION_MEDICINSKA_SKAL_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionNedsattningArbetsformaga.QUESTION_NEDSATTNING_ARBETSFORMAGA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionOvrigt.QUESTION_OVRIGT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionPrognos.QUESTION_PROGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSvarareAtergangVidOjamnArbetstid.QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionTransportstod.QUESTION_TRANSPORTSTOD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionYrkeOchArbetsuppgifter.QUESTION_YRKE_ARBETSUPPGIFTER_ID;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.EMPTY;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.MAXIMAL;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.MINIMAL;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIcf;
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
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0003;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0006;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceFK7804 implements TestabilityCertificateFillService {

  private static final List<ElementId> MAXIMAL_IDS = List.of(
      QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
      QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
      QUESTION_SYSSELSATTNING_ID,
      QUESTION_YRKE_ARBETSUPPGIFTER_ID,
      QUESTION_DIAGNOS_ID,
      QUESTION_MEDICINSK_BEHANDLING_ID,
      QUESTION_NEDSATTNING_ARBETSFORMAGA_ID,
      QUESTION_ARBETFORMAGA_LANGRE_ID,
      QUESTION_TRANSPORTSTOD_ID,
      QUESTION_SVARARE_ATERGANG_VID_OJAMN_ARBETSTID_ID,
      QUESTION_MEDICINSKA_SKAL_ID,
      QUESTION_PROGNOS_ID,
      QUESTION_ANTAL_MANADER_ID,
      QUESTION_ATGARDER_ID,
      QUESTION_OVRIGT_ID,
      QUESTION_KONTAKT_ID,
      QUESTION_VARFOR_KONTAKT_ID,
      QUESTION_AKTIVITETSBEGRANSNING_ID,
      QUESTION_FUNKTIONSNEDSATTNINGAR_ID
  );

  private static final List<ElementId> MINIMAL_IDS = List.of(
      QUESTION_SMITTBARARPENNING_ID,
      QUESTION_NEDSATTNING_ARBETSFORMAGA_ID,
      QUESTION_DIAGNOS_ID
  );

  private static final Map<ElementId, String> TEXT_QUESTION_MOCKS = Map.ofEntries(
      Map.entry(QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
          "Patienten har fått rådgivning via e-post."),
      Map.entry(QUESTION_YRKE_ARBETSUPPGIFTER_ID,
          "Butikssäljare, ansvarar för kassa och varuplock."),
      Map.entry(QUESTION_MEDICINSK_BEHANDLING_ID, "Rehabövningar under 10 dagar hemifrån."),
      Map.entry(QUESTION_ARBETFORMAGA_LANGRE_ID, "Fysiskt krävande arbete."),
      Map.entry(QUESTION_MEDICINSKA_SKAL_ID,
          "Behöver regelbundna vilopauser och kan inte arbeta heltid."),
      Map.entry(QUESTION_GRUND_FOR_BEDOMNING_ID,
          "Patienten har långvariga besvär och tidigare behandlingar har haft begränsad effekt."),
      Map.entry(QUESTION_ATGARDER_ID,
          "Arbetsanpassning med höj- och sänkbart skrivbord och flexibla arbetstider."),
      Map.entry(QUESTION_OVRIGT_ID,
          "Patienten har svårt att ta sig till arbetsplatsen med kollektivtrafik."),
      Map.entry(QUESTION_VARFOR_KONTAKT_ID,
          "Behöver diskutera kompletterande intyg med handläggare."),
      Map.entry(QUESTION_AKTIVITETSBEGRANSNING_ID,
          "Patienten har svårt att lyfta tunga föremål och behöver undvika långvarigt stående arbete."),
      Map.entry(QUESTION_FUNKTIONSNEDSATTNINGAR_ID,
          "Patienten har nedsatt rörlighet i höger arm och behöver hjälp med vissa rörelser.")
  );

  @Override
  public List<CertificateModelId> certificateModelIds() {
    return List.of(
        FK7804_V2_0
    );
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
          .value(elementValueBoolean.withValue(getBoolean(elementSpecification.id(), fillType)))
          .build();
    }

    if (value instanceof ElementValueCode) {
      final var code = getCode(elementSpecification.id(), fillType).code();
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(
              ElementValueCode.builder()
                  .code(code)
                  .codeId(new FieldId(code))
                  .build()
          )
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

    if (value instanceof ElementValueDateRangeList elementValueDateRangeList) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(
              elementValueDateRangeList.withDateRangeList(List.of(
                      getDateRange(fillType)
                  )
              )
          )
          .build();
    }

    if (value instanceof ElementValueInteger) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(
              ElementValueInteger.builder()
                  .value(10)
                  .unitOfMeasurement("månader")
                  .integerId(QUESTION_ANTAL_MANADER_FIELD_ID)
                  .build()
          )
          .build();
    }

    if (value instanceof ElementValueCodeList elementValueCodeList) {
      final var code = getCode(elementSpecification.id(), fillType).code();
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(
              elementValueCodeList.withList(List.of(
                  ElementValueCode.builder()
                      .code(code)
                      .codeId(new FieldId(code))
                      .build()
              ))
          )
          .build();
    }

    if (value instanceof ElementValueIcf elementValueIcf) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(
              elementValueIcf.withText(
                  TEXT_QUESTION_MOCKS.get(elementSpecification.id())
              )
          ).build();
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

    if (elementId.equals(QUESTION_SYSSELSATTNING_ID)) {
      if (fillType == MINIMAL) {
        return CodeSystemKvFkmu0002.FORALDRALEDIG;
      }
      return CodeSystemKvFkmu0002.NUVARANDE_ARBETE;
    }

    if (elementId.equals(QUESTION_PROGNOS_ID)) {
      if (fillType == MINIMAL) {
        return CodeSystemKvFkmu0006.STOR_SANNOLIKHET;
      }
      return CodeSystemKvFkmu0006.ATER_X_ANTAL_MANADER;
    }

    throw new IllegalStateException("Unexpected code for elementId: " + elementId);
  }

  private static boolean getBoolean(ElementId elementId, TestabilityFillTypeDTO fillType) {
    if (elementId == QUESTION_SMITTBARARPENNING_ID) {
      return fillType == MINIMAL;
    }

    return fillType == MAXIMAL;
  }

  private static DateRange getDateRange(TestabilityFillTypeDTO fillType) {
    if (fillType == MINIMAL) {
      return DateRange.builder()
          .dateRangeId(new FieldId(CodeSystemKvFkmu0003.HELT_NEDSATT.code()))
          .from(LocalDate.now().minusDays(7))
          .to(LocalDate.now().plusDays(30))
          .build();
    }

    return DateRange.builder()
        .dateRangeId(new FieldId(CodeSystemKvFkmu0003.EN_FJARDEDEL.code()))
        .from(LocalDate.now().minusDays(7))
        .to(LocalDate.now().plusDays(30))
        .build();
  }
}
