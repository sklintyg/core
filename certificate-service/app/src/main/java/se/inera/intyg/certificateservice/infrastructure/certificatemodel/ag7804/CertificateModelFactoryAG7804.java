package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.CategoryAktivitetsbegransning.categoryAktivitetsbegransning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.CategoryAtgarderSomKanFramjaAtergang.categoryAtgarderSomKanFramjaAttergang;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.CategoryBedomning.categoryBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.CategoryDiagnos.categoryDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.CategoryFunktionsnedsattning.categoryFunktionsnedsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.CategoryGrundForMedicinsktUnderlag.categoryGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.CategoryKontakt.categoryKontakt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.CategoryMedicinskBehandling.categoryMedicinskBehandling;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.CategoryOvrigt.categoryOvrigt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.CategoryPrognos.categoryPrognos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.CategorySmittbararpenning.categorySmittbararpenning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.CategorySysselsattning.categorySysselsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.MessageNedsattningArbetsformagaStartDateInfo.messageStartDateInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionAktivitetsbegransningar.questionAktivitetsbegransningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionAngeVarforDuVillHaKontakt.questionAngeVarforDuVillHaKontakt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionAnnanGrundForMedicinsktUnderlag.questionAnnanGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionAntalManader.questionAntalManader;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionArbetsformagaLangreAnBeslutsstod.questionArbetsformagaLangreAnBeslutsstod;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionAtgarderSomKanFramjaAtergang.questionAtgarderSomKanFramjaAtergang;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionDiagnos.questionDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFormedlaInfoOmDiagnosTillAG.questionFormedlaInfoOmDiagnosTillAG;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionFunktionsnedsattningar.questionFunktionsnedsattningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionGrundForBedomning.questionGrundForBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionKontakt.questionKontakt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionMedicinskBehandling.questionMedicinskBehandling;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionMedicinskaSkalForSvarareAtergang.questionMedicinskaSkalForSvarareAtergang;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionNedsattningArbetsformaga.questionNedsattningArbetsformaga;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionOvrigt.questionOvrigt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionPrognos.questionPrognos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.questionSmittbararpenning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSvarareAtergangVidOjamnArbetstid.questionSvarareAtergangVidOjamnArbetstid;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSysselsattning.questionSysselsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionTransportstod.questionTransportstod;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionYrkeOchArbetsuppgifter.questionYrkeOchArbetsuppgifter;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.CertificateModelFactoryFK7804.FK7804_V2_0;

import java.time.LocalDateTime;
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
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygstyp;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryAG7804 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;

  @Value("${certificate.model.ag7804.v2_0.active.from}")
  private LocalDateTime activeFrom;

  private final DiagnosisCodeRepository diagnosisCodeRepository;

  private static final String AG_7804 = "ag7804";
  private static final String VERSION = "2.0";
  private static final CertificateTypeName AG7804_TYPE_NAME = new CertificateTypeName("AG7804");
  private static final String NAME = "Läkarintyg om arbetsförmåga – arbetsgivaren";
  private static final String DESCRIPTION = """
      Läkarintyg om arbetsförmåga – arbetsgivaren skapas från Försäkringskassans läkarintyg för sjukpenning (FK 7804) och används i följande situationer:
      
      <ul><li>När patienten har en anställning och behöver ett läkarintyg i förhållande till sin arbetsgivare.</li><li>När patienten varit frånvarande från arbetet i fler än 14 dagar.</li><li>När intygsutfärdande läkaren bedömer att patienten behöver vara sjukfrånvarande längre tid än 14 dagar och att sjukfrånvaron därmed kommer att överstiga sjuklöneperioden. <em>Läkarintyg om arbetsförmåga – arbetsgivaren</em> kan utfärdas för att undvika ett återbesök efter dag 14 och fyller då funktionen som intyg till arbetsgivare både <em>under</em> sjuklöneperioden och <em>efter</em> sjuklöneperioden.</li></ul>
      """;

  private static final String DETAILED_DESCRIPTION = """
      <p><b>Utfärdare</b><br/>Läkarintyget får utfärdas av läkare eller tandläkare. I intyget omfattar begreppet läkare båda professionerna. Det är inte möjligt att delegera rätten att utfärda läkarintyget till annan profession, till exempel sjuksköterska eller fysioterapeut. Se även Socialstyrelsens föreskrifter om intyg (HSLF-FS 2018:54).</p>
      <p><b>Syften med läkarintyget</b></p>
      <ul>
        <li><b>Rätt till frånvaro.</b> Att ge arbetstagaren rätt att vara frånvarande från arbetet på grund av sjukdom/skada. Arbetstagaren är skyldig att vid sjukdom/skada lämna in ett medicinskt underlag som styrker dels att frånvaron beror på sjukdom, dels att sjukdomen sätter ned arbetsförmågan i arbetstagarens arbetsuppgifter. Om läkarintyget inte ger tillräcklig information kan frånvaron anses vara olovlig och medföra att intyget behöver kompletteras.</li>
        <li><b>Arbetsgivarens rehabiliteringsansvar.</b> Läkarintyget ska ge arbetsgivaren tillräcklig information om arbetstagarens medicinska hinder för att utföra sina ordinarie arbetsuppgifter. Arbetsgivare har enligt socialförsäkringsbalken, arbetsmiljölagen och arbetsdomstolens rättspraxis ett omfattande och långtgående rehabiliteringsansvar och ska genom skäliga stöd och anpassningar på arbetsplatsen underlätta för sjuka eller skadade att komma tillbaka i arbete.</li>
        <li><b>Plan för återgång i arbete.</b> Sedan 2018 har arbetsgivare en skyldighet enligt 30 kap. 6 § socialförsäkringsbalken att ta fram en plan för återgång i arbete när en arbetstagare förväntas vara sjuk under en längre tid. Planen ska kontinuerligt följas upp och revideras vid behov. Planen är främst ett stöd för rehabiliteringsarbetet på arbetsplatsen och ska underlätta en tidig återgång i arbete. Läkarintyget ger grundläggande medicinsk information om patientens möjligheter att återgå i arbete och är en förutsättning för att arbetsgivaren tillsammans med sin arbetstagare ska kunna upprätta en plan för återgång i arbete.</li>
        <li><b>Sjukpenningtillägg.</b> Att ge arbetstagaren rätt till kompletterande sjukpenningtillägg. De flesta kollektivavtal på svensk arbetsmarknad ger arbetstagare rätt till sjukpenningtillägg vid sjukdom.</li>
        <li><b>Läkarintyg från och med dag 1 i sjuklöneperioden</b> Läkarintyget kan användas från och med dag 1 i sjuklöneperioden, ett så kallat förstadagsintyg. Arbetsgivaren får om det finns särskilda skäl för det begära att arbetstagaren genom intyg av läkare styrker nedsättning av arbetsförmågan tidigare än dag 8 i sjuklöneperioden.</li>
      </ul>
      <p><b>Innehåll i läkarintyget</b></p>
      <p>Läkarintyg om arbetsförmåga - arbetsgivaren skapas från Försäkringskassans läkarintyg för sjukpenning (FK 7804) och ska innehålla motsvarande information.  Patienten kan dock välja om hen vill delge arbetsgivaren information om vilken eller vilka diagnoser som föreligger.  Övrig information i intyget är obligatorisk.</p>
      <p><b>När ska Läkarintyg om arbetsförmåga – arbetsgivaren användas?</b></p>
      <ul>
        <li>När patienten har en anställning och behöver ett läkarintyg i förhållande till sin arbetsgivare.</li>
        <li>När patienten varit frånvarande från arbetet i fler än 14 dagar.</li>
        <li>När intygsutfärdande läkaren bedömer att patienten behöver vara sjukfrånvarande längre tid än 14 dagar och att sjukfrånvaron därmed kommer att överstiga sjuklöneperioden. Läkarintyg om arbetsförmåga – arbetsgivaren kan utfärdas för att undvika ett återbesök efter dag 14 och fyller då funktionen som intyg till arbetsgivare både under sjuklöneperioden och efter sjuklöneperioden.</li>
      </ul>
      """;

  private static final String PREAMBLE_TEXT = """
      Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. Har du frågor kontaktar du den som skrivit ditt intyg. Det här intyget behöver du skriva ut och skicka själv.
      """;

  public static final CertificateModelId AG7804_V2_0 = CertificateModelId.builder()
      .type(new CertificateType(AG_7804))
      .version(new CertificateVersion(VERSION))
      .build();

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(AG7804_V2_0)
        .type(CodeSystemKvIntygstyp.AG7804)
        .typeName(AG7804_TYPE_NAME)
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION.replaceAll("\\R", ""))
        .activeFrom(activeFrom)
        .availableForCitizen(true)
        .texts(List.of(
            CertificateText.builder()
                .text(PREAMBLE_TEXT)
                .type(CertificateTextType.PREAMBLE_TEXT)
                .build()
        ))
        .ableToCreateDraftForModel(FK7804_V2_0)
        .summaryProvider(new AG7804CertificateSummaryProvider())
        .citizenAvailableFunctionsProvider(new AG7804CitizenAvailableFunctionsProvider())
        .certificateActionSpecifications(AG7804CertificateActionSpecification.create())
        .messageActionSpecifications(List.of())
        .elementSpecifications(List.of(
            categorySmittbararpenning(
                questionSmittbararpenning()
            ),
            categoryGrundForMedicinsktUnderlag(
                questionGrundForMedicinsktUnderlag(
                    questionAnnanGrundForMedicinsktUnderlag()
                )
            ),
            categorySysselsattning(
                questionSysselsattning(
                    questionYrkeOchArbetsuppgifter()
                )
            ),
            categoryDiagnos(
                questionFormedlaInfoOmDiagnosTillAG(),
                questionDiagnos(diagnosisCodeRepository)
            ),
            categoryFunktionsnedsattning(
                questionFunktionsnedsattningar()
            ),
            categoryAktivitetsbegransning(
                questionAktivitetsbegransningar()
            ),
            categoryMedicinskBehandling(
                questionMedicinskBehandling()
            ),
            categoryBedomning(
                questionNedsattningArbetsformaga(
                    messageStartDateInfo()
                ),
                questionArbetsformagaLangreAnBeslutsstod(),
                questionTransportstod(),
                questionSvarareAtergangVidOjamnArbetstid(
                    questionMedicinskaSkalForSvarareAtergang()
                )
            ),
            categoryPrognos(
                questionPrognos(
                    questionAntalManader(),
                    questionGrundForBedomning()
                )
            ),
            categoryAtgarderSomKanFramjaAttergang(
                questionAtgarderSomKanFramjaAtergang()
            ),
            categoryOvrigt(
                questionOvrigt()
            ),
            categoryKontakt(
                questionKontakt(
                    questionAngeVarforDuVillHaKontakt()
                )
            ),
            issuingUnitContactInfo()
        ))
        .certificateActionFactory(certificateActionFactory)
        .recipient(CertificateRecipientFactory.skr())
        .sickLeaveProvider(new AG7804SickLeaveProvider())
        .generalPrintProvider(new AG7804CertificateGeneralPrintProvider())
        .build();
  }
}
