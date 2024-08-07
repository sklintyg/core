package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.fk7809;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.FYSIOTERAPEUT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.NEUROPSYKIATRISKT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.ORTOPEDTEKNIKER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.FK7809_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionAktivitetsbegransningar.QUESTION_AKTIVITETSBEGRANSNINGAR_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionAnnanGrundForMedicinsktUnderlag.QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionAnnanKroppsligFunktionMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_ANNAN_KROPPSILIG_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionBaseratPaAnnatMedicinsktUnderlag.QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionDiagnos.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionDiagnos.DIAGNOS_1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionDiagnos.DIAGNOS_2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionDiagnos.DIAGNOS_3;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionDiagnos.DIAGNOS_4;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionDiagnos.DIAGNOS_5;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionDiagnosHistorik.DIAGNOSIS_MOTIVATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_ANNAN_KROPPSILIG_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_HORSELFUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_KOORDINATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_PSYKISK_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_SINNESFUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_SYNFUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_UPPMAKRMSAHET_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionHorselFunktionMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_HORSELFUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionIntellektuellFunktionMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_INTELLEKTUELL_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionKommunikationSocialInteraktionMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionKoordinationMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_KOORDINATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionOvrigt.QUESTION_OVRIGT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionPagaendeOchPlaneradeBehandlingar.QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionPrognos.QUESTION_PROGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionPsykiskFunktionMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_PSYKISK_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionRelationTillPatienten.QUESTION_RELATION_TILL_PATIENTEN_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionSinnesfunktionMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_SINNESFUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionSynfunktionMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_SYNFUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionUppmarksamhetMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_UPPMAKRMSAHET_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionUtredningEllerUnderlag.MEDICAL_INVESTIGATION_FIELD_ID_1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionUtredningEllerUnderlag.MEDICAL_INVESTIGATION_FIELD_ID_2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionUtredningEllerUnderlag.MEDICAL_INVESTIGATION_FIELD_ID_3;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionUtredningEllerUnderlag.QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionVardenhetOchTidplan.QUESTION_VARDENHET_OCH_TIDPLAN_ID;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.EMPTY;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.MAXIMAL;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.diagnosisList;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.elementData;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.emptyValue;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.investigation;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.now;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.nowPlusDays;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.spec;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.valueCode;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.valueDate;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.valueDiagnosis;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.valueText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceFK7809 implements
    TestabilityCertificateFillService {

  private static final FieldId MED_INVEST_1_DATE = new FieldId("medicalInvestigation1_DATE");
  private static final FieldId MED_INVEST_2_DATE = new FieldId("medicalInvestigation2_DATE");
  private static final FieldId MED_INVEST_3_DATE = new FieldId("medicalInvestigation3_DATE");
  private static final FieldId MED_INVEST_1_TYPE = new FieldId(
      "medicalInvestigation1_INVESTIGATION_TYPE");
  private static final FieldId MED_INVEST_2_TYPE = new FieldId(
      "medicalInvestigation2_INVESTIGATION_TYPE");
  private static final FieldId MED_INVEST_3_TYPE = new FieldId(
      "medicalInvestigation3_INVESTIGATION_TYPE");
  private static final FieldId MED_INVEST_1_SOURCE = new FieldId(
      "medicalInvestigation1_INFORMATION_SOURCE");
  private static final FieldId MED_INVEST_2_SOURCE = new FieldId(
      "medicalInvestigation2_INFORMATION_SOURCE");
  private static final FieldId MED_INVEST_3_SOURCE = new FieldId(
      "medicalInvestigation3_INFORMATION_SOURCE");

  @Override
  public List<CertificateModelId> certificateModelIds() {
    return List.of(FK7809_V1_0);
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
    final var specBaseratPa =
        spec(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID, certificateModel);
    final var specBaseratPaAnhorig =
        spec(QUESTION_RELATION_TILL_PATIENTEN_ID, certificateModel);
    final var specBaseratPaAnnat =
        spec(QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID, certificateModel);
    final var specAnnanUtredning =
        spec(QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID, certificateModel);
    final var specMedicinskaUtredningar =
        spec(QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_ID, certificateModel);
    final var specDiagnos =
        spec(DIAGNOSIS_ID, certificateModel);
    final var specDiagnoshistorik =
        spec(DIAGNOSIS_MOTIVATION_ID, certificateModel);
    final var specFunktionsnedsattning =
        spec(FUNKTIONSNEDSATTNING_ID, certificateModel);
    final var specIntellektuell =
        spec(FUNKTIONSNEDSATTNING_MOTIVERING_INTELLEKTUELL_FUNKTION_ID, certificateModel);
    final var specPsykosocial =
        spec(FUNKTIONSNEDSATTNING_MOTIVERING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID, certificateModel);
    final var specUppmarksamhet =
        spec(FUNKTIONSNEDSATTNING_MOTIVERING_UPPMAKRMSAHET_ID, certificateModel);
    final var specPsykisk =
        spec(FUNKTIONSNEDSATTNING_MOTIVERING_PSYKISK_FUNKTION_ID, certificateModel);
    final var specHorsel =
        spec(FUNKTIONSNEDSATTNING_MOTIVERING_HORSELFUNKTION_ID, certificateModel);
    final var specSyn =
        spec(FUNKTIONSNEDSATTNING_MOTIVERING_SYNFUNKTION_ID, certificateModel);
    final var specSinnes =
        spec(FUNKTIONSNEDSATTNING_MOTIVERING_SINNESFUNKTION_ID, certificateModel);
    final var specKoordination =
        spec(FUNKTIONSNEDSATTNING_MOTIVERING_KOORDINATION_ID, certificateModel);
    final var specAnnanKroppslig =
        spec(FUNKTIONSNEDSATTNING_MOTIVERING_ANNAN_KROPPSILIG_FUNKTION_ID, certificateModel);
    final var specAktivitetsbegransning =
        spec(QUESTION_AKTIVITETSBEGRANSNINGAR_ID, certificateModel);
    final var specBehandling =
        spec(QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID, certificateModel);
    final var specAnsvarigVardenhet =
        spec(QUESTION_VARDENHET_OCH_TIDPLAN_ID, certificateModel);
    final var specPrognos =
        spec(QUESTION_PROGNOS_ID, certificateModel);
    final var specOvrigt =
        spec(QUESTION_OVRIGT_ID, certificateModel);

    baseratPa(specBaseratPa, elementData, fillType);
    baseratPaAnhorig(specBaseratPaAnhorig, elementData, fillType);
    baseratPaAnnat(specBaseratPaAnnat, elementData, fillType);
    annanUtredning(specAnnanUtredning, elementData, fillType);
    medicinskaUtredningar(specMedicinskaUtredningar, elementData, fillType);
    diagnos(specDiagnos, elementData, fillType);
    diagnoshistorik(specDiagnoshistorik, elementData);
    funktionsnedsattning(specFunktionsnedsattning, elementData, fillType);
    intellektuell(specIntellektuell, elementData);
    psykosocial(specPsykosocial, elementData, fillType);
    uppmarksamhet(specUppmarksamhet, elementData, fillType);
    psykisk(specPsykisk, elementData, fillType);
    horsel(specHorsel, elementData, fillType);
    syn(specSyn, elementData, fillType);
    sinnes(specSinnes, elementData, fillType);
    koordination(specKoordination, elementData, fillType);
    annanKroppslig(specAnnanKroppslig, elementData, fillType);
    aktivitetsbegransning(specAktivitetsbegransning, elementData, fillType);
    behandling(specBehandling, elementData, fillType);
    ansvarigVardenhet(specAnsvarigVardenhet, elementData, fillType);
    prognos(specPrognos, elementData);
    ovrigt(specOvrigt, elementData, fillType);

    return elementData;
  }

  private static void baseratPa(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueDateList elementValueDateList) {
      final var dateList = List.of(
          valueDate(new FieldId(UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID), now()),
          valueDate(new FieldId(UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID), now()),
          valueDate(UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID, now()),
          valueDate(UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID, now())
      );

      final var valueDateList = elementValueDateList
          .withDateList(fillType == MAXIMAL ? dateList : dateList.subList(0, 1));
      list.add(elementData(spec.id(), valueDateList));
    }
  }

  private static void baseratPaAnhorig(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Anhörigs relation");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void baseratPaAnnat(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Anger vad annat är");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void annanUtredning(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueBoolean elementValueBoolean) {
      list.add(elementData(spec.id(), elementValueBoolean.withValue(fillType == MAXIMAL)));
    }
  }

  private static void medicinskaUtredningar(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL &&
        emptyValue(spec) instanceof ElementValueMedicalInvestigationList medicalInvestigationList) {
      final var investigations = List.of(
          investigation(MEDICAL_INVESTIGATION_FIELD_ID_1,
              valueDate(MED_INVEST_1_DATE, nowPlusDays(0)),
              valueCode(MED_INVEST_1_TYPE, NEUROPSYKIATRISKT.code()),
              valueText(MED_INVEST_1_SOURCE, "Neurokliniken")),
          investigation(MEDICAL_INVESTIGATION_FIELD_ID_2,
              valueDate(MED_INVEST_2_DATE, nowPlusDays(0)),
              valueCode(MED_INVEST_2_TYPE, FYSIOTERAPEUT.code()),
              valueText(MED_INVEST_2_SOURCE, "Fysiologikliniken")),
          investigation(MEDICAL_INVESTIGATION_FIELD_ID_3,
              valueDate(MED_INVEST_3_DATE, nowPlusDays(0)),
              valueCode(MED_INVEST_3_TYPE, ORTOPEDTEKNIKER.code()),
              valueText(MED_INVEST_3_SOURCE, "Ortopedkliniken")));

      final var investigationList = medicalInvestigationList.withList(investigations);
      list.add(elementData(spec.id(), investigationList));
    }
  }

  private static void diagnos(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueDiagnosisList) {
      final var diagnoses = List.of(
          valueDiagnosis(DIAGNOS_1, "A78", "Q-feber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID),
          valueDiagnosis(DIAGNOS_2, "A25", "Råttbettsfeber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID),
          valueDiagnosis(DIAGNOS_3, "A23", "Undulantfeber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID),
          valueDiagnosis(DIAGNOS_4, "A984", "Ebolafeber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID),
          valueDiagnosis(DIAGNOS_5, "A010", "Tyfoidfeber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
      );

      final var diagnosisList = fillType == MAXIMAL ? diagnoses : diagnoses.subList(0, 1);
      list.add(elementData(spec.id(), diagnosisList(diagnosisList)));
    }
  }

  private static void diagnoshistorik(ElementSpecification spec, List<ElementData> list) {
    if (emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Diagnoshistorik");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void funktionsnedsattning(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueCodeList elementValueCodeList) {
      final var codes = List.of(
          valueCode(
              FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID,
              FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID.value()),
          valueCode(
              FUNKTIONSNEDSATTNING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID,
              FUNKTIONSNEDSATTNING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID.value()),
          valueCode(
              FUNKTIONSNEDSATTNING_UPPMAKRMSAHET_ID,
              FUNKTIONSNEDSATTNING_UPPMAKRMSAHET_ID.value()),
          valueCode(
              FUNKTIONSNEDSATTNING_PSYKISK_FUNKTION_ID,
              FUNKTIONSNEDSATTNING_PSYKISK_FUNKTION_ID.value()),
          valueCode(
              FUNKTIONSNEDSATTNING_HORSELFUNKTION_ID,
              FUNKTIONSNEDSATTNING_HORSELFUNKTION_ID.value()),
          valueCode(
              FUNKTIONSNEDSATTNING_SYNFUNKTION_ID,
              FUNKTIONSNEDSATTNING_SYNFUNKTION_ID.value()),
          valueCode(
              FUNKTIONSNEDSATTNING_SINNESFUNKTION_ID,
              FUNKTIONSNEDSATTNING_SINNESFUNKTION_ID.value()),
          valueCode(
              FUNKTIONSNEDSATTNING_KOORDINATION_ID,
              FUNKTIONSNEDSATTNING_KOORDINATION_ID.value()),
          valueCode(
              FUNKTIONSNEDSATTNING_ANNAN_KROPPSILIG_FUNKTION_ID,
              FUNKTIONSNEDSATTNING_ANNAN_KROPPSILIG_FUNKTION_ID.value()));

      final var codeList = elementValueCodeList.withList(
          fillType == MAXIMAL ? codes : codes.subList(0, 1));
      list.add(elementData(spec.id(), codeList));
    }
  }

  private static void intellektuell(ElementSpecification spec, List<ElementData> list) {
    if (emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Intellektuell funktion");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void psykosocial(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Psykosocial funktion");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void uppmarksamhet(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Uppmärksamhetsproblem");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void psykisk(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Psykisk funktion");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void horsel(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Hörselfunktion");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void syn(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Synfunktion");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void sinnes(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Sinnesfunktion");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void koordination(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Koordinationsproblem");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void annanKroppslig(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Annan kroppslig funktion");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void aktivitetsbegransning(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Aktivitetsbegränsning");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void behandling(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Medicinsk behandling");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void ansvarigVardenhet(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Ansvarig vårdenhet");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void prognos(ElementSpecification spec, List<ElementData> list) {
    if (emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Prognos");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void ovrigt(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Övrigt");
      list.add(elementData(spec.id(), text));
    }
  }
}
