package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.CertificateModelFactoryAG7804.AG7804_V2_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryAktivitetsbegransning.categoryAktivitetsbegransning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryAtgarderSomKanFramjaAttergang.categoryAtgarderSomKanFramjaAttergang;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryBedomning.categoryBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryDiagnos.categoryDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryFunktionsnedsattning.categoryFunktionsnedsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryGrundForMedicinsktUnderlag.categoryGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryKontakt.categoryKontakt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryMedicinskBehandling.categoryMedicinskBehandling;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryOvrigt.categoryOvrigt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryPrognos.categoryPrognos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategorySmittbararpenning.categorySmittbararpenning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategorySysselsattning.categorySysselsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.MessageNedsattningArbetsformagaStartDateInfo.messageStartDateInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAktivitetsbegransningar.questionAktivitetsbegransningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAngeVarforDuVillHaKontakt.questionAngeVarforDuVillHaKontakt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAnnanGrundForMedicinsktUnderlag.questionAnnanGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAntalManader.questionAntalManader;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionArbetsformagaLangreAnBeslutsstod.questionArbetsformagaLangreAnBeslutsstod;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAtgarderSomKanFramjaAtergang.questionAtgarderSomKanFramjaAtergang;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionDiagnos.questionDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionFunktionsnedsattningar.questionFunktionsnedsattningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionGrundForBedomning.questionGrundForBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionKontakt.questionKontakt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionMedicinskBehandling.questionMedicinskBehandling;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionMedicinskaSkalForSvarareAtergang.questionMedicinskaSkalForSvarareAtergang;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionNedsattningArbetsformaga.questionNedsattningArbetsformaga;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionOvrigt.questionOvrigt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionPrognos.questionPrognos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.questionSmittbararpenning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSvarareAtergangVidOjamnArbetstid.questionSvarareAtergangVidOjamnArbetstid;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSysselsattning.questionSysselsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionTransportstod.questionTransportstod;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionYrkeOchArbetsuppgifter.questionYrkeOchArbetsuppgifter;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateTypeName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.CertificateLink;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygstyp;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryFK7804 implements CertificateModelFactory {

  private static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk7804/schematron/lisjp.v2.sch");
  private final CertificateActionFactory certificateActionFactory;

  @Value("${certificate.model.fk7804.v2_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetofk.logicaladdress}")
  private String fkLogicalAddress;

  private final DiagnosisCodeRepository diagnosisCodeRepository;

  private static final String FK_7804 = "fk7804";
  private static final String VERSION = "2.0";
  private static final CertificateTypeName FK7804_TYPE_NAME = new CertificateTypeName("FK7804");
  private static final String NAME = "Läkarintyg för sjukpenning";
  private static final String DESCRIPTION = """
      <b>Vad är sjukpenning?</b>
      Sjukpenning är en ersättning för personer som arbetar i Sverige och har en nedsatt arbetsförmåga på grund av sjukdom. Beroende på hur mycket arbetsförmågan är nedsatt kan man få en fjärdedels, halv, tre fjärdedels eller hel sjukpenning.\s
      
      <b>Andra förmåner som detta läkarintyg används till</b>
      Om du stänger av patienten enligt smittskyddslagen ska du även använda detta intyg.
      """;

  private static final String DETAILED_DESCRIPTION = """
      <p>Om du inte känner patienten ska hen styrka sin identitet genom legitimation med foto (HSLF-FS 2018:54).</p>
      <p><b>Vad är sjukpenning?</b><br/>Sjukpenning är en ersättning för personer som har en nedsatt arbetsförmåga på grund av sjukdom. Beroende på hur mycket arbetsförmågan är nedsatt kan man få en fjärdedels, halv, tre fjärdedels eller hel sjukpenning.</p>
      <p><b>Förutsättningar för att få sjukpenning</b><br/>Arbetsförmågan bedöms i förhållande till personens sysselsättning som kan vara nuvarande arbete eller föräldraledighet för vård av barn. För personer som är arbetslösa bedöms arbetsförmågan i förhållandet till att utföra sådant arbete som är normalt förekommande på arbetsmarknaden.</p>
      <p>För att få sjukpenning ska man, förutom att ha nedsatt arbetsförmåga, tillhöra svensk socialförsäkring och ha inkomst från arbete. Försäkringskassan beslutar om och hur mycket sjukpenning personen kan få.</p>
      <p>Från den åttonde dagen i en sjukperiod måste det finnas ett läkarintyg. Läkarintyget ska tydligt beskriva hur patientens sjukdom påverkar patientens förmåga att utföra sin sysselsättning.</p>
      <p><b>Studerande</b><br/>En person kan få behålla sitt studiemedel om förmågan att studera är helt eller halvt nedsatt på grund av sjukdom. Försäkringskassan bedömer om förmågan är nedsatt och om studieperioden ska godkännas till CSN.</p>
      <p>Den som ansöker om att få behålla sitt studiemedel ska från och med den femtonde dagen i sjukperioden styrka sin nedsatta studieförmåga med ett läkarintyg.</p>
      <p><b>Smittbärarpenning</b><br/>En person kan få ersättning om hen måste avstå från sin sysselsättning på grund av läkarens beslut om avstängning enligt smittskyddslagen eller läkarundersökning alternativt hälsokontroll som syftar till att klarlägga sjukdom, smitta, sår eller annan skada som gör att hen inte får hantera livsmedel.</p>
      <p>Den som ansöker om smittbärarpenning ska skicka med ett läkarintyg.</p>
      <p><b>Mer om hur Försäkringskassan bedömer arbetsförmågan</b><br/>Försäkringskassan bedömer arbetsförmågan enligt rehabiliteringskedjan, som innebär följande:</p>
      <ul>
        <li>Under de första 90 dagarna som personen är sjukskriven kan Försäkringskassan betala sjukpenning om personen inte kan utföra sitt vanliga arbete eller ett annat tillfälligt arbete hos sin arbetsgivare.</li>
        <li>Efter 90 dagar kan Försäkringskassan betala sjukpenning om personen inte kan utföra något arbete alls hos sin arbetsgivare.</li>
        <li>Efter 180 dagar kan Försäkringskassan betala ut sjukpenning om personen inte kan utföra sådant arbete som är normalt förekommande på arbetsmarknaden. Men detta gäller inte om Försäkringskassan bedömer att personen med stor sannolikhet kommer att kunna gå tillbaka till ett arbete hos sin arbetsgivare innan dag 366. I dessa fall bedöms arbetsförmågan i förhållande till ett arbete hos arbetsgivaren även efter dag 180. Regeln gäller inte heller om det kan anses oskäligt att bedöma personens arbetsförmåga i förhållande till arbete som är normalt förekommande på arbetsmarknaden.</li>
        <li>Efter 365 dagar kan Försäkringskassan betala ut sjukpenning om personen inte kan utföra sådant arbete som är normalt förekommande på arbetsmarknaden. Undantag från detta kan göras om det kan anses oskäligt att bedöma personens arbetsförmåga i förhållande till sådant arbete som normalt förekommer på arbetsmarknaden.</li>
      </ul>
      <p>Rehabiliteringskedjan gäller fullt ut bara för den som har en anställning.</p>
      <p>Egna företagares arbetsförmåga bedöms i förhållande till de vanliga arbetsuppgifterna fram till och med dag 180. Sedan bedöms arbetsförmågan i förhållande till sådant arbete som normalt förekommer på arbetsmarknaden.</p>
      <p>För arbetslösa bedöms arbetsförmågan i förhållande till arbeten som normalt förekommer på arbetsmarknaden redan från första dagen i sjukperioden.</p>
      """;

  public static final String LINK_FK_ID = "LINK_FK";
  private static final String PREAMBLE_TEXT = """
      Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. Har du frågor kontaktar du den som skrivit ditt intyg. Om du vill ansöka om sjukpenning, gör du det på {LINK_FK}.
      """;
  public static final String URL_FK = "https://www.forsakringskassan.se/";
  public static final String FK_NAME = "Försäkringskassan";

  public static final CertificateModelId FK7804_V2_0 = CertificateModelId.builder()
      .type(new CertificateType(FK_7804))
      .version(new CertificateVersion(VERSION))
      .build();

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK7804_V2_0)
        .type(CodeSystemKvIntygstyp.FK7804)
        .typeName(FK7804_TYPE_NAME)
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION.replaceAll("\\R", ""))
        .activeFrom(activeFrom)
        .availableForCitizen(true)
        .recipient(CertificateRecipientFactory.fkassa(fkLogicalAddress))
        .schematronPath(SCHEMATRON_PATH)
        .sickLeaveProvider(new FK7804SickLeaveProvider())
        .ableToCreateDraftForModel(AG7804_V2_0)
        .messageTypes(List.of(
            CertificateMessageType.builder()
                .type(MessageType.MISSING)
                .subject(new Subject(MessageType.MISSING.displayName()))
                .build(),
            CertificateMessageType.builder()
                .type(MessageType.COORDINATION)
                .subject(new Subject(MessageType.COORDINATION.displayName()))
                .build(),
            CertificateMessageType.builder()
                .type(MessageType.CONTACT)
                .subject(new Subject(MessageType.CONTACT.displayName()))
                .build(),
            CertificateMessageType.builder()
                .type(MessageType.OTHER)
                .subject(new Subject(MessageType.OTHER.displayName()))
                .build()
        ))
        .texts(
            List.of(
                CertificateText.builder()
                    .text(PREAMBLE_TEXT)
                    .type(CertificateTextType.PREAMBLE_TEXT)
                    .links(List.of(CertificateLink.builder()
                        .url(URL_FK)
                        .id(LINK_FK_ID)
                        .name(FK_NAME)
                        .build()))
                    .build()
            )
        )
        .summaryProvider(new FK7804CertificateSummaryProvider())
        .certificateActionSpecifications(FK7804CertificateActionSpecification.create())
        .messageActionSpecifications(FK7804MessageActionSpecification.create())
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
        .pdfSpecification(FK7804PdfSpecification.create())
        .certificateActionFactory(certificateActionFactory)
        .build();
  }
}