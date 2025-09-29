package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elemets.CategorySmittbararpenning.categorySmittbararpenning;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryAG7804 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;

  @Value("${certificate.model.ag7804.v2_0.active.from}")
  private LocalDateTime activeFrom;

  private final DiagnosisCodeRepository diagnosisCodeRepository;

  private static final String AG_7804 = "ag7804";
  private static final String VERSION = "2.0";
  private static final String NAME = "Läkarintyg om arbetsförmåga – arbetsgivaren";
  private static final String DESCRIPTION = """
      Läkarintyg om arbetsförmåga – arbetsgivaren skapas från Försäkringskassans läkarintyg för sjukpenning (FK 7804) och används i följande situationer:
      
      <ul><li>När patienten har en anställning och behöver ett läkarintyg i förhållande till sin arbetsgivare.</li><li>När patienten varit frånvarande från arbetet i fler än 14 dagar.</li><li>När intygsutfärdande läkaren bedömer att patienten behöver vara sjukfrånvarande längre tid än 14 dagar och att sjukfrånvaron därmed kommer att överstiga sjuklöneperioden. <em>Läkarintyg om arbetsförmåga – arbetsgivaren</em> kan utfärdas för att undvika ett återbesök efter dag 14 och fyller då funktionen som intyg till arbetsgivare både <em>under</em> sjuklöneperioden och <em>efter</em> sjuklöneperioden.</li></ul>
      """;

  private static final String DETAILED_DESCRIPTION = """
      <p><b>Utfärdare</b><br/>Läkarintyget får utfärdas av läkare eller tandläkare. I intyget omfattar begreppet läkare båda professionerna. Det är inte möjligt att delegera rätten att utfärda läkarintyget till annan profession, till exempel sjuksköterska eller fysioterapeut. Se även Socialstyrelsens föreskrifter om intyg (HSLF-FS 2018:54).</p>
      <p><b>Syften med läkarintyget</b></p>
      <ul>
        <li><p><b>Rätt till frånvaro.</b> Att ge arbetstagaren rätt att vara frånvarande från arbetet på grund av sjukdom/skada. Arbetstagaren är skyldig att vid sjukdom/skada lämna in ett medicinskt underlag som styrker dels att frånvaron beror på sjukdom, dels att sjukdomen sätter ned arbetsförmågan i arbetstagarens arbetsuppgifter. Om läkarintyget inte ger tillräcklig information kan frånvaron anses vara olovlig och medföra att intyget behöver kompletteras.</p></li>
        <li><p><b>Arbetsgivarens rehabiliteringsansvar.</b> Läkarintyget ska ge arbetsgivaren tillräcklig information om arbetstagarens medicinska hinder för att utföra sina ordinarie arbetsuppgifter. Arbetsgivare har enligt socialförsäkringsbalken, arbetsmiljölagen och arbetsdomstolens rättspraxis ett omfattande och långtgående rehabiliteringsansvar och ska genom skäliga stöd och anpassningar på arbetsplatsen underlätta för sjuka eller skadade att komma tillbaka i arbete.</p></li>
        <li><p><b>Plan för återgång i arbete.</b> Sedan 2018 har arbetsgivare en skyldighet enligt 30 kap. 6 § socialförsäkringsbalken att ta fram en plan för återgång i arbete när en arbetstagare förväntas vara sjuk under en längre tid. Planen ska kontinuerligt följas upp och revideras vid behov. Planen är främst ett stöd för rehabiliteringsarbetet på arbetsplatsen och ska underlätta en tidig återgång i arbete. Läkarintyget ger grundläggande medicinsk information om patientens möjligheter att återgå i arbete och är en förutsättning för att arbetsgivaren tillsammans med sin arbetstagare ska kunna upprätta en plan för återgång i arbete.</p></li>
        <li><p><b>Sjukpenningtillägg.</b> Att ge arbetstagaren rätt till kompletterande sjukpenningtillägg. De flesta kollektivavtal på svensk arbetsmarknad ger arbetstagare rätt till sjukpenningtillägg vid sjukdom.</p></li>
        <li><p><b>Läkarintyg från och med dag 1 i sjuklöneperioden</b> Läkarintyget kan användas från och med dag 1 i sjuklöneperioden, ett så kallat förstadagsintyg. Arbetsgivaren får om det finns särskilda skäl för det begära att arbetstagaren genom intyg av läkare styrker nedsättning av arbetsförmågan tidigare än dag 8 i sjuklöneperioden.</p></li>
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

  public static final CertificateModelId AG7804_V2_0 = CertificateModelId.builder()
      .type(new CertificateType(AG_7804))
      .version(new CertificateVersion(VERSION))
      .build();

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(AG7804_V2_0)
        .type(
            new Code(
                "AG7804",
                "b64ea353-e8f6-4832-b563-fc7d46f29548",
                NAME
            )
        )
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION.replaceAll("\\R", ""))
        .activeFrom(activeFrom)
        .availableForCitizen(true)
        .sickLeaveProvider(new AG7804SickLeaveProvider())
        .summaryProvider(new AG7804CertificateSummaryProvider())
        .certificateActionSpecifications(AG7804CertificateActionSpecification.create())
        .messageActionSpecifications(AG7804MessageActionSpecification.create())
        .elementSpecifications(List.of(
            categorySmittbararpenning()
        ))
        .certificateActionFactory(certificateActionFactory)
        .build();
  }
}