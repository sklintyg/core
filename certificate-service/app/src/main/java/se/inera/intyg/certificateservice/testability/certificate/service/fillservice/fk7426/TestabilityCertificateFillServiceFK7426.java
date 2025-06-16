package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.fk7426;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.CertificateModelFactoryFK7426.FK7426_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_FORALDERS_BESKRIVNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_FYSISKT_MOTE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionHalsotillstandSomatiska.QUESTION_HALSOTILLSTAND_SOMATISKA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPeriodSjukdom.QUESTION_PERIOD_SJUKDOM_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPeriodSjukdomMotivering.QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPeriodVardasBarnInneliggandePaSjukhus.QUESTION_PERIOD_VARDAS_BARN_INNE_SJUKHUS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPeriodVardasBarnetInskrivetMedHemsjukvard.QUESTION_PERIOD_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionVardasBarnetInneliggandePaSjukhus.QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionVardasBarnetInskrivetMedHemsjukvard.QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionDiagnos.DIAGNOS_1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionDiagnos.DIAGNOS_2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionDiagnos.DIAGNOS_3;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_DIGITALT_VARDMOTE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.MAXIMAL;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.now;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.valueDate;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.valueDiagnosis;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionAnnanGrundForMedicinsktUnderlag;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionDiagnos;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionGrundForBedomning;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionGrundForMedicinsktUnderlag;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionHalsotillstandPsykiska;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPagaendeBehandlingar;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPlaneradeBehandlingar;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionSymtom;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceFK7426 implements TestabilityCertificateFillService {

  private static final List<ElementId> MAXIMAL_IDS = List.of(
      QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
      QuestionAnnanGrundForMedicinsktUnderlag.QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
      QuestionDiagnos.DIAGNOSIS_ID,
      QuestionSymtom.QUESTION_SYMTOM_ID,
      QUESTION_HALSOTILLSTAND_SOMATISKA_ID,
      QuestionHalsotillstandPsykiska.QUESTION_HALSOTILLSTAND_PSYKISKA_ID,
      QuestionPagaendeBehandlingar.QUESTION_PAGAENDE_BEHANDLING_ID,
      QuestionPlaneradeBehandlingar.QUESTION_PLANERADE_BEHANDLING_ID,
      QuestionGrundForBedomning.QUESTION_GRUND_FOR_BEDOMNING_ID,
      QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID,
      QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID,
      QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID,
      QUESTION_PERIOD_SJUKDOM_ID,
      QUESTION_PERIOD_VARDAS_BARN_INNE_SJUKHUS_ID,
      QUESTION_PERIOD_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID
  );

  private static final List<ElementId> MINIMAL_IDS = List.of(
      QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
      QuestionDiagnos.DIAGNOSIS_ID,
      QUESTION_HALSOTILLSTAND_SOMATISKA_ID,
      QuestionGrundForBedomning.QUESTION_GRUND_FOR_BEDOMNING_ID,
      QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID,
      QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID,
      QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID,
      QUESTION_PERIOD_SJUKDOM_ID
  );

  @Override
  public List<CertificateModelId> certificateModelIds() {
    return List.of(FK7426_V1_0);
  }

  @Override
  public List<ElementData> fill(CertificateModel certificateModel,
      TestabilityFillTypeDTO fillType) {
    return fillType == TestabilityFillTypeDTO.EMPTY
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
          .value(elementValueBoolean.withValue(fillType == MAXIMAL))
          .build();
    }

    if (value instanceof ElementValueText elementValueText) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(elementValueText.withText("Example text"))
          .build();
    }

    if (value instanceof ElementValueDate elementValueDate) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(elementValueDate.withDate(LocalDate.now()))
          .build();
    }

    if (value instanceof ElementValueDateRange elementValueDateRange) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(ElementValueDateRange.builder()
              .fromDate(LocalDate.now().minusDays(7))
              .toDate(LocalDate.now())
              .id(elementValueDateRange.id())
              .build()
          ).build();
    }

    if (value instanceof ElementValueDateList elementValueDateList) {
      final var dateList = List.of(
          valueDate(UTLATANDE_BASERAT_PA_FYSISKT_MOTE_FIELD_ID, now()),
          valueDate(UTLATANDE_BASERAT_PA_DIGITALT_VARDMOTE_FIELD_ID, now()),
          valueDate(UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID, now()),
          valueDate(UTLATANDE_BASERAT_PA_FORALDERS_BESKRIVNING_FIELD_ID, now()),
          valueDate(UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID, now())
      );

      final var valueDateList = elementValueDateList
          .withDateList(fillType == MAXIMAL ? dateList : dateList.subList(0, 1));

      return ElementData.builder()
          .id(elementSpecification.id())
          .value(valueDateList)
          .build();
    }

    if (value instanceof ElementValueDiagnosisList elementValueDiagnosisList) {
      final var diagnoses = List.of(
          valueDiagnosis(DIAGNOS_1, "A78", "Q-feber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID),
          valueDiagnosis(DIAGNOS_2, "A25", "Råttbettsfeber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID),
          valueDiagnosis(DIAGNOS_3, "A23", "Undulantfeber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
      );

      final var diagnosisList = fillType == MAXIMAL ? diagnoses : diagnoses.subList(0, 1);
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(elementValueDiagnosisList.withDiagnoses(diagnosisList))
          .build();
    }

    return null;
  }
}