package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryAdhdAutismPsykiskUtvecklingsstorning.categoryAdhdAutismPsykiskUtvecklingsstorning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryAlkoholNarkotikaOchLakemedel.categoryAlkoholNarkotikaOchLakemedel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryAnamnes.categoryAnamnes;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryBalanssinne.categoryBalanssinne;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryBedomning.categoryBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryDemensOchAndraKognitivaStorningar.categoryDemensOchAndraKognitivaStorningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryDiabetes.categoryDiabetes;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryEpilepsi.categoryEpilepsi;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryHjartOchKarlsjukdomar.categoryHjartOchKarlsjukdomar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryHorsel.categoryHorsel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryIdentitet.categoryIdentitet;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryIntygetAvser.categoryIntygetAvser;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryIntygetBaseratPa.categoryIntygetBaseratPa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryNeurologiskaSjukdomar.categoryNeurologiskaSjukdomar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryNjursjukdomar.categoryNjursjukdomar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryOvrigKommentar.categoryOvrigKommentar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryOvrigMedicinering.categoryOvrigMedicinering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryPsykiskaSjukdomarOchStorningar.categoryPsykiskaSjukdomarOchStorningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategoryRorelseorganensFunktioner.categoryRorelseorganensFunktioner;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategorySomnOchVakenhetsstorningar.categorySomnOchVakenhetsstorningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategorySynfunktion.categorySynfunktion;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.CategorySynskarpa.categorySynskarpa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionArytmi.questionArytmi;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionArytmiBeskrivning.questionArytmiBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionBalanssinne.questionBalanssinne;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionBalanssinneBeskrivning.questionBalanssinneBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionBaseratPa.questionBaseratPa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionBaseratPaDatum.questionBaseratPaDatum;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionBedomning.questionBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionBedomningOkand.questionBedomningOkand;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionBedomningRisk.questionBedomningRisk;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionDemens.questionDemens;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionDemensBeskrivning.questionDemensBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionDiabetes.questionDiabetes;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionEpilepsi.questionEpilepsi;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionEpilepsiAnfall.questionEpilepsiAnfall;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionEpilepsiAnfallBeskrivning.questionEpilepsiAnfallBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionEpilepsiBeskrivning.questionEpilepsiBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionEpilepsiMedicin.questionEpilepsiMedicin;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionEpilepsiMedicinBeskrivning.questionEpilepsiMedicinBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionEpilepsiMedicinTidpunkt.questionEpilepsiMedicinTidpunkt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionHjartsjukdom.questionHjartsjukdom;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionHjartsjukdomBehandlad.questionHjartsjukdomBehandlad;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionHjartsjukdomBehandladBeskrivning.questionHjartsjukdomBehandladBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionHjartsjukdomBeskrivning.questionHjartsjukdomBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionHorsel.questionHorsel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionHorselhjalpmedel.questionHorselhjalpmedel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionHorselhjalpmedelPosition.questionHorselhjalpmedelPosition;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionIdentitet.questionIdentitet;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionIntygetAvser.questionIntygetAvser;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionKognitivStorning.questionKognitivStorning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionLakemedel.questionLakemedel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionLakemedelBeskrivning.questionLakemedelBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionMedicinering.questionMedicinering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionMedicineringBeskrivning.questionMedicineringBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionMedvetandestorning.questionMedvetandestorning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionMedvetandestorningTidpunkt.questionMedvetandestorningTidpunkt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionMissbruk.questionMissbruk;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionMissbrukBeskrivning.questionMissbrukBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionMissbrukJournaluppgifter.questionMissbrukJournaluppgifter;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionMissbrukJournaluppgifterBeskrivning.questionMissbrukJournaluppgifterBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionMissbrukProvtagning.questionMissbrukProvtagning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionMissbrukVard.questionMissbrukVard;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionMissbrukVardBeskrivning.questionMissbrukVardBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNeurologiskSjukdom.questionNeurologiskSjukdom;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNeurologiskSjukdomBeskrivning.questionNeurologiskSjukdomBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNeuropsykiatrisk.questionNeuropsykiatrisk;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNeuropsykiatriskLakemedel.questionNeuropsykiatriskLakemedel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNeuropsykiatriskLakemedelBeskrivning.questionNeuropsykiatriskLakemedelBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNeuropsykiatriskTidpunkt.questionNeuropsykiatriskTidpunkt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNeuropsykiatriskTrafikrisk.questionNeuropsykiatriskTrafikrisk;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNjurfunktion.questionNjurfunktion;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNjurtransplatation.questionNjurtransplatation;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNjurtransplatationTidpunkt.questionNjurtransplatationTidpunkt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionOvrigBeskrivning.questionOvrigBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionPsykisk.questionPsykisk;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionPsykiskBeskrivning.questionPsykiskBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionPsykiskTidpunkt.questionPsykiskTidpunkt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionPsykiskUtvecklingsstorning.questionPsykiskUtvecklingsstorning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionPsykiskUtvecklingsstorningAllvarlig.questionPsykiskUtvecklingsstorningAllvarlig;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionRorlighet.questionRorlighet;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionRorlighetBeskrivning.questionRorlighetBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSjukdomEllerSynnedsattning.questionSjukdomEllerSynnedsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSjukdomEllerSynnedsattningBeskrivning.questionSjukdomEllerSynnedsattningBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSjukdomshistorik.questionSjukdomshistorik;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSjukdomshistorikBeskrivning.questionSjukdomshistorikBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSomn.questionSomn;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSomnBehandling.questionSomnBehandling;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSomnBeskrivning.questionSomnBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionStroke.questionStroke;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionStrokePavarkan.questionStrokePavarkan;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSynfunktioner.questionSynfunktioner;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSynkope.questionSynkope;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionSynkopeBeskrivning.questionSynkopeBeskrivning;

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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryTSTRK8071 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;

  @Value("${certificate.model.tstrk8071.v1_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetots.logicaladdress}")
  private String tsLogicalAddress;

  private static final String TSTRK_8071 = "tstrk8071";
  private static final String VERSION = "1.0";
  private static final String NAME = "Läkarintyg avseende högre körkortsbehörigheter, taxiförarlegitimation och på begäran av Transportstyrelsen";
  private static final String DESCRIPTION = """
      Transportstyrelsens läkarintyg ska användas vid förlängd giltighet av högre behörighet från 45 år, ansökan om körkortstillstånd för grupp II och III och vid ansökan om taxiförarlegitimation. Transportstyrelsens läkarintyg kan även användas när Transportstyrelsen i annat fall begärt ett allmänt läkarintyg avseende lämplighet att inneha körkort.
      """;
  private static final String DETAILED_DESCRIPTION = """
       Intyg för körkort och taxiförarlegitimation ska avges med beaktande av vad som anges i Transportstyrelsens föreskrifter och allmänna råd (TSFS 2010:125) om medicinska krav för innehav av körkort m.m. (medicinföreskrifterna). Föreskrifterna finns på Transportstyrelsen external-link och där finns också kompletterande upplysningar till vissa av kapitlen. För närvarande finns kompletterande upplysningar till kapitel 1 om bland annat läkares anmälan, kapitel 2 om synfunktionerna, kapitel 6 om diabetes och kapitel 17 om medicinska intyg.
       På  Transportstyrelsen external-link finns också blanketter för olika specialistläkarintyg, exempelvis för intyg om ADHD m.m., om hjärt- och kärlsjukdomar, om diabetes och om alkohol, narkotika och läkemedel. Om en person ska lämna något specialistläkarintyg har denne fått ett brev om saken från Transporstyrelsen. Det är därför ofta klokt att  be att få se brevet, så att intyget kommer att svara mot de frågeställningar som kan finnas hos myndigheten.
      
       <b className="iu-fw-heading">Har du frågor?</b>
       Kontakta avdelning Körkort på Transportstyrelsen, telefon 0771-81 81 81.
      """;
  private static final String PREAMBLE_TEXT =
      "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. "
          + "Har du frågor kontaktar du den som skrivit ditt intyg.";

  public static final CertificateModelId TSTRK8071_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(TSTRK_8071))
      .version(new CertificateVersion(VERSION))
      .build();

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(TSTRK8071_V1_0)
        .type(
            new Code(
                TSTRK_8071.toUpperCase(),
                "b64ea353-e8f6-4832-b563-fc7d46f29548",
                NAME
            )
        )
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(true)
        .summaryProvider(new TSTRK8071CertificateSummaryProvider())
        .texts(
            List.of(
                CertificateText.builder()
                    .text(PREAMBLE_TEXT)
                    .type(CertificateTextType.PREAMBLE_TEXT)
                    .links(Collections.emptyList())
                    .build()
            )
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
                categorySynskarpa(),
                categoryAnamnes(
                    questionSjukdomEllerSynnedsattning(
                        questionSjukdomEllerSynnedsattningBeskrivning()
                    ),
                    questionSjukdomshistorik(
                        questionSjukdomshistorikBeskrivning()
                    )
                ),
                categoryBalanssinne(
                    questionBalanssinne(
                        questionBalanssinneBeskrivning()
                    )
                ),
                categoryHorsel(
                    questionHorsel(),
                    questionHorselhjalpmedel(
                        questionHorselhjalpmedelPosition()
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
                        questionHjartsjukdomBeskrivning()
                    ),
                    questionHjartsjukdomBehandlad(
                        questionHjartsjukdomBehandladBeskrivning()
                    ),
                    questionArytmi(
                        questionArytmiBeskrivning()
                    ),
                    questionSynkope(
                        questionSynkopeBeskrivning()
                    ),
                    questionStroke(
                        questionStrokePavarkan()
                    )
                ),
                categoryDiabetes(
                    questionDiabetes()
                ),
                categoryNeurologiskaSjukdomar(
                    questionNeurologiskSjukdom(
                        questionNeurologiskSjukdomBeskrivning()
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
                        questionEpilepsiMedicinTidpunkt()
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
                    questionKognitivStorning(),
                    questionDemens(
                        questionDemensBeskrivning()
                    )
                ),
                categorySomnOchVakenhetsstorningar(
                    questionSomn(
                        questionSomnBeskrivning()
                    ),
                    questionSomnBehandling()
                ),
                categoryAlkoholNarkotikaOchLakemedel(
                    questionMissbruk(
                        questionMissbrukBeskrivning()
                    ),
                    questionMissbrukJournaluppgifter(
                        questionMissbrukJournaluppgifterBeskrivning(),
                        questionMissbrukProvtagning()
                    ),
                    questionMissbrukVard(
                        questionMissbrukVardBeskrivning()
                    ),
                    questionLakemedel(
                        questionLakemedelBeskrivning()
                    )
                ),
                categoryPsykiskaSjukdomarOchStorningar(
                    questionPsykisk(
                        questionPsykiskBeskrivning(),
                        questionPsykiskTidpunkt()
                    )
                ),
                categoryAdhdAutismPsykiskUtvecklingsstorning(
                    questionNeuropsykiatrisk(
                        questionNeuropsykiatriskTrafikrisk(),
                        questionNeuropsykiatriskTidpunkt(),
                        questionNeuropsykiatriskLakemedel(
                            questionNeuropsykiatriskLakemedelBeskrivning()
                        )
                    ),
                    questionPsykiskUtvecklingsstorning(
                        questionPsykiskUtvecklingsstorningAllvarlig()
                    )
                ),
                categoryOvrigMedicinering(
                    questionMedicinering(
                        questionMedicineringBeskrivning()
                    )
                ),
                categoryOvrigKommentar(
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
        .certificateActionSpecifications(TSTRK8071CertificateActionSpecification.create())
        .certificateActionFactory(certificateActionFactory)
        .build();
  }
}
