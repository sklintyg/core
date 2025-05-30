package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.fk7427;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.CertificateModelFactoryFK7427.FK7427_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionAnnanGrundForMedicinsktUnderlag.QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionDiagnos.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionDiagnos.DIAGNOS_1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionDiagnos.DIAGNOS_2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionDiagnos.DIAGNOS_3;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_DIGITALT_VARDMOTE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_FORALDERS_BESKRIVNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_FYSISKT_MOTE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionHalsotillstand.QUESTION_HALSOTILLSTAND_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionPagaendeOchPlaneradeBehandlingar.QUESTION_PAGAENDE_OCH_PLANERAD_BEHANDLING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionPeriodInneliggandePaSjukhus.QUESTION_PERIOD_INNELIGGANDE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionPeriodVardEllerTillsyn.QUESTION_PERIOD_VARD_ELLER_TILLSYN_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionSymtom.QUESTION_SYMTOM_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionVardEllerTillsyn.QUESTION_VARD_ELLER_TILLSYN_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionVardasBarnetInneliggandePaSjukhus.QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.EMPTY;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.MAXIMAL;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.diagnosisList;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.elementData;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.emptyValue;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.now;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.spec;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.valueDate;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.valueDiagnosis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceFK7427 implements
    TestabilityCertificateFillService {

  @Override
  public List<CertificateModelId> certificateModelIds() {
    return List.of(FK7427_V1_0);
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
    final var elementData = new ArrayList<ElementData>();
    final var specGrundForMedicinsktUnderlag = spec(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
        certificateModel);
    final var specAnnanGrundForMedicinskitUnderlag = spec(
        QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID, certificateModel);
    final var specDiagnos = spec(DIAGNOSIS_ID, certificateModel);
    final var specSymtom = spec(QUESTION_SYMTOM_ID, certificateModel);
    final var specHalsotillstand = spec(QUESTION_HALSOTILLSTAND_ID, certificateModel);
    final var specVardEllerTillsyn = spec(QUESTION_VARD_ELLER_TILLSYN_ID, certificateModel);
    final var specVardasBarnetInneliggandePaSjukhus = spec(
        QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID, certificateModel);
    final var specPagaendeOchPlaneradBehandling = spec(QUESTION_PAGAENDE_OCH_PLANERAD_BEHANDLING_ID,
        certificateModel);
    final var specPeriodVardEllerTillsyn = spec(QUESTION_PERIOD_VARD_ELLER_TILLSYN_ID,
        certificateModel);
    final var specPeriodInneliggandePaSjukhus = spec(QUESTION_PERIOD_INNELIGGANDE_ID,
        certificateModel);

    grundForMedicinsktUnderlag(specGrundForMedicinsktUnderlag, elementData, fillType);
    baseratPaAnnat(specAnnanGrundForMedicinskitUnderlag, elementData, fillType);
    diagnos(specDiagnos, elementData, fillType);
    symtom(specSymtom, elementData, fillType);
    halsotillstand(specHalsotillstand, elementData);
    vardEllerTillsyn(specVardEllerTillsyn, elementData);
    vardasBarnetInneliggandePaSjukhus(specVardasBarnetInneliggandePaSjukhus, elementData, fillType);
    behandling(specPagaendeOchPlaneradBehandling, elementData, fillType);
    periodVardEllerTillsyn(specPeriodVardEllerTillsyn, elementData, fillType);
    periodInneliggandePaSjukhus(specPeriodInneliggandePaSjukhus, elementData, fillType);

    return elementData;
  }

  private static void grundForMedicinsktUnderlag(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueDateList elementValueDateList) {
      final var dateList = List.of(
          valueDate(UTLATANDE_BASERAT_PA_FYSISKT_MOTE_FIELD_ID, now()),
          valueDate(UTLATANDE_BASERAT_PA_DIGITALT_VARDMOTE_FIELD_ID, now()),
          valueDate(UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID, now()),
          valueDate(UTLATANDE_BASERAT_PA_FORALDERS_BESKRIVNING_FIELD_ID, now()),
          valueDate(UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID, now())
      );

      final var valueDateList = elementValueDateList
          .withDateList(fillType == MAXIMAL ? dateList : dateList.subList(0, 1));
      list.add(elementData(spec.id(), valueDateList));
    }
  }

  private static void baseratPaAnnat(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Anger vad annat är");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void diagnos(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueDiagnosisList) {
      final var diagnoses = List.of(
          valueDiagnosis(DIAGNOS_1, "A78", "Q-feber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID),
          valueDiagnosis(DIAGNOS_2, "A25", "Råttbettsfeber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID),
          valueDiagnosis(DIAGNOS_3, "A23", "Undulantfeber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
      );

      final var diagnosisList = fillType == MAXIMAL ? diagnoses : diagnoses.subList(0, 1);
      list.add(elementData(spec.id(), diagnosisList(diagnosisList)));
    }
  }

  private static void symtom(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Barnet har haft den här symtomen");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void halsotillstand(ElementSpecification spec, List<ElementData> list) {
    if (emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Hälsotillståndet är stabilt");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void vardEllerTillsyn(ElementSpecification spec, List<ElementData> list) {
    if (emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Barnet har stort behov av vård och tillsyn");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void vardasBarnetInneliggandePaSjukhus(ElementSpecification spec,
      List<ElementData> list, TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueBoolean elementValueBoolean) {
      final var text = elementValueBoolean.withValue(fillType == MAXIMAL);
      list.add(elementData(spec.id(), text));
    }
  }

  private static void behandling(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Det finns behov av behandling");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void periodVardEllerTillsyn(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueDateRange elementValueDateRange) {
      final var valueDateRange = elementValueDateRange
          .withFromDate(now())
          .withToDate(fillType == MAXIMAL ? now().plusDays(7) : now());
      list.add(elementData(spec.id(), valueDateRange));
    }
  }

  private static void periodInneliggandePaSjukhus(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueDateRange elementValueDateRange) {
      final var valueDateRange = elementValueDateRange
          .withFromDate(now())
          .withToDate(fillType == MAXIMAL ? now().plusDays(5) : now());
      list.add(elementData(spec.id(), valueDateRange));
    }
  }
}
