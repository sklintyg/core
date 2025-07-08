package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.fk3226;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0009.AKUT_LIVSHOTANDE;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0009.ENDAST_PALLIATIV;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.FK3226_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionDiagnos.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionDiagnos.DIAGNOS_1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionDiagnos.DIAGNOS_2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionDiagnos.DIAGNOS_3;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionDiagnos.DIAGNOS_4;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionDiagnos.DIAGNOS_5;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionForutsattningarForAttLamnaSkriftligtSamtycke.FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionNarAktivaBehandlingenAvslutades.QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionNarTillstandetBlevAkutLivshotande.QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatagligtHotMotPatientensLivAkutLivshotande.QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_AKUT_LIVSHOTANDE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatientensBehandlingOchVardsituation.AKUT_LIVSHOTANDE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatientensBehandlingOchVardsituation.ENDAST_PALLIATIV_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatientensBehandlingOchVardsituation.QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionTillstandetUppskattasLivshotandeTillOchMed.QUESTION_TILLSTANDET_UPPSKATTAS_LIVSHOTANDE_TILL_OCH_MED_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUppskattaHurLangeTillstandetKommerVaraLivshotande.QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUtlatandeBaseratPa.QUESTION_UTLATANDE_BASERAT_PA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUtlatandeBaseratPa.UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUtlatandeBaseratPa.UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUtlatandeBaseratPa.UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUtlatandeBaseratPaAnnat.QUESTION_UTLATANDE_BASERAT_PA_ANNAT_ID;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.EMPTY;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.MAXIMAL;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.diagnosisList;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.elementData;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.emptyValue;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.now;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.nowPlusDays;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.spec;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.valueCode;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.valueDate;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.valueDiagnosis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceFK3226 implements TestabilityCertificateFillService {

  @Override
  public List<CertificateModelId> certificateModelIds() {
    return List.of(FK3226_V1_0);
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
        spec(QUESTION_UTLATANDE_BASERAT_PA_ID, certificateModel);
    final var specBaseratPaAnnat =
        spec(QUESTION_UTLATANDE_BASERAT_PA_ANNAT_ID, certificateModel);
    final var specDiagnosis =
        spec(DIAGNOSIS_ID, certificateModel);
    final var specBehandlingOchVard =
        spec(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID, certificateModel);
    final var specBehandlingAvslutad =
        spec(QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_ID, certificateModel);
    final var specLivshotandeNar =
        spec(QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_ID, certificateModel);
    final var specLivshotandeHur =
        spec(QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_AKUT_LIVSHOTANDE_ID, certificateModel);
    final var specKanUppskatta =
        spec(QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID, certificateModel);
    final var specUppskattasTill =
        spec(QUESTION_TILLSTANDET_UPPSKATTAS_LIVSHOTANDE_TILL_OCH_MED_ID, certificateModel);
    final var specSamtycke =
        spec(FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID, certificateModel);

    baseratPa(specBaseratPa, elementData, fillType);
    baseratPaAnnat(specBaseratPaAnnat, elementData, fillType);
    diagnos(specDiagnosis, elementData, fillType);
    behandlingOchVard(specBehandlingOchVard, elementData, fillType);
    behandlingAvslutad(specBehandlingAvslutad, elementData, fillType);
    livshotandeNar(specLivshotandeNar, elementData, fillType);
    livshotandeHur(specLivshotandeHur, elementData, fillType);
    kanUppskatta(specKanUppskatta, elementData, fillType);
    uppskattasTill(specUppskattasTill, elementData, fillType);
    samtycke(specSamtycke, elementData);

    return elementData;
  }

  private static void baseratPa(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueDateList elementValueDateList) {
      final var dateList = List.of(
          valueDate(new FieldId(UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID), now()),
          valueDate(new FieldId(UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID), now()),
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
          valueDiagnosis(DIAGNOS_3, "A23", "Undulantfeber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID),
          valueDiagnosis(DIAGNOS_4, "A984", "Ebolafeber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID),
          valueDiagnosis(DIAGNOS_5, "A010", "Tyfoidfeber", CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
      );

      final var diagnosisList = fillType == MAXIMAL ? diagnoses : diagnoses.subList(0, 1);
      list.add(elementData(spec.id(), diagnosisList(diagnosisList)));
    }
  }

  private static void behandlingOchVard(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueCode) {
      final var code = fillType == MAXIMAL
          ? valueCode(AKUT_LIVSHOTANDE_FIELD_ID, AKUT_LIVSHOTANDE.code())
          : valueCode(ENDAST_PALLIATIV_FIELD_ID, ENDAST_PALLIATIV.code());

      list.add(elementData(spec.id(), code));
    }
  }

  private static void behandlingAvslutad(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType != MAXIMAL && emptyValue(spec) instanceof ElementValueDate elementValueDate) {
      list.add(elementData(spec.id(), elementValueDate.withDate(now())));
    }
  }

  private static void livshotandeNar(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueDate elementValueDate) {
      list.add(elementData(spec.id(), elementValueDate.withDate(now())));
    }
  }

  private static void livshotandeHur(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Anger livshotande hur");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void kanUppskatta(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec)
        instanceof ElementValueBoolean elementValueBoolean) {
      list.add(elementData(spec.id(), elementValueBoolean.withValue(true)));
    }
  }

  private static void uppskattasTill(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (fillType == MAXIMAL && emptyValue(spec) instanceof ElementValueDate elementValueDate) {
      list.add(elementData(spec.id(), elementValueDate.withDate(nowPlusDays(7L))));
    }
  }

  private static void samtycke(ElementSpecification spec, List<ElementData> list) {
    if (emptyValue(spec) instanceof ElementValueBoolean elementValueBoolean) {
      list.add(elementData(spec.id(), elementValueBoolean.withValue(true)));
    }
  }
}
