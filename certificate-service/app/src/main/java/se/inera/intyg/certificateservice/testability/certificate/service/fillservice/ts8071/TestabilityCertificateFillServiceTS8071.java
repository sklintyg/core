package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.ts8071;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIdKontroll.IDK1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvInformationskallaForIntyg.DISTANSKONTAKT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvInformationskallaForIntyg.JOURNALUPPGIFTER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.GR_II;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.TAXI;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs001.JA;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs002.NO;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.CertificateModelFactoryTS8071.TS8071_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionArytmi.QUESTION_ARYTMI_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionArytmiBeskrivning.QUESTION_ARYTMI_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBalanssinne.QUESTION_BALANSSINNE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBaseratPa.QUESTION_BASERAT_PA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBaseratPaDatum.QUESTION_BASERAT_PA_DATUM_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBedomning.QUESTION_BEDOMNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBedomningOkand.QUESTION_BEDOMNING_OKAND_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBedomningRisk.QUESTION_BEDOMNING_RISK_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsi.QUESTION_EPILEPSI_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiAnfall.QUESTION_EPILEPSI_ANFALL_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiAnfallBeskrivning.QUESTION_EPILEPSI_ANFALL_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiBeskrivning.QUESTION_EPILEPSI_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiMedicin.QUESTION_EPILEPSI_MEDICIN_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiMedicinBeskrivning.QUESTION_EPILEPSI_MEDICIN_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdom.QUESTION_HJARTSJUKDOM_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdomBehandlad.QUESTION_HJARTSJUKDOM_BEHANDLAD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionIdentitet.QUESTION_IDENTITET_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionIntygetAvser.QUESTION_INTYGET_AVSER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedicinering.QUESTION_MEDICINERING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedicineringBeskrivning.QUESTION_MEDICINERING_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedvetandestorning.QUESTION_MEDVETANDESTORNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedvetandestorningTidpunkt.QUESTION_MEDVETANDESTORNING_TIDPUNKT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMissbrukProvtagning.QUESTION_MISSBRUK_PROVTAGNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNjurfunktion.QUESTION_NJURFUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNjurtransplatation.QUESTION_NJURTRANSPLATATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNjurtransplatationTidpunkt.QUESTION_NJURTRANSPLATATION_TIDPUNKT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionOvrigBeskrivning.QUESTION_OVRIG_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionPsykiskBeskrivning.QUESTION_PSYKISK_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionRorlighet.QUESTION_RORLIGHET_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionRorlighetBeskrivning.QUESTION_RORLIGHET_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionRorlighetHjalpaPassagerare.QUESTION_RORLIGHET_HJALPA_PASSAGERARE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomEllerSynnedsattning.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomshistorik.QUESTION_SJUKDOMSHISTORIK_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSomnBehandling.QUESTION_SOMN_BEHANDLING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSomnBeskrivning.QUESTION_SOMN_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynkopeBeskrivning.QUESTION_SYNKOPE_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.QUESTION_SYNSKARPA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionBalanssinneBeskrivningV1.QUESTION_BALANSSINNE_BESKRIVNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionDemensBeskrivningV1.QUESTION_DEMENS_BESKRIVNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionDemensV1.QUESTION_DEMENS_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionDiabetesV1.QUESTION_DIABETES_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionEpilepsiMedicinTidpunktV1.QUESTION_EPILEPSI_MEDICIN_TIDPUNKT_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionHjartsjukdomBehandladBeskrivningV1.QUESTION_HJARTSJUKDOM_BEHANDLAD_BESKRIVNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionHjartsjukdomBeskrivningV1.QUESTION_HJARTSJUKDOM_BESKRIVNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionHorselV1.QUESTION_HORSEL_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionHorselhjalpmedelPositionV1.QUESTION_HORSELHJALPMEDEL_POSITION_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionHorselhjalpmedelV1.QUESTION_HORSELHJALPMEDEL_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKognitivStorningV1.QUESTION_KOGNITIV_STORNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1.QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_OVER_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKorrigeringAvSynskarpaKontaktlinserV1.QUESTION_KONTAKTLINSER_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKorrigeringAvSynskarpaStyrkaOverV1.QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_UNDER_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKorrigeringAvSynskarpaV1.QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionLakemedelBeskrivningV1.QUESTION_LAKEMEDEL_BESKRIVNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionLakemedelV1.QUESTION_LAKEMEDEL_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukBeskrivningV1.QUESTION_MISSBRUK_BESKRIVNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukJournaluppgifterBeskrivningV1.QUESTION_MISSBRUK_JOURNALUPPGIFTER_BESKRIVNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukJournaluppgifterV1.QUESTION_MISSBRUK_JOURNALUPPGIFTER_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukV1.QUESTION_MISSBRUK_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukVardBeskrivningV1.QUESTION_MISSBRUK_VARD_BESKRIVNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukVardV1.QUESTION_MISSBRUK_VARD_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeurologiskSjukdomBeskrivningV1.QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeurologiskSjukdomV1.QUESTION_NEUROLOGISK_SJUKDOM_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskLakemedelBeskrivningV1.QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskLakemedelV1.QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskTidpunktV1.QUESTION_NEUROPSYKIATRISK_TIDPUNKT_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskTrafikriskV1.QUESTION_NEUROPSYKIATRISK_TRAFIKRISK_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskV1.QUESTION_NEUROPSYKIATRISK_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionPsykiskTidpunktV1.QUESTION_PSYKISK_TIDPUNKT_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionPsykiskUtvecklingsstorningAllvarligV1.QUESTION_PSYKISK_UTVECKLINGSSTORNING_ALLVARLIG_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionPsykiskUtvecklingsstorningV1.QUESTION_PSYKISK_UTVECKLINGSSTORNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionPsykiskV1.QUESTION_PSYKISK_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSjukdomEllerSynnedsattningBeskrivningV1.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSjukdomshistorikBeskrivningV1.QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSomnV1.QUESTION_SOMN_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionStrokePaverkanV1.QUESTION_STROKE_PAVERKAN_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionStrokeV1.QUESTION_STROKE_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSynkopeV1.QUESTION_SYNKOPE_V1_ID;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.EMPTY;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.MAXIMAL;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.MINIMAL;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.VisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKorrigeringAvSynskarpa;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvAnatomiskLokalisationHorapparat;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs001;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceTS8071 implements
    TestabilityCertificateFillService {

  private static final List<ElementId> MAXIMAL_IDS = List.of(
      QUESTION_INTYGET_AVSER_ID, QUESTION_BASERAT_PA_ID, QUESTION_BASERAT_PA_DATUM_ID,
      QUESTION_SYNSKARPA_ID, QUESTION_IDENTITET_ID, QUESTION_SYNFUNKTIONER_ID,
      QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID, QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_OVER_V1_ID,
      QUESTION_KONTAKTLINSER_V1_ID, QUESTION_KORRIGERING_AV_SYNSKARPA_STRYKA_UNDER_V1_ID,
      QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID,
      QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_V1_ID, QUESTION_SJUKDOMSHISTORIK_ID,
      QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_V1_ID, QUESTION_BALANSSINNE_ID,
      QUESTION_BALANSSINNE_BESKRIVNING_V1_ID, QUESTION_HORSEL_V1_ID,
      QUESTION_HORSELHJALPMEDEL_V1_ID,
      QUESTION_HORSELHJALPMEDEL_POSITION_V1_ID, QUESTION_RORLIGHET_ID,
      QUESTION_RORLIGHET_BESKRIVNING_ID,
      QUESTION_RORLIGHET_HJALPA_PASSAGERARE_ID, QUESTION_HJARTSJUKDOM_ID,
      QUESTION_HJARTSJUKDOM_BESKRIVNING_V1_ID,
      QUESTION_HJARTSJUKDOM_BEHANDLAD_ID, QUESTION_HJARTSJUKDOM_BEHANDLAD_BESKRIVNING_V1_ID,
      QUESTION_ARYTMI_ID, QUESTION_ARYTMI_BESKRIVNING_ID, QUESTION_SYNKOPE_V1_ID,
      QUESTION_SYNKOPE_BESKRIVNING_ID, QUESTION_STROKE_V1_ID, QUESTION_STROKE_PAVERKAN_V1_ID,
      QUESTION_DIABETES_V1_ID, QUESTION_NEUROLOGISK_SJUKDOM_V1_ID,
      QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V1_ID, QUESTION_EPILEPSI_ID,
      QUESTION_EPILEPSI_BESKRIVNING_ID, QUESTION_EPILEPSI_ANFALL_ID,
      QUESTION_EPILEPSI_ANFALL_BESKRIVNING_ID, QUESTION_EPILEPSI_MEDICIN_ID,
      QUESTION_EPILEPSI_MEDICIN_BESKRIVNING_ID, QUESTION_EPILEPSI_MEDICIN_TIDPUNKT_V1_ID,
      QUESTION_MEDVETANDESTORNING_ID, QUESTION_MEDVETANDESTORNING_TIDPUNKT_ID,
      QUESTION_NJURFUNKTION_ID, QUESTION_NJURTRANSPLATATION_ID,
      QUESTION_NJURTRANSPLATATION_TIDPUNKT_ID, QUESTION_KOGNITIV_STORNING_V1_ID,
      QUESTION_DEMENS_V1_ID,
      QUESTION_DEMENS_BESKRIVNING_V1_ID, QUESTION_SOMN_V1_ID, QUESTION_SOMN_BESKRIVNING_ID,
      QUESTION_SOMN_BEHANDLING_ID, QUESTION_MISSBRUK_V1_ID, QUESTION_MISSBRUK_BESKRIVNING_V1_ID,
      QUESTION_MISSBRUK_JOURNALUPPGIFTER_V1_ID,
      QUESTION_MISSBRUK_JOURNALUPPGIFTER_BESKRIVNING_V1_ID,
      QUESTION_MISSBRUK_PROVTAGNING_ID, QUESTION_MISSBRUK_VARD_V1_ID,
      QUESTION_MISSBRUK_VARD_BESKRIVNING_V1_ID, QUESTION_LAKEMEDEL_V1_ID,
      QUESTION_LAKEMEDEL_BESKRIVNING_V1_ID, QUESTION_PSYKISK_V1_ID, QUESTION_PSYKISK_BESKRIVNING_ID,
      QUESTION_PSYKISK_TIDPUNKT_V1_ID, QUESTION_NEUROPSYKIATRISK_V1_ID,
      QUESTION_NEUROPSYKIATRISK_TRAFIKRISK_V1_ID, QUESTION_NEUROPSYKIATRISK_TIDPUNKT_V1_ID,
      QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_V1_ID,
      QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_V1_ID,
      QUESTION_PSYKISK_UTVECKLINGSSTORNING_V1_ID,
      QUESTION_PSYKISK_UTVECKLINGSSTORNING_ALLVARLIG_V1_ID,
      QUESTION_MEDICINERING_ID, QUESTION_MEDICINERING_BESKRIVNING_ID, QUESTION_OVRIG_BESKRIVNING_ID,
      QUESTION_BEDOMNING_ID, QUESTION_BEDOMNING_RISK_ID, QUESTION_BEDOMNING_OKAND_ID
  );

  private static final List<ElementId> MINIMAL_IDS = List.of(
      QUESTION_BASERAT_PA_ID, QUESTION_INTYGET_AVSER_ID,
      QUESTION_IDENTITET_ID, QUESTION_SYNFUNKTIONER_ID, QUESTION_BALANSSINNE_ID,
      QUESTION_HORSEL_V1_ID,
      QUESTION_HORSELHJALPMEDEL_V1_ID, QUESTION_RORLIGHET_ID,
      QUESTION_RORLIGHET_HJALPA_PASSAGERARE_ID, QUESTION_HJARTSJUKDOM_ID, QUESTION_DIABETES_V1_ID,
      QUESTION_NEUROLOGISK_SJUKDOM_V1_ID,
      QUESTION_EPILEPSI_ID, QUESTION_EPILEPSI_ANFALL_ID,
      QUESTION_MEDVETANDESTORNING_ID,
      QUESTION_NJURFUNKTION_ID, QUESTION_NJURTRANSPLATATION_ID,
      QUESTION_KOGNITIV_STORNING_V1_ID, QUESTION_DEMENS_V1_ID,
      QUESTION_SOMN_V1_ID, QUESTION_SOMN_BEHANDLING_ID, QUESTION_MISSBRUK_V1_ID,
      QUESTION_MISSBRUK_JOURNALUPPGIFTER_V1_ID, QUESTION_MISSBRUK_VARD_V1_ID,
      QUESTION_LAKEMEDEL_V1_ID, QUESTION_PSYKISK_V1_ID, QUESTION_NEUROPSYKIATRISK_V1_ID,
      QUESTION_PSYKISK_UTVECKLINGSSTORNING_V1_ID,
      QUESTION_MEDICINERING_ID, QUESTION_BEDOMNING_ID
  );

  @Override
  public List<CertificateModelId> certificateModelIds() {
    return List.of(TS8071_V1_0);
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

  private static ElementData fill(
      ElementSpecification elementSpecification, TestabilityFillTypeDTO fillType) {
    final var value = elementSpecification.configuration().emptyValue();
    if (value instanceof ElementValueBoolean elementValueBoolean) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(elementValueBoolean.withValue(getBoolean(elementSpecification.id(), fillType)))
          .build();
    }

    if (value instanceof ElementValueText elementValueText) {
      final var questionName = elementSpecification.configuration().name();
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(elementValueText.withText(
              String.format(
                  "Ett exempel på text för frågan %s",
                  elementSpecification.configuration().name()
              ).substring(0, questionName.length() < 50 ? questionName.length() - 1 : 49)))
          .build();
    }

    if (value instanceof ElementValueDate elementValueDate) {
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(elementValueDate.withDate(LocalDate.now()))
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

    if (value instanceof ElementValueVisualAcuities) {
      final var config = (ElementConfigurationVisualAcuities) elementSpecification.configuration();
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(
              ElementValueVisualAcuities.builder()
                  .binocular(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              getCorrection(config.binocular().withoutCorrectionId(), 1D))
                          .withCorrection(
                              getCorrection(config.binocular().withCorrectionId(), 1D)
                          )
                          .build()
                  )
                  .rightEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              getCorrection(config.rightEye().withoutCorrectionId(), 0.5D))
                          .withCorrection(
                              getCorrection(config.rightEye().withCorrectionId(), 0.8)
                          )
                          .build()
                  )
                  .leftEye(
                      VisualAcuity.builder()
                          .withoutCorrection(
                              getCorrection(config.leftEye().withoutCorrectionId(), 0.0D))
                          .withCorrection(
                              getCorrection(config.leftEye().withCorrectionId(), 0.1D)
                          )
                          .build()
                  )
                  .build()
          )
          .build();
    }

    return null;
  }

  private static Correction getCorrection(String id, Double value) {
    return Correction.builder()
        .id(new FieldId(id))
        .value(value)
        .build();
  }

  private static boolean getBoolean(ElementId elementId, TestabilityFillTypeDTO fillType) {
    if (fillType == MINIMAL) {
      return elementId == QUESTION_SYNFUNKTIONER_ID;
    }

    return elementId != QUESTION_SYNFUNKTIONER_ID;
  }


  private static Code getCode(ElementId elementId, TestabilityFillTypeDTO fillType) {
    if (elementId == QUESTION_IDENTITET_ID) {
      return IDK1;
    }

    if (elementId == QUESTION_BEDOMNING_ID) {
      if (fillType == MINIMAL) {
        return NO;
      }

      return JA;
    }

    if (elementId == QUESTION_BASERAT_PA_ID) {
      if (fillType == MINIMAL) {
        return JOURNALUPPGIFTER;
      }

      return DISTANSKONTAKT;
    }

    if (elementId == QUESTION_INTYGET_AVSER_ID) {
      if (fillType == MINIMAL) {
        return GR_II;
      }

      return TAXI;
    }

    if (elementId == QUESTION_STROKE_PAVERKAN_V1_ID) {
      return CodeSystemKvTs001.NEJ;
    }

    if (elementId == QUESTION_HORSELHJALPMEDEL_POSITION_V1_ID) {
      return CodeSystemKvAnatomiskLokalisationHorapparat.BADA_ORONEN;
    }

    if (elementId == QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID) {
      return CodeSystemKorrigeringAvSynskarpa.GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER;
    }

    throw new IllegalStateException(
        String.format("Code not defined in TS8071 fill service for question %s",
            elementId.id()));

  }
}
