package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.ts8071;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemDecision.NO;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKnowledge.YES;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIdKontroll.IDK1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0001.DISTANSKONTAKT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0001.JOURNALUPPGIFTER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.GR_II;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002.TAXI;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.CertificateModelFactoryTS8071.TS8071_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionArytmi.QUESTION_ARYTMI_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionArytmiBeskrivning.QUESTION_ARYTMI_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionBalanssinne.QUESTION_BALANSSINNE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionBalanssinneBeskrivning.QUESTION_BALANSSINNE_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionBaseratPa.QUESTION_BASERAT_PA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionBaseratPaDatum.QUESTION_BASERAT_PA_DATUM_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionBedomning.QUESTION_BEDOMNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionBedomningOkand.QUESTION_BEDOMNING_OKAND_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionBedomningRisk.QUESTION_BEDOMNING_RISK_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionDemens.QUESTION_DEMENS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionDemensBeskrivning.QUESTION_DEMENS_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionDiabetes.QUESTION_DIABETES_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionEpilepsi.QUESTION_EPILEPSI_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionEpilepsiAnfall.QUESTION_EPILEPSI_ANFALL_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionEpilepsiAnfallBeskrivning.QUESTION_EPILEPSI_ANFALL_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionEpilepsiBeskrivning.QUESTION_EPILEPSI_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionEpilepsiMedicin.QUESTION_EPILEPSI_MEDICIN_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionEpilepsiMedicinBeskrivning.QUESTION_EPILEPSI_MEDICIN_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionEpilepsiMedicinTidpunkt.QUESTION_EPILEPSI_MEDICIN_TIDPUNKT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHjartsjukdom.QUESTION_HJARTSJUKDOM_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHjartsjukdomBehandlad.QUESTION_HJARTSJUKDOM_BEHANDLAD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHjartsjukdomBehandladBeskrivning.QUESTION_HJARTSJUKDOM_BEHANDLAD_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHjartsjukdomBeskrivning.QUESTION_HJARTSJUKDOM_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHorsel.QUESTION_HORSEL_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHorselhjalpmedel.QUESTION_HORSELHJALPMEDEL_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHorselhjalpmedelPosition.QUESTION_HORSELHJALPMEDEL_POSITION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionIdentitet.QUESTION_IDENTITET_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionIntygetAvser.QUESTION_INTYGET_AVSER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionKognitivStorning.QUESTION_KOGNITIV_STORNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionLakemedel.QUESTION_LAKEMEDEL_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionLakemedelBeskrivning.QUESTION_LAKEMEDEL_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMedicinering.QUESTION_MEDICINERING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMedicineringBeskrivning.QUESTION_MEDICINERING_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMedvetandestorning.QUESTION_MEDVETANDESTORNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMedvetandestorningTidpunkt.QUESTION_MEDVETANDESTORNING_TIDPUNKT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMissbruk.QUESTION_MISSBRUK_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMissbrukBeskrivning.QUESTION_MISSBRUK_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMissbrukJournaluppgifter.QUESTION_MISSBRUK_JOURNALUPPGIFTER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMissbrukJournaluppgifterBeskrivning.QUESTION_MISSBRUK_JOURNALUPPGIFTER_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMissbrukProvtagning.QUESTION_MISSBRUK_PROVTAGNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMissbrukVard.QUESTION_MISSBRUK_VARD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMissbrukVardBeskrivning.QUESTION_MISSBRUK_VARD_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionNeurologiskSjukdom.QUESTION_NEUROLOGISK_SJUKDOM_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionNeuropsykiatrisk.QUESTION_NEUROPSYKIATRISK_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionNeuropsykiatriskLakemedel.QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionNeuropsykiatriskLakemedelBeskrivning.QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionNeuropsykiatriskTidpunkt.QUESTION_NEUROPSYKIATRISK_TIDPUNKT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionNeuropsykiatriskTrafikrisk.QUESTION_NEUROPSYKIATRISK_TRAFIKRISK_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionNjurfunktion.QUESTION_NJURFUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionNjurtransplatation.QUESTION_NJURTRANSPLATATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionNjurtransplatationTidpunkt.QUESTION_NJURTRANSPLATATION_TIDPUNKT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionOvrigBeskrivning.QUESTION_OVRIG_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionPsykisk.QUESTION_PSYKISK_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionPsykiskBeskrivning.QUESTION_PSYKISK_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionPsykiskTidpunkt.QUESTION_PSYKISK_TIDPUNKT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionPsykiskUtvecklingsstorning.QUESTION_PSYKISK_UTVECKLINGSSTORNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionPsykiskUtvecklingsstorningAllvarlig.QUESTION_PSYKISK_UTVECKLINGSSTORNING_ALLVARLIG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionRorlighet.QUESTION_RORLIGHET_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionRorlighetBeskrivning.QUESTION_RORLIGHET_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionRorlighetHjalpaPassagerare.QUESTION_RORLIGHET_HJALPA_PASSAGERARE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSjukdomEllerSynnedsattning.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSjukdomEllerSynnedsattningBeskrivning.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSjukdomshistorik.QUESTION_SJUKDOMSHISTORIK_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSjukdomshistorikBeskrivning.QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSomn.QUESTION_SOMN_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSomnBehandling.QUESTION_SOMN_BEHANDLING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSomnBeskrivning.QUESTION_SOMN_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionStroke.QUESTION_STROKE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionStrokePavarkan.QUESTION_STROKE_PAVARKAN_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSynfunktioner.QUESTION_SYNFUNKTIONER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSynkope.QUESTION_SYNKOPE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSynkopeBeskrivning.QUESTION_SYNKOPE_BESKRIVNING_ID;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.EMPTY;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.MAXIMAL;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.MINIMAL;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKnowledge;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemPositionHearingAid;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceTS8071 implements
    TestabilityCertificateFillService {

  private static final List<ElementId> MAXIMAL_IDS = List.of(
      QUESTION_INTYGET_AVSER_ID, QUESTION_BASERAT_PA_ID, QUESTION_BASERAT_PA_DATUM_ID,
      QUESTION_IDENTITET_ID, QUESTION_SYNFUNKTIONER_ID, QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID,
      QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_ID, QUESTION_SJUKDOMSHISTORIK_ID,
      QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_ID, QUESTION_BALANSSINNE_ID,
      QUESTION_BALANSSINNE_BESKRIVNING_ID, QUESTION_HORSEL_ID, QUESTION_HORSELHJALPMEDEL_ID,
      QUESTION_HORSELHJALPMEDEL_POSITION_ID, QUESTION_RORLIGHET_ID,
      QUESTION_RORLIGHET_BESKRIVNING_ID,
      QUESTION_RORLIGHET_HJALPA_PASSAGERARE_ID, QUESTION_HJARTSJUKDOM_ID,
      QUESTION_HJARTSJUKDOM_BESKRIVNING_ID,
      QUESTION_HJARTSJUKDOM_BEHANDLAD_ID, QUESTION_HJARTSJUKDOM_BEHANDLAD_BESKRIVNING_ID,
      QUESTION_ARYTMI_ID, QUESTION_ARYTMI_BESKRIVNING_ID, QUESTION_SYNKOPE_ID,
      QUESTION_SYNKOPE_BESKRIVNING_ID, QUESTION_STROKE_ID, QUESTION_STROKE_PAVARKAN_ID,
      QUESTION_DIABETES_ID, QUESTION_NEUROLOGISK_SJUKDOM_ID, QUESTION_EPILEPSI_ID,
      QUESTION_EPILEPSI_BESKRIVNING_ID, QUESTION_EPILEPSI_ANFALL_ID,
      QUESTION_EPILEPSI_ANFALL_BESKRIVNING_ID, QUESTION_EPILEPSI_MEDICIN_ID,
      QUESTION_EPILEPSI_MEDICIN_BESKRIVNING_ID, QUESTION_EPILEPSI_MEDICIN_TIDPUNKT_ID,
      QUESTION_MEDVETANDESTORNING_ID, QUESTION_MEDVETANDESTORNING_TIDPUNKT_ID,
      QUESTION_NJURFUNKTION_ID, QUESTION_NJURTRANSPLATATION_ID,
      QUESTION_NJURTRANSPLATATION_TIDPUNKT_ID, QUESTION_KOGNITIV_STORNING_ID, QUESTION_DEMENS_ID,
      QUESTION_DEMENS_BESKRIVNING_ID, QUESTION_SOMN_ID, QUESTION_SOMN_BESKRIVNING_ID,
      QUESTION_SOMN_BEHANDLING_ID, QUESTION_MISSBRUK_ID, QUESTION_MISSBRUK_BESKRIVNING_ID,
      QUESTION_MISSBRUK_JOURNALUPPGIFTER_ID, QUESTION_MISSBRUK_JOURNALUPPGIFTER_BESKRIVNING_ID,
      QUESTION_MISSBRUK_PROVTAGNING_ID, QUESTION_MISSBRUK_VARD_ID,
      QUESTION_MISSBRUK_VARD_BESKRIVNING_ID, QUESTION_LAKEMEDEL_ID,
      QUESTION_LAKEMEDEL_BESKRIVNING_ID, QUESTION_PSYKISK_ID, QUESTION_PSYKISK_BESKRIVNING_ID,
      QUESTION_PSYKISK_TIDPUNKT_ID, QUESTION_NEUROPSYKIATRISK_ID,
      QUESTION_NEUROPSYKIATRISK_TRAFIKRISK_ID, QUESTION_NEUROPSYKIATRISK_TIDPUNKT_ID,
      QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_ID, QUESTION_NEUROPSYKIATRISK_LAKEMEDEL_BESKRIVNING_ID,
      QUESTION_PSYKISK_UTVECKLINGSSTORNING_ID, QUESTION_PSYKISK_UTVECKLINGSSTORNING_ALLVARLIG_ID,
      QUESTION_MEDICINERING_ID, QUESTION_MEDICINERING_BESKRIVNING_ID, QUESTION_OVRIG_BESKRIVNING_ID,
      QUESTION_BEDOMNING_ID, QUESTION_BEDOMNING_RISK_ID, QUESTION_BEDOMNING_OKAND_ID
  );

  private static final List<ElementId> MINIMAL_IDS = List.of(
      QUESTION_BASERAT_PA_ID, QUESTION_INTYGET_AVSER_ID,
      QUESTION_IDENTITET_ID, QUESTION_SYNFUNKTIONER_ID, QUESTION_BALANSSINNE_ID, QUESTION_HORSEL_ID,
      QUESTION_HORSELHJALPMEDEL_ID, QUESTION_RORLIGHET_ID,
      QUESTION_RORLIGHET_HJALPA_PASSAGERARE_ID, QUESTION_HJARTSJUKDOM_ID, QUESTION_DIABETES_ID,
      QUESTION_NEUROLOGISK_SJUKDOM_ID,
      QUESTION_EPILEPSI_ID, QUESTION_EPILEPSI_ANFALL_ID,
      QUESTION_MEDVETANDESTORNING_ID,
      QUESTION_NJURFUNKTION_ID, QUESTION_NJURTRANSPLATATION_ID,
      QUESTION_KOGNITIV_STORNING_ID, QUESTION_DEMENS_ID,
      QUESTION_SOMN_ID, QUESTION_SOMN_BEHANDLING_ID, QUESTION_MISSBRUK_ID,
      QUESTION_MISSBRUK_JOURNALUPPGIFTER_ID, QUESTION_MISSBRUK_VARD_ID,
      QUESTION_LAKEMEDEL_ID, QUESTION_PSYKISK_ID, QUESTION_NEUROPSYKIATRISK_ID,
      QUESTION_PSYKISK_UTVECKLINGSSTORNING_ID,
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
      return ElementData.builder()
          .id(elementSpecification.id())
          .value(elementValueText.withText(
              String.format(
                  "Ett exempel på text för frågan %s",
                  elementSpecification.configuration().name()
              ).substring(0, 49)))
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

    return null;
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

      return YES;
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

    if (elementId == QUESTION_STROKE_PAVARKAN_ID) {
      return CodeSystemKnowledge.NO;
    }

    if (elementId == QUESTION_HORSELHJALPMEDEL_POSITION_ID) {
      return CodeSystemPositionHearingAid.BADA_ORONEN;
    }

    throw new IllegalStateException(
        String.format("Code not defined in TS8071 fill service for question %s", elementId.id()));

  }
}
