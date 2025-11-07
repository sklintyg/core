package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryAnamnes.categoryAnamnes;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryBalanssinne.categoryBalanssinne;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryBedomning.categoryBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryDemensOchAndraKognitivaStorningar.categoryDemensOchAndraKognitivaStorningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryDiabetes.categoryDiabetes;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryEpilepsi.categoryEpilepsi;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryHjartOchKarlsjukdomar.categoryHjartOchKarlsjukdomar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryIdentitet.categoryIdentitet;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryIntygetAvser.categoryIntygetAvser;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryIntygetBaseratPa.categoryIntygetBaseratPa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryNeurologiskaSjukdomar.categoryNeurologiskaSjukdomar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryNjursjukdomar.categoryNjursjukdomar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryOvrigMedicinering.categoryOvrigMedicinering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryPsykiskaSjukdomarOchStorningar.categoryPsykiskaSjukdomarOchStorningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryRorelseorganensFunktioner.categoryRorelseorganensFunktioner;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategorySomnOchVakenhetsstorningar.categorySomnOchVakenhetsstorningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategorySynfunktion.categorySynfunktion;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategorySynskarpa.categorySynskarpa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionArytmi.questionArytmi;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionArytmiBeskrivning.questionArytmiBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBalanssinne.questionBalanssinne;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBaseratPa.questionBaseratPa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBaseratPaDatum.questionBaseratPaDatum;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBedomning.questionBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBedomningOkand.questionBedomningOkand;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBedomningRisk.questionBedomningRisk;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsi.questionEpilepsi;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiAnfall.questionEpilepsiAnfall;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiAnfallBeskrivning.questionEpilepsiAnfallBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiBeskrivning.questionEpilepsiBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiMedicin.questionEpilepsiMedicin;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiMedicinBeskrivning.questionEpilepsiMedicinBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdom.questionHjartsjukdom;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdomBehandlad.questionHjartsjukdomBehandlad;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionIdentitet.questionIdentitet;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionIntygetAvser.questionIntygetAvser;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedicinering.questionMedicinering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedicineringBeskrivning.questionMedicineringBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedvetandestorning.questionMedvetandestorning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedvetandestorningTidpunkt.questionMedvetandestorningTidpunkt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMissbrukProvtagning.questionMissbrukProvtagning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNjurfunktion.questionNjurfunktion;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNjurtransplatation.questionNjurtransplatation;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNjurtransplatationTidpunkt.questionNjurtransplatationTidpunkt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionOvrigBeskrivning.questionOvrigBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionPsykiskBeskrivning.questionPsykiskBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionRorlighet.questionRorlighet;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionRorlighetBeskrivning.questionRorlighetBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomEllerSynnedsattning.questionSjukdomEllerSynnedsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomshistorik.questionSjukdomshistorik;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSomnBehandling.questionSomnBehandling;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSomnBeskrivning.questionSomnBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynfunktioner.questionSynfunktioner;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynkopeBeskrivning.questionSynkopeBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.questionSynskarpa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.CategoryAdhdAutismPsykiskUtvecklingsstorningV1.categoryAdhdAutismPsykiskUtvecklingsstorningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.CategoryAlkoholNarkotikaOchLakemedelV1.categoryAlkoholNarkotikaOchLakemedelV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.CategoryHorselV1.categoryHorselV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.CategoryOvrigKommentarV1.categoryOvrigKommentarV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.MessageDiabetesV1.messageDiabetesV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionBalanssinneBeskrivningV1.questionBalanssinneBeskrivningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionDemensBeskrivningV1.questionDemensBeskrivningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionDemensV1.questionDemensV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionDiabetesV1.questionDiabetesV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionEpilepsiMedicinTidpunktV1.questionEpilepsiMedicinTidpunktV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionHjartsjukdomBehandladBeskrivningV1.questionHjartsjukdomBehandladBeskrivningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionHjartsjukdomBeskrivningV1.questionHjartsjukdomBeskrivningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionHorselV1.questionHorselV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionHorselhjalpmedelPositionV1.questionHorselhjalpmedelPositionV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionHorselhjalpmedelV1.questionHorselhjalpmedelV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKognitivStorningV1.questionKognitivStorningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1.questionKorrigeringAvSynskarpaIngenStyrkaOverV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKorrigeringAvSynskarpaKontaktlinserV1.questionKorrigeringAvSynskarpaKontaktlinserV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKorrigeringAvSynskarpaStyrkaOverV1.questionKorrigeringAvSynskarpaStyrkaOverV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionKorrigeringAvSynskarpaV1.questionKorrigeringAvSynskarpaV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionLakemedelBeskrivningV1.questionLakemedelBeskrivningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionLakemedelV1.questionLakemedelV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukBeskrivningV1.questionMissbrukBeskrivningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukJournaluppgifterBeskrivningV1.questionMissbrukJournaluppgifterBeskrivningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukJournaluppgifterV1.QUESTION_MISSBRUK_JOURNALUPPGIFTER_V1_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukJournaluppgifterV1.QUESTION_MISSBRUK_JOURNALUPPGIFTER_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukJournaluppgifterV1.questionMissbrukJournaluppgifterV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukV1.QUESTION_MISSBRUK_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukV1.questionMissbrukV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukVardBeskrivningV1.questionMissbrukVardBeskrivningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukVardV1.questionMissbrukVardV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeurologiskSjukdomBeskrivningV1.questionNeurologiskSjukdomBeskrivningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeurologiskSjukdomV1.questionNeurologiskSjukdomV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskLakemedelBeskrivningV1.questionNeuropsykiatriskLakemedelBeskrivningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskLakemedelV1.questionNeuropsykiatriskLakemedelV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskTidpunktV1.questionNeuropsykiatriskTidpunktV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskTrafikriskV1.questionNeuropsykiatriskTrafikriskV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskV1.questionNeuropsykiatriskV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionPsykiskTidpunktV1.questionPsykiskTidpunktV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionPsykiskUtvecklingsstorningAllvarligV1.questionPsykiskUtvecklingsstorningAllvarligV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionPsykiskUtvecklingsstorningV1.questionPsykiskUtvecklingsstorningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionPsykiskV1.QUESTION_PSYKISK_FIELD_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionPsykiskV1.QUESTION_PSYKISK_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionPsykiskV1.questionPsykiskV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSjukdomEllerSynnedsattningBeskrivningV1.questionSjukdomEllerSynnedsattningBeskrivningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSjukdomshistorikBeskrivningV1.questionSjukdomshistorikBeskrivningV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSomnV1.QUESTION_SOMN_V1_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSomnV1.QUESTION_SOMN_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSomnV1.questionSomnV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionStrokePaverkanV1.questionStrokePaverkanV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionStrokeV1.questionStrokeV1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSynkopeV1.QUESTION_SYNKOPE_FIELD_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSynkopeV1.QUESTION_SYNKOPE_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSynkopeV1.questionSynkopeV1;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateTypeName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.GeneralPdfSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygstyp;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryTS8071 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;
  private final CertificateActionConfigurationRepository certificateActionConfigurationRepository;

  @Value("${certificate.model.ts8071.v1_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetots.logicaladdress}")
  private String tsLogicalAddress;

  private static final String TS_8071 = "ts8071";
  private static final String VERSION = "1.0";
  private static final CertificateTypeName TS8071_TYPE_NAME = new CertificateTypeName("TS8071");
  private static final String NAME = "Läkarintyg för högre körkortsbehörigheter, taxiförarlegitimation och på begäran av Transportstyrelsen";
  private static final String DESCRIPTION = """
      Transportstyrelsens läkarintyg ska användas vid förlängd giltighet av högre behörighet från 45 år, ansökan om körkortstillstånd för grupp II och III och vid ansökan om taxiförarlegitimation. Transportstyrelsens läkarintyg kan även användas när Transportstyrelsen i annat fall begärt ett allmänt läkarintyg avseende lämplighet att inneha körkort.
      
      Specialistintyg finns bl.a. för alkohol, läkemedel, synfunktion, Alkolås m.m. Se <LINK:transportstyrelsenLink>.
      """;
  private static final String PDF_DESCRIPTION = """
      Transportstyrelsens läkarintyg ska användas vid förlängd giltighet av högre behörighet från 45 år, ansökan om körkortstillstånd för grupp II och III och vid ansökan om taxiförarlegitimation. Transportstyrelsens läkarintyg kan även användas när Transportstyrelsen i annat fall begärt ett allmänt läkarintyg avseende lämplighet att inneha körkort.
      
      Specialistintyg finns bl.a. för alkohol, läkemedel, synfunktion, Alkolås m.m. Se Transportstyrelsens hemsida.""";
  private static final String DETAILED_DESCRIPTION = """
       Intyg för körkort och taxiförarlegitimation ska avges med beaktande av vad som anges i Transportstyrelsens föreskrifter och allmänna råd (TSFS 2010:125) om medicinska krav för innehav av körkort m.m. (medicinföreskrifterna). Föreskrifterna finns på <LINK:transportstyrelsenLink> och där finns också kompletterande upplysningar till vissa av kapitlen. För närvarande finns kompletterande upplysningar till kapitel 1 om bland annat läkares anmälan, kapitel 2 om synfunktionerna, kapitel 6 om diabetes och kapitel 17 om medicinska intyg.
      
       På <LINK:transportstyrelsenLink> finns också blanketter för olika specialistläkarintyg, exempelvis för intyg om ADHD m.m., om hjärt- och kärlsjukdomar, om diabetes och om alkohol, narkotika och läkemedel. Om en person ska lämna något specialistläkarintyg har denne fått ett brev om saken från Transporstyrelsen. Det är därför ofta klokt att  be att få se brevet, så att intyget kommer att svara mot de frågeställningar som kan finnas hos myndigheten.
      
       <b className="iu-fw-heading">Har du frågor?</b>
       Kontakta avdelning Körkort på Transportstyrelsen, telefon 0771-81 81 81.
      """;
  private static final String PREAMBLE_TEXT =
      "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. "
          + "Har du frågor kontaktar du den som skrivit ditt intyg.";

  public static final CertificateModelId TS8071_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(TS_8071))
      .version(new CertificateVersion(VERSION))
      .build();

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(TS8071_V1_0)
        .type(CodeSystemKvIntygstyp.TS8071)
        .typeName(TS8071_TYPE_NAME)
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(true)
        .summaryProvider(new TS8071CertificateSummaryProvider())
        .citizenAvailableFunctionsProvider(
            new TS8071CitizenAvailableFunctionsProvider(certificateActionConfigurationRepository))
        .texts(
            List.of(
                CertificateText.builder()
                    .text(PREAMBLE_TEXT)
                    .type(CertificateTextType.PREAMBLE_TEXT)
                    .links(Collections.emptyList())
                    .build()
            )
        )
        .pdfSpecification(
            GeneralPdfSpecification.builder()
                .description(PDF_DESCRIPTION)
                .build()
        )
        .elementSpecifications(
            List.of(
                categoryIntygetAvser(
                    questionIntygetAvser()
                ),
                categoryIntygetBaseratPa(
                    questionBaseratPa(
                        questionBaseratPaDatum()
                    )
                ),
                categoryIdentitet(
                    questionIdentitet()
                ),
                categorySynfunktion(
                    questionSynfunktioner()
                ),
                categorySynskarpa(
                    questionSynskarpa(),
                    questionKorrigeringAvSynskarpaV1(
                        questionKorrigeringAvSynskarpaIngenStyrkaOverV1(),
                        questionKorrigeringAvSynskarpaStyrkaOverV1(),
                        questionKorrigeringAvSynskarpaKontaktlinserV1()
                    )
                ),
                categoryAnamnes(
                    questionSjukdomEllerSynnedsattning(
                        questionSjukdomEllerSynnedsattningBeskrivningV1()
                    ),
                    questionSjukdomshistorik(
                        questionSjukdomshistorikBeskrivningV1()
                    )
                ),
                categoryBalanssinne(
                    questionBalanssinne(
                        questionBalanssinneBeskrivningV1()
                    )
                ),
                categoryHorselV1(
                    questionHorselV1(),
                    questionHorselhjalpmedelV1(
                        questionHorselhjalpmedelPositionV1()
                    )
                ),
                categoryRorelseorganensFunktioner(
                    questionRorlighet(
                        questionRorlighetBeskrivning()
                    ),
                    questionRorlighetHjalpaPassagerare()
                ),
                categoryHjartOchKarlsjukdomar(
                    questionHjartsjukdom(
                        questionHjartsjukdomBeskrivningV1()
                    ),
                    questionHjartsjukdomBehandlad(
                        questionHjartsjukdomBehandladBeskrivningV1()
                    ),
                    questionArytmi(
                        questionArytmiBeskrivning()
                    ),
                    questionSynkopeV1(
                        questionSynkopeBeskrivning(QUESTION_SYNKOPE_V1_ID,
                            QUESTION_SYNKOPE_FIELD_V1_ID)
                    ),
                    questionStrokeV1(
                        questionStrokePaverkanV1()
                    )
                ),
                categoryDiabetes(
                    questionDiabetesV1(),
                    messageDiabetesV1()
                ),
                categoryNeurologiskaSjukdomar(
                    questionNeurologiskSjukdomV1(
                        questionNeurologiskSjukdomBeskrivningV1()
                    )
                ),
                categoryEpilepsi(
                    questionEpilepsi(
                        questionEpilepsiBeskrivning()
                    ),
                    questionEpilepsiAnfall(
                        questionEpilepsiAnfallBeskrivning()
                    ),
                    questionEpilepsiMedicin(
                        questionEpilepsiMedicinBeskrivning(),
                        questionEpilepsiMedicinTidpunktV1()
                    ),
                    questionMedvetandestorning(
                        questionMedvetandestorningTidpunkt()
                    )
                ),
                categoryNjursjukdomar(
                    questionNjurfunktion(),
                    questionNjurtransplatation(
                        questionNjurtransplatationTidpunkt()
                    )
                ),
                categoryDemensOchAndraKognitivaStorningar(
                    questionKognitivStorningV1(),
                    questionDemensV1(
                        questionDemensBeskrivningV1()
                    )
                ),
                categorySomnOchVakenhetsstorningar(
                    questionSomnV1(
                        questionSomnBeskrivning(QUESTION_SOMN_V1_ID, QUESTION_SOMN_V1_FIELD_ID)
                    ),
                    questionSomnBehandling(QUESTION_SOMN_V1_ID)
                ),
                categoryAlkoholNarkotikaOchLakemedelV1(
                    questionMissbrukV1(
                        questionMissbrukBeskrivningV1()
                    ),
                    questionMissbrukJournaluppgifterV1(
                        questionMissbrukJournaluppgifterBeskrivningV1(),
                        questionMissbrukProvtagning(QUESTION_MISSBRUK_JOURNALUPPGIFTER_V1_ID,
                            QUESTION_MISSBRUK_JOURNALUPPGIFTER_V1_FIELD_ID,
                            QUESTION_MISSBRUK_V1_ID)
                    ),
                    questionMissbrukVardV1(
                        questionMissbrukVardBeskrivningV1()
                    ),
                    questionLakemedelV1(
                        questionLakemedelBeskrivningV1()
                    )
                ),
                categoryPsykiskaSjukdomarOchStorningar(
                    questionPsykiskV1(
                        questionPsykiskBeskrivning(
                            QUESTION_PSYKISK_V1_ID,
                            QUESTION_PSYKISK_FIELD_V1_ID
                        ),
                        questionPsykiskTidpunktV1()
                    )
                ),
                categoryAdhdAutismPsykiskUtvecklingsstorningV1(
                    questionNeuropsykiatriskV1(
                        questionNeuropsykiatriskTrafikriskV1(),
                        questionNeuropsykiatriskTidpunktV1(),
                        questionNeuropsykiatriskLakemedelV1(
                            questionNeuropsykiatriskLakemedelBeskrivningV1()
                        )
                    ),
                    questionPsykiskUtvecklingsstorningV1(
                        questionPsykiskUtvecklingsstorningAllvarligV1()
                    )
                ),
                categoryOvrigMedicinering(
                    questionMedicinering(
                        questionMedicineringBeskrivning()
                    )
                ),
                categoryOvrigKommentarV1(
                    questionOvrigBeskrivning()
                ),
                categoryBedomning(
                    questionBedomning(
                        questionBedomningRisk(),
                        questionBedomningOkand()
                    )
                ),
                issuingUnitContactInfo()
            )
        )
        .recipient(CertificateRecipientFactory.transp(tsLogicalAddress))
        .certificateActionSpecifications(TS8071CertificateActionSpecification.create())
        .certificateActionFactory(certificateActionFactory)
        .build();
  }
}
