package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.CategoryArbetsformaga.categoryArbetsformaga;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.CategoryBedomning.categoryBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.CategoryDiagnos.categoryDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.CategoryGrundForMedicinsktUnderlag.categoryGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.CategoryKontakt.categoryKontakt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.CategoryOvrigt.categoryOvrigt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.CategorySysselsattning.categorySysselsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.MessageArbetsformagaAlert.messageArbetsformagaAlert;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionAngeVadAnnatAr.questionAngeVadAnnatAr;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionArbetsformaga.questionArbetsformaga;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionBeskrivArbetsformagan.questionBeskrivArbetsformagan;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionDiagnos.questionDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFinnsArbetsformaga.questionFinnsArbetsformaga;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionFormedlaDiagnos.questionFormedlaDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionKontakt.questionKontakt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionKontaktBeskrivning.questionKontaktBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionOvrigt.questionOvrigt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodBedomning.questionPeriodBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodProcentBedomning.questionPeriodProcentBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionSysselsattning.questionSysselsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;

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
public class CertificateModelFactoryAG114 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;

  @Value("${certificate.model.ag114.v2_0.active.from}")
  private LocalDateTime activeFrom;

  private final DiagnosisCodeRepository diagnosisCodeRepository;

  private static final String AG114 = "ag114";
  private static final CertificateTypeName AG114_TYPE_NAME = new CertificateTypeName("AG1-14");
  private static final String NAME = "Läkarintyg om arbetsförmåga – sjuklöneperioden";
  private static final String VERSION = "2.0";
  private static final String DESCRIPTION = """
      Läkarintyg om arbetsförmåga – sjuklöneperioden ska användas när patienten har en anställning och behöver ett läkarintyg i förhållande till sin arbetsgivare. Intyget används under sjuklöneperioden, det vill säga under de 14 första dagarna i sjukfallet.
      """;

  private static final String PREAMBLE_TEXT = """
      Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. Har du frågor kontaktar du den som skrivit ditt intyg. Det här intyget behöver du skriva ut och skicka själv.
      """;

  public static final CertificateModelId AG114_V2_0 = CertificateModelId.builder()
      .type(new CertificateType(AG114))
      .version(new CertificateVersion(VERSION))
      .build();

  private static final String DETAILED_DESCRIPTION = """
      <p><b>Utfärdare</b><br/>Läkarintyget får utfärdas av läkare eller tandläkare. I intyget omfattar begreppet läkare båda professionerna. Det är inte möjligt att delegera rätten att utfärda läkarintyget till annan profession, till exempel sjuksköterska eller fysioterapeut. Se även Socialstyrelsens föreskrifter om intyg (HSLF-FS 2018:54).</p>
      <p><b>Syften med läkarintyget</b></p>
      <ul>
        <li>Läkarintyget är ett beslutsunderlag för arbetsgivaren vid bedömning av om arbetstagaren har rätt till sjuklön under sjuklöneperioden, dag 1-14.</li>
        <li>Med stöd av läkarintyget styrker arbetstagaren dels sin rätt till att vara frånvarande från arbetet, dels vilka arbetsuppgifter hen inte bör utföra.</li>
        <li>Läkarintyget kan ge arbetsgivaren vägledning till om det går att anpassa arbetsuppgifter för att göra det möjligt för arbetstagaren att utföra visst arbete. Informationen kan även ha betydelse för arbetsgivaren i rehabiliteringsarbetet.</li>
        <li>Läkarintyget ska endast användas till och med dag 14 i sjuklöneperioden. Om sjukfallet bedöms passera dag 14 ska istället Läkarintyg om arbetsförmåga - arbetsgivaren utfärdas och lämnas till arbetsgivare.</li>
        <li>Läkarintyget kan användas från och med dag 1 i sjuklöneperioden, ett så kallat förstadagsintyg. Arbetsgivaren får om det finns särskilda skäl för det begära att arbetstagaren genom intyg av läkare styrker nedsättning av arbetsförmågan tidigare än dag 8 i sjuklöneperioden.</li>
      </ul>
      <p><b>Information i intyget</b></p>
      <p>Intyget ska ge följande information:</p>
      <ul>
        <li>På vilket sätt sjukdomen sätter ned arbetsförmågan och om kvarvarande arbetsförmåga finns.</li>
        <li>Om tillfällig anpassning av ordinarie arbetsuppgifter eller arbetstid kan stödja ökad arbetsförmåga.</li>
      </ul>
      <p><b>Sjuklöneperiod</b></p>
      <p>Sjuklöneperioden börjar den dag som arbetstagaren gjort en sjukanmälan till arbetsgivaren samt avhåller sig från arbete vid nedsatt arbetsförmåga på grund av sjukdom.</p>
      <p><b>Läkarintyg från dag 15</b></p>
      <p>Från och med dag 15 i sjukperioden beslutar Försäkringskassan om arbetstagaren har rätt till sjukpenning. För dessa fall används istället Försäkringskassans läkarintyg för sjukpenning (FK7804).</p>
      <p>Om patienten behöver ett läkarintyg i förhållande till sin arbetsgivare ska Läkarintyg om arbetsförmåga – arbetsgivaren (AG7804) utfärdas.</p>
      """;

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(AG114_V2_0)
        .type(CodeSystemKvIntygstyp.AG114)
        .typeName(AG114_TYPE_NAME)
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION.replaceAll("\\R", ""))
        .activeFrom(activeFrom)
        .availableForCitizen(true)
        .recipient(CertificateRecipientFactory.skr())
        .texts(List.of(
            CertificateText.builder()
                .text(PREAMBLE_TEXT)
                .type(CertificateTextType.PREAMBLE_TEXT)
                .build()
        ))
        .summaryProvider(new AG114CertificateSummaryProvider())
        .certificateActionSpecifications(AG114CertificateActionSpecification.create())
        .citizenAvailableFunctionsProvider(new AG114CitizenAvailableFunctionsProvider())
        .messageActionSpecifications(List.of())
        .elementSpecifications(List.of(
            categoryGrundForMedicinsktUnderlag(
                questionGrundForMedicinsktUnderlag(
                    questionAngeVadAnnatAr()
                )
            ),
            categorySysselsattning(
                questionSysselsattning()
            ),
            categoryDiagnos(
                questionFormedlaDiagnos(),
                questionDiagnos(diagnosisCodeRepository)
            ),
            categoryArbetsformaga(
                questionArbetsformaga(),
                questionFinnsArbetsformaga(
                    questionBeskrivArbetsformagan()
                )
            ),
            categoryBedomning(
                questionPeriodProcentBedomning(),
                questionPeriodBedomning(),
                messageArbetsformagaAlert()
            ),
            categoryOvrigt(
                questionOvrigt()
            ),
            categoryKontakt(
                questionKontakt(
                    questionKontaktBeskrivning()
                )
            ),
            issuingUnitContactInfo()
        ))
        .certificateActionFactory(certificateActionFactory)
        .sickLeaveProvider(new AG114SickLeaveProvider())
        .generalPrintProvider(new AG114CertificateGeneralPrintProvider())
        .build();
  }
}
