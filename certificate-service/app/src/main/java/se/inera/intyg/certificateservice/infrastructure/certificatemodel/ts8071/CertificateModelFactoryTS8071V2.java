package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryAlkoholNarkotikaOchLakemedel.categoryAlkoholNarkotikaOchLakemedel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryAnamnes.categoryAnamnes;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryBalanssinne.categoryBalanssinne;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryBedomning.categoryBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryDemensOchAndraKognitivaStorningar.categoryDemensOchAndraKognitivaStorningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryDiabetes.categoryDiabetes;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryEpilepsi.categoryEpilepsi;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryHjartOchKarlsjukdomar.categoryHjartOchKarlsjukdomar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryHorsel.categoryHorsel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryIdentitet.categoryIdentitet;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryIntygetAvser.categoryIntygetAvser;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryIntygetBaseratPa.categoryIntygetBaseratPa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryNeurologiskaSjukdomar.categoryNeurologiskaSjukdomar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryNjursjukdomar.categoryNjursjukdomar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryOvrigKommentar.categoryOvrigKommentar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryOvrigMedicinering.categoryOvrigMedicinering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryPsykiskaSjukdomarOchStorningar.categoryPsykiskaSjukdomarOchStorningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategoryRorelseorganensFunktioner.categoryRorelseorganensFunktioner;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategorySomnOchVakenhetsstorningar.categorySomnOchVakenhetsstorningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategorySynfunktion.categorySynfunktion;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.CategorySynskarpa.categorySynskarpa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.MessageDiabetes.messageDiabetes;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionArytmi.questionArytmi;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionArytmiBeskrivning.questionArytmiBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBalanssinne.questionBalanssinne;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBalanssinneBeskrivning.questionBalanssinneBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBaseratPa.questionBaseratPa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBaseratPaDatum.questionBaseratPaDatum;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBedomning.questionBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBedomningOkand.questionBedomningOkand;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBedomningRisk.questionBedomningRisk;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionDemens.questionDemens;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionDemensBeskrivning.questionDemensBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionDiabetes.questionDiabetes;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsi.questionEpilepsi;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiAnfall.questionEpilepsiAnfall;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiAnfallBeskrivning.questionEpilepsiAnfallBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiBeskrivning.questionEpilepsiBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiMedicin.questionEpilepsiMedicin;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionEpilepsiMedicinBeskrivning.questionEpilepsiMedicinBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdom.questionHjartsjukdom;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdomBehandlad.questionHjartsjukdomBehandlad;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHjartsjukdomBehandladBeskrivning.questionHjartsjukdomBehandladBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHorsel.questionHorsel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHorselhjalpmedel.questionHorselhjalpmedel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionHorselhjalpmedelPosition.questionHorselhjalpmedelPosition;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionIdentitet.questionIdentitet;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionKorrigeringAvSynskarpa.questionKorrigeringAvSynskarpa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionKorrigeringAvSynskarpaIngenStyrkaOver.questionKorrigeringAvSynskarpaIngenStyrkaOver;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionKorrigeringAvSynskarpaKontaktlinser.questionKorrigeringAvSynskarpaKontaktlinser;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionKorrigeringAvSynskarpaStyrkaOver.questionKorrigeringAvSynskarpaStyrkaOver;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionLakemedel.questionLakemedel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionLakemedelBeskrivning.questionLakemedelBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedicinering.questionMedicinering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedicineringBeskrivning.questionMedicineringBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedvetandestorning.questionMedvetandestorning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMedvetandestorningTidpunkt.questionMedvetandestorningTidpunkt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMissbrukJournaluppgifter.questionMissbrukJournaluppgifter;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMissbrukJournaluppgifterBeskrivning.questionMissbrukJournaluppgifterBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMissbrukProvtagning.questionMissbrukProvtagning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMissbrukVard.questionMissbrukVard;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMissbrukVardBeskrivning.questionMissbrukVardBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNeurologiskSjukdom.questionNeurologiskSjukdom;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNeurologiskSjukdomBeskrivning.questionNeurologiskSjukdomBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNjurfunktion.questionNjurfunktion;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNjurtransplatation.questionNjurtransplatation;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNjurtransplatationTidpunkt.questionNjurtransplatationTidpunkt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionOvrigBeskrivning.questionOvrigBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionPsykisk.questionPsykisk;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionPsykiskBeskrivning.questionPsykiskBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionPsykiskTidpunkt.questionPsykiskTidpunkt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionRorlighet.questionRorlighet;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionRorlighetBeskrivning.questionRorlighetBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionRorlighetHjalpaPassagerare.questionRorlighetHjalpaPassagerare;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomEllerSynnedsattning.questionSjukdomEllerSynnedsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomEllerSynnedsattningBeskrivning.questionSjukdomEllerSynnedsattningBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomshistorik.questionSjukdomshistorik;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomshistorikBeskrivning.questionSjukdomshistorikBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSomn.questionSomn;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSomnBehandling.questionSomnBehandling;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSomnBeskrivning.questionSomnBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionStroke.questionStroke;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionStrokePavarkan.questionStrokePavarkan;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynfunktioner.questionSynfunktioner;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynkopeBeskrivning.questionSynkopeBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.questionSynskarpa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionEpilepsiMedicinTidpunktV2.questionEpilepsiMedicinTidpunktV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionHjartsjukdomBeskrivningV2.questionHjartsjukdomBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionIntygetAvserV2.questionIntygetAvserV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionKognitivStorningV2.questionKognitivStorningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukBeskrivningV2.questionMissbrukBeskrivningV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukRemissionV2.questionMissbrukRemissionV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukV2.questionMissbrukV2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionSynkopeV2.questionSynkopeV2;

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
public class CertificateModelFactoryTS8071V2 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;

  @Value("${certificate.model.ts8071.v2_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetots.logicaladdress}")
  private String tsLogicalAddress;

  private static final String TS_8071 = "ts8071";
  private static final String VERSION = "2.0";
  private static final String NAME = "Läkarintyg för högre körkortsbehörigheter, taxiförarlegitimation och på begäran av Transportstyrelsen";
  private static final String DESCRIPTION = """
      Transportstyrelsens läkarintyg ska användas vid förlängd giltighet av högre behörighet från 45 år, ansökan om körkortstillstånd för grupp II och III och vid ansökan om taxiförarlegitimation. Transportstyrelsens läkarintyg kan även användas när Transportstyrelsen i annat fall begärt ett allmänt läkarintyg avseende lämplighet att inneha körkort.
      
      Specialistintyg finns bl.a. för alkohol, läkemedel, synfunktion, Alkolås m.m. Se <LINK:transportstyrelsenLink>.
      """;
  private static final String DETAILED_DESCRIPTION = """
       Intyg för körkort och taxiförarlegitimation ska avges med beaktande av vad som anges i Transportstyrelsens föreskrifter och allmänna råd (TSFS 2010:125) om medicinska krav för innehav av körkort m.m. (medicinföreskrifterna). Föreskrifterna finns på <LINK:transportstyrelsenLink> och där finns också kompletterande upplysningar till vissa av kapitlen. För närvarande finns kompletterande upplysningar till kapitel 1 om bland annat läkares anmälan, kapitel 2 om synfunktionerna, kapitel 6 om diabetes och kapitel 17 om medicinska intyg.
      
       På <LINK:transportstyrelsenLink> finns också blanketter för olika specialistläkarintyg, exempelvis för intyg om ADHD m.m., om hjärt- och kärlsjukdomar, om diabetes och om alkohol, narkotika och läkemedel. Om en person ska lämna något specialistläkarintyg har denne fått ett brev om saken från Transporstyrelsen. Det är därför ofta klokt att  be att få se brevet, så att intyget kommer att svara mot de frågeställningar som kan finnas hos myndigheten.
      
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
        .type(
            new Code(
                TS_8071.toUpperCase(),
                "b64ea353-e8f6-4832-b563-fc7d46f29548",
                NAME
            )
        )
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
        .elementSpecifications(
            List.of(
                categoryIntygetAvser(
                    questionIntygetAvserV2()
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
                    questionKorrigeringAvSynskarpa(
                        questionKorrigeringAvSynskarpaIngenStyrkaOver(),
                        questionKorrigeringAvSynskarpaStyrkaOver(),
                        questionKorrigeringAvSynskarpaKontaktlinser()
                    )
                ),
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
                        questionHjartsjukdomBeskrivningV2()
                    ),
                    questionHjartsjukdomBehandlad(
                        questionHjartsjukdomBehandladBeskrivning()
                    ),
                    questionArytmi(
                        questionArytmiBeskrivning()
                    ),
                    questionSynkopeV2(
                        questionSynkopeBeskrivning()
                    ),
                    questionStroke(
                        questionStrokePavarkan()
                    )
                ),
                categoryDiabetes(
                    questionDiabetes(),
                    messageDiabetes()
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
                    questionMissbrukV2(
                        questionMissbrukBeskrivningV2(),
                        questionMissbrukRemissionV2()
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
        .certificateActionSpecifications(TS8071CertificateActionSpecification.create())
        .certificateActionFactory(certificateActionFactory)
        .build();
  }
}

