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
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.CategoryAlkoholOchLakemedelV2.categoryAlkoholOchLakemedelV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.CategoryHorselV2.categoryHorselV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.CategoryIntellektuellFunktionsnedsattningV2.categoryIntellektuellFunktionsnedsattningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.CategoryOvrigKommentarV2.categoryOvrigKommentarV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.MessageDiabetesV2.messageDiabetesV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionBalanssinneBeskrivningV2.questionBalanssinneBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionBedomningRiskV2.questionBedomningRiskV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionDemensBeskrivningV2.questionDemensBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionDemensV2.questionDemensV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionDiabetesV2.questionDiabetesV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionEpilepsiMedicinTidpunktV2.questionEpilepsiMedicinTidpunktV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionGlasogonStyrkaV2.questionGlasogonStyrkaV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionHjartsjukdomBehandladBeskrivningV2.questionHjartsjukdomBehandladBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionHjartsjukdomBeskrivningV2.questionHjartsjukdomBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionHorselV2.questionHorselV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionHorselhjalpmedelPositionV2.questionHorselhjalpmedelPositionV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionHorselhjalpmedelV2.questionHorselhjalpmedelV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionIntellektuellFunktionsnedsattningBeskrivningV2.questionIntellektuellFunktionsnedsattningBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionIntellektuellFunktionsnedsattningV2.questionIntellektuellFunktionsnedsattningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionKognitivStorningV2.questionKognitivStorningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionLakemedelBeskrivningV2.questionLakemedelBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionLakemedelV2.questionLakemedelV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMedicineringBeskrivningV2.questionMedicineringBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukBeskrivningV2.questionMissbrukBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukJournaluppgifterBeskrivningV2.questionMissbrukJournaluppgifterBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukJournaluppgifterV2.QUESTION_MISSBRUK_JOURNALUPPGIFTER_V2_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukJournaluppgifterV2.QUESTION_MISSBRUK_JOURNALUPPGIFTER_V2_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukJournaluppgifterV2.questionMissbrukJournaluppgifterV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukRemissionV2.questionMissbrukRemissionV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukV2.QUESTION_MISSBRUK_V2_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukV2.questionMissbrukV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukVardBeskrivningV2.questionMissbrukVardBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukVardV2.questionMissbrukVardV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionNeurologiskSjukdomBeskrivningV2.questionNeurologiskSjukdomBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionNeurologiskSjukdomV2.questionNeurologiskSjukdomV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionPsykiskTidpunktV2.questionPsykiskTidpunktV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionPsykiskV2.QUESTION_PSYKISK_V2_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionPsykiskV2.QUESTION_PSYKISK_V2_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionPsykiskV2.questionPsykiskV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionSjukdomEllerSynnedsattningBeskrivningV2.questionSjukdomEllerSynnedsattningBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionSjukdomshistorikBeskrivningV2.questionSjukdomshistorikBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionSomnV2.QUESTION_SOMN_V2_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionSomnV2.QUESTION_SOMN_V2_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionSomnV2.questionSomnV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionStrokePaverkanV2.questionStrokePaverkanV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionStrokeV2.questionStrokeV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionSynkopeV2.QUESTION_SYNKOPE_V2_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionSynkopeV2.QUESTION_SYNKOPE_V2_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionSynkopeV2.questionSynkopeV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionToleransKorrektionV2.questionToleransKorrektionV2;

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
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygstyp;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryTS8071V2 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;

  @Value("${certificate.model.ts8071.v2_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetots.logicaladdress}")
  private String tsLogicalAddress;

  private static final String TS_8071 = "ts8071";
  private static final String VERSION = "2.0";
  private static final CertificateTypeName TS8071_TYPE_NAME = new CertificateTypeName("TS8071");
  private static final String NAME = "Läkarintyg för högre körkortsbehörigheter, taxiförarlegitimation och på begäran av Transportstyrelsen";
  private static final String DESCRIPTION = """
      Transportstyrelsens läkarintyg ska användas vid förlängd giltighet av högre behörighet från 45 år, ansökan om körkortstillstånd för grupp II och III och vid ansökan om taxiförarlegitimation. Transportstyrelsens läkarintyg kan även användas när Transportstyrelsen i annat fall begärt ett allmänt läkarintyg avseende lämplighet att inneha körkort.
      
      Specialistintyg finns bl.a. för alkohol, läkemedel, synfunktion, Alkolås m.m. Se <LINK:transportstyrelsenHemsidaLink>.
      """;
  private static final String PDF_DESCRIPTION = """
      Transportstyrelsens läkarintyg ska användas vid förlängd giltighet av högre behörighet från 45 år, ansökan om körkortstillstånd för grupp II och III och vid ansökan om taxiförarlegitimation. Transportstyrelsens läkarintyg kan även användas när Transportstyrelsen i annat fall begärt ett allmänt läkarintyg avseende lämplighet att inneha körkort.
      
      Specialistintyg finns bl.a. för alkohol, läkemedel, synfunktion, Alkolås m.m. Se Transportstyrelsens hemsida.""";
  private static final String DETAILED_DESCRIPTION = """      
       Intyg för körkort och taxiförarlegitimation ska avges med beaktande av vad som anges i Transportstyrelsens föreskrifter och allmänna råd (TSFS 2010:125) om medicinska krav för innehav av körkort m.m. (medicinföreskrifterna). Föreskrifterna finns på <LINK:transportstyrelsenHemsidaLink> och där finns också kompletterande upplysningar till vissa av kapitlen. För närvarande finns kompletterande upplysningar till kapitel 1 om bland annat läkares anmälan, kapitel 2 om synfunktionerna, kapitel 6 om diabetes och kapitel 17 om medicinska intyg.
      
       På <LINK:transportstyrelsenHemsidaLink> finns också blanketter för olika specialistläkarintyg, exempelvis för intyg om hjärt- och kärlsjukdomar, om diabetes och om alkohol, narkotika och läkemedel. Om en person ska lämna något specialistläkarintyg har denne fått ett brev om saken från Transporstyrelsen. Det är därför ofta klokt att  be att få se brevet, så att intyget kommer att svara mot de frågeställningar som kan finnas hos myndigheten.
      
       <b className="iu-fw-heading">Har du frågor?</b>
       Kontakta avdelning Körkort på Transportstyrelsen, telefon 0771-81 81 81.
      """;
  private static final String PREAMBLE_TEXT =
      "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. "
          + "Har du frågor kontaktar du den som skrivit ditt intyg.";

  public static final CertificateModelId TS8071_V2_0 = CertificateModelId.builder()
      .type(new CertificateType(TS_8071))
      .version(new CertificateVersion(VERSION))
      .build();

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(TS8071_V2_0)
        .type(CodeSystemKvIntygstyp.TS8071)
        .typeName(TS8071_TYPE_NAME)
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(true)
        .summaryProvider(new TS8071CertificateSummaryProvider())
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
                    questionToleransKorrektionV2(
                        questionGlasogonStyrkaV2()
                    )
                ),
                categoryAnamnes(
                    questionSjukdomEllerSynnedsattning(
                        questionSjukdomEllerSynnedsattningBeskrivningV2()
                    ),
                    questionSjukdomshistorik(
                        questionSjukdomshistorikBeskrivningV2()
                    )
                ),
                categoryBalanssinne(
                    questionBalanssinne(
                        questionBalanssinneBeskrivningV2()
                    )
                ),
                categoryHorselV2(
                    questionHorselV2(),
                    questionHorselhjalpmedelV2(
                        questionHorselhjalpmedelPositionV2()
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
                        questionHjartsjukdomBeskrivningV2()
                    ),
                    questionHjartsjukdomBehandlad(
                        questionHjartsjukdomBehandladBeskrivningV2()
                    ),
                    questionArytmi(
                        questionArytmiBeskrivning()
                    ),
                    questionSynkopeV2(
                        questionSynkopeBeskrivning(QUESTION_SYNKOPE_V2_ID,
                            QUESTION_SYNKOPE_V2_FIELD_ID)
                    ),
                    questionStrokeV2(
                        questionStrokePaverkanV2()
                    )
                ),
                categoryDiabetes(
                    questionDiabetesV2(),
                    messageDiabetesV2()
                ),
                categoryNeurologiskaSjukdomar(
                    questionNeurologiskSjukdomV2(
                        questionNeurologiskSjukdomBeskrivningV2()
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
                        questionEpilepsiMedicinTidpunktV2()
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
                    questionKognitivStorningV2(),
                    questionDemensV2(
                        questionDemensBeskrivningV2()
                    )
                ),
                categorySomnOchVakenhetsstorningar(
                    questionSomnV2(
                        questionSomnBeskrivning(QUESTION_SOMN_V2_ID, QUESTION_SOMN_V2_FIELD_ID)
                    ),
                    questionSomnBehandling(QUESTION_SOMN_V2_ID)
                ),
                categoryAlkoholOchLakemedelV2(
                    questionMissbrukV2(
                        questionMissbrukBeskrivningV2(),
                        questionMissbrukRemissionV2()
                    ),
                    questionMissbrukJournaluppgifterV2(
                        questionMissbrukJournaluppgifterBeskrivningV2(),
                        questionMissbrukProvtagning(QUESTION_MISSBRUK_JOURNALUPPGIFTER_V2_ID,
                            QUESTION_MISSBRUK_JOURNALUPPGIFTER_V2_FIELD_ID,
                            QUESTION_MISSBRUK_V2_ID)
                    ),
                    questionMissbrukVardV2(
                        questionMissbrukVardBeskrivningV2()
                    ),
                    questionLakemedelV2(
                        questionLakemedelBeskrivningV2()
                    )
                ),
                categoryPsykiskaSjukdomarOchStorningar(
                    questionPsykiskV2(
                        questionPsykiskBeskrivning(
                            QUESTION_PSYKISK_V2_ID,
                            QUESTION_PSYKISK_V2_FIELD_ID
                        ),
                        questionPsykiskTidpunktV2()
                    )
                ),
                categoryIntellektuellFunktionsnedsattningV2(
                    questionIntellektuellFunktionsnedsattningV2(
                        questionIntellektuellFunktionsnedsattningBeskrivningV2()
                    )
                ),
                categoryOvrigMedicinering(
                    questionMedicinering(
                        questionMedicineringBeskrivningV2()
                    )
                ),
                categoryOvrigKommentarV2(
                    questionOvrigBeskrivning()
                ),
                categoryBedomning(
                    questionBedomning(
                        questionBedomningRiskV2(),
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

