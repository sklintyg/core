package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.fk7810;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.FYSIOTERAPEUT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.NEUROPSYKIATRISKT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0005.VARDCENTRAL;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0010.MINDRE_AN_ETT_AR;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.CertificateModelFactoryFK7810.FK7810_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_KOMMUNIKATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_LARANDE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_MOVEMENT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_OVRIG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.AKTIVITETSBAGRENSNINGAR_PERSONAL_CARE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAndningsFunktionMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_ANDNINGS_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAnnanGrundForMedicinsktUnderlag.QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAnnanKroppsligFunktionMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_ANNAN_KROPPSILIG_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionBaseratPaAnnatMedicinsktUnderlag.QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionDiagnos.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionDiagnos.DIAGNOS_1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionDiagnos.DIAGNOS_2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionDiagnos.DIAGNOS_3;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionDiagnos.DIAGNOS_4;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionDiagnos.DIAGNOS_5;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionDiagnosHistorik.DIAGNOSIS_MOTIVATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionForflyttningBegransningMotivering.AKTIVITETSBEGRANSNING_MOTIVERING_FORFLYTTNING_BEGRANSNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_ANDNINGS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_ANNAN_KROPPSILIG_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_KOORDINATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_PSYKISK_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_SINNESFUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_UPPMAKRMSAHET_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionGrundForMedicinsktUnderlag.UNDERSOKNING_VID_DIGITALT_VARDMOTE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionGrundForMedicinsktUnderlag.UNDERSOKNING_VID_FYSISKT_VARDMOTE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionIntellektuellFunktionMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_INTELLEKTUELL_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionKannedomOmPatienten.QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionKommunikationBegransningMotivering.AKTIVITETSBEGRANSNING_MOTIVERING_KOMMUNIKATION_BEGRANSNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionKommunikationSocialInteraktionMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionKoordinationMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_KOORDINATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionLarandeBegransningMotivering.AKTIVITETSBEGRANSNING_MOTIVERING_LARANDE_BEGRANSNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionOvrigaBegransningMotivering.AKTIVITETSBEGRANSNING_MOTIVERING_OVRIGT_BEGRANSNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionOvrigt.QUESTION_OVRIGT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionPagaendeOchPlaneradeBehandlingar.QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionPersonligVardBegransningMotivering.AKTIVITETSBEGRANSNING_MOTIVERING_PERSONLIG_VARD_BEGRANSNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionPrognos.QUESTION_PROGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionPsykiskFunktionMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_PSYKISK_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionRelationTillPatienten.QUESTION_RELATION_TILL_PATIENTEN_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSinnesfunktionMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_SINNESFUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsEgenvard.QUESTION_SJUKVARDANDE_INSATS_EGENVARD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsEgenvardInsatser.QUESTION_SJUKVARDANDE_INSATS_EGENVARD_INSATSER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsHSL.QUESTION_SJUKVARDANDE_INSATS_HSL_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsHSLInsatser.QUESTION_SJUKVARDANDE_INSATS_HSL_INSATSER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionUppmarksamhetMotivering.FUNKTIONSNEDSATTNING_MOTIVERING_UPPMAKRMSAHET_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionUtredningEllerUnderlag.MEDICAL_INVESTIGATION_FIELD_ID_1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionUtredningEllerUnderlag.MEDICAL_INVESTIGATION_FIELD_ID_2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionUtredningEllerUnderlag.MEDICAL_INVESTIGATION_FIELD_ID_3;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionUtredningEllerUnderlag.QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionVardenhetOchTidplan.QUESTION_VARDENHET_OCH_TIDPLAN_ID;
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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
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
public class TestabilityCertificateFillServiceFK7810 implements
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
    return List.of(FK7810_V1_0);
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
    final var specKannedomOmPatienten =
        spec(QUESTION_GRUND_FOR_KANNEDOM_OM_PATIENTEN_ID, certificateModel);
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
    final var specSinnes =
        spec(FUNKTIONSNEDSATTNING_MOTIVERING_SINNESFUNKTION_ID, certificateModel);
    final var specKoordination =
        spec(FUNKTIONSNEDSATTNING_MOTIVERING_KOORDINATION_ID, certificateModel);
    final var specAndning =
        spec(FUNKTIONSNEDSATTNING_MOTIVERING_ANDNINGS_FUNKTION_ID, certificateModel);
    final var specAnnanKroppslig =
        spec(FUNKTIONSNEDSATTNING_MOTIVERING_ANNAN_KROPPSILIG_FUNKTION_ID, certificateModel);
    final var specAktivitetsbegransning =
        spec(AKTIVITETSBAGRENSNINGAR_ID, certificateModel);
    final var specLarande =
        spec(AKTIVITETSBEGRANSNING_MOTIVERING_LARANDE_BEGRANSNING_ID, certificateModel);
    final var specKommunikation =
        spec(AKTIVITETSBEGRANSNING_MOTIVERING_KOMMUNIKATION_BEGRANSNING_ID, certificateModel);
    final var specForflyttning =
        spec(AKTIVITETSBEGRANSNING_MOTIVERING_FORFLYTTNING_BEGRANSNING_ID, certificateModel);
    final var specPersonligVard =
        spec(AKTIVITETSBEGRANSNING_MOTIVERING_PERSONLIG_VARD_BEGRANSNING_ID, certificateModel);
    final var specOvriga =
        spec(AKTIVITETSBEGRANSNING_MOTIVERING_OVRIGT_BEGRANSNING_ID, certificateModel);
    final var specBehandling =
        spec(QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID, certificateModel);
    final var specAnsvarigVardenhet =
        spec(QUESTION_VARDENHET_OCH_TIDPLAN_ID, certificateModel);
    final var specPrognos =
        spec(QUESTION_PROGNOS_ID, certificateModel);
    final var specSjukvardandeInsatserHSL =
        spec(QUESTION_SJUKVARDANDE_INSATS_HSL_ID, certificateModel);
    final var specSjukvardandeInsatserHSLInsatser =
        spec(QUESTION_SJUKVARDANDE_INSATS_HSL_INSATSER_ID, certificateModel);
    final var specSjukvardandeInsatserEgenvard =
        spec(QUESTION_SJUKVARDANDE_INSATS_EGENVARD_ID, certificateModel);
    final var specSjukvardandeInsatserEgenvardInsatser =
        spec(QUESTION_SJUKVARDANDE_INSATS_EGENVARD_INSATSER_ID, certificateModel);
    final var specOvrigt =
        spec(QUESTION_OVRIGT_ID, certificateModel);

    baseratPa(specBaseratPa, elementData, fillType);
    baseratPaAnhorig(specBaseratPaAnhorig, elementData, fillType);
    baseratPaAnnat(specBaseratPaAnnat, elementData, fillType);
    kannedomOmPatienten(specKannedomOmPatienten, elementData);
    annanUtredning(specAnnanUtredning, elementData, fillType);
    medicinskaUtredningar(specMedicinskaUtredningar, elementData, fillType);
    diagnos(specDiagnos, elementData, fillType);
    diagnoshistorik(specDiagnoshistorik, elementData);
    funktionsnedsattning(specFunktionsnedsattning, elementData, fillType);
    intellektuell(specIntellektuell, elementData);
    psykosocial(specPsykosocial, elementData, fillType);
    uppmarksamhet(specUppmarksamhet, elementData, fillType);
    psykisk(specPsykisk, elementData, fillType);
    sinnes(specSinnes, elementData, fillType);
    koordination(specKoordination, elementData, fillType);
    andning(specAndning, elementData, fillType);
    annanKroppslig(specAnnanKroppslig, elementData, fillType);
    aktivitetsbegransningar(specAktivitetsbegransning, elementData, fillType);
    larande(specLarande, elementData);
    kommunikation(specKommunikation, elementData, fillType);
    forflyttning(specForflyttning, elementData, fillType);
    personligtVard(specPersonligVard, elementData, fillType);
    ovriga(specOvriga, elementData, fillType);
    behandling(specBehandling, elementData, fillType);
    ansvarigVardenhet(specAnsvarigVardenhet, elementData, fillType);
    prognos(specPrognos, elementData);
    sjukvardandeInsatsHSL(specSjukvardandeInsatserHSL, elementData, fillType);
    sjukvardandeInsatsHSLInsatser(specSjukvardandeInsatserHSLInsatser, elementData, fillType);
    sjukvardandeInsatsEgenvard(specSjukvardandeInsatserEgenvard, elementData, fillType);
    sjukvardandeInsatsEgenvardInsatser(specSjukvardandeInsatserEgenvardInsatser, elementData,
        fillType);
    ovrigt(specOvrigt, elementData, fillType);

    return elementData;
  }

  private static void kannedomOmPatienten(ElementSpecification spec,
      ArrayList<ElementData> elementData) {
    if (emptyValue(spec) instanceof ElementValueCode) {
      elementData.add(elementData(spec.id(),
          valueCode(new FieldId(MINDRE_AN_ETT_AR.code()), MINDRE_AN_ETT_AR.code())));
    }
  }

  private static void baseratPa(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueDateList elementValueDateList) {
      final var dateList = List.of(
          valueDate(UNDERSOKNING_VID_FYSISKT_VARDMOTE_FIELD_ID, now()),
          valueDate(UNDERSOKNING_VID_DIGITALT_VARDMOTE_FIELD_ID, now()),
          valueDate(UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID, now()),
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
              valueCode(MED_INVEST_3_TYPE, VARDCENTRAL.code()),
              valueText(MED_INVEST_3_SOURCE, "Vårdcentral")));

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
              FUNKTIONSNEDSATTNING_SINNESFUNKTION_ID,
              FUNKTIONSNEDSATTNING_SINNESFUNKTION_ID.value()),
          valueCode(
              FUNKTIONSNEDSATTNING_KOORDINATION_ID,
              FUNKTIONSNEDSATTNING_KOORDINATION_ID.value()),
          valueCode(
              FUNKTIONSNEDSATTNING_ANDNINGS_ID,
              FUNKTIONSNEDSATTNING_ANDNINGS_ID.value()),
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

  private static void andning(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Andningsproblem");
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

  private static void aktivitetsbegransningar(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueCodeList elementValueCodeList) {
      final var codes = List.of(
          valueCode(
              AKTIVITETSBAGRENSNINGAR_LARANDE_ID,
              AKTIVITETSBAGRENSNINGAR_LARANDE_ID.value()),
          valueCode(
              AKTIVITETSBAGRENSNINGAR_KOMMUNIKATION_ID,
              AKTIVITETSBAGRENSNINGAR_KOMMUNIKATION_ID.value()),
          valueCode(
              AKTIVITETSBAGRENSNINGAR_MOVEMENT_ID,
              AKTIVITETSBAGRENSNINGAR_MOVEMENT_ID.value()),
          valueCode(
              AKTIVITETSBAGRENSNINGAR_PERSONAL_CARE_ID,
              AKTIVITETSBAGRENSNINGAR_PERSONAL_CARE_ID.value()),
          valueCode(
              AKTIVITETSBAGRENSNINGAR_OVRIG_ID,
              AKTIVITETSBAGRENSNINGAR_OVRIG_ID.value()));

      final var codeList = elementValueCodeList.withList(
          fillType == MAXIMAL ? codes : codes.subList(0, 1));
      list.add(elementData(spec.id(), codeList));
    }
  }

  private static void larande(ElementSpecification spec, List<ElementData> list) {
    if (emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText(
          "Lärande, tillämpa kunskap samt allmänna uppgifter och krav");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void kommunikation(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Kommunikation");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void forflyttning(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Förflyttning");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void personligtVard(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Personlig vård och sköta sin hälsa");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void ovriga(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Övriga aktivitetsbegränsningar");
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

  private static void sjukvardandeInsatsHSL(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {

    var value = fillType == MAXIMAL
        ? ElementValueBoolean.builder().booleanId(spec.configuration().id()).value(true).build()
        : ElementValueBoolean.builder().booleanId(spec.configuration().id()).value(false).build();

    list.add(elementData(spec.id(), value));

  }

  private static void sjukvardandeInsatsHSLInsatser(ElementSpecification spec,
      List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText(
          "Ange vilka insatser och i vilken omfattning");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void sjukvardandeInsatsEgenvard(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {

    var value = fillType == MAXIMAL
        ? ElementValueBoolean.builder().booleanId(spec.configuration().id()).value(true).build()
        : ElementValueBoolean.builder().booleanId(spec.configuration().id()).value(false).build();

    list.add(elementData(spec.id(), value));
  }

  private static void sjukvardandeInsatsEgenvardInsatser(ElementSpecification spec,
      List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText(
          "Ange vilka insatser och i vilken omfattning");
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