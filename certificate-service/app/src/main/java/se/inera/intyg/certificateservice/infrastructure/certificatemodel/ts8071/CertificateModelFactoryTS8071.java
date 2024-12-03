package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryAdhdAutismPsykiskUtvecklingsstorning.categoryAdhdAutismPsykiskUtvecklingsstorning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryAlkoholNarkotikaOchLakemedel.categoryAlkoholNarkotikaOchLakemedel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryAnamnes.categoryAnamnes;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryBalanssinne.categoryBalanssinne;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryBedomning.categoryBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryDemensOchAndraKognitivaStorningar.categoryDemensOchAndraKognitivaStorningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryDiabetes.categoryDiabetes;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryEpilepsi.categoryEpilepsi;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryHjartOchKarlsjukdomar.categoryHjartOchKarlsjukdomar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryHorsel.categoryHorsel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryIdentitet.categoryIdentitet;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryIntygetAvser.categoryIntygetAvser;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryIntygetBaseratPa.categoryIntygetBaseratPa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryNeurologiskaSjukdomar.categoryNeurologiskaSjukdomar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryNjursjukdomar.categoryNjursjukdomar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryOvrigKommentar.categoryOvrigKommentar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryOvrigMedicinering.categoryOvrigMedicinering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryPsykiskaSjukdomarOchStorningar.categoryPsykiskaSjukdomarOchStorningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategoryRorelseorganensFunktioner.categoryRorelseorganensFunktioner;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategorySomnOchVakenhetsstorningar.categorySomnOchVakenhetsstorningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategorySynfunktion.categorySynfunktion;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.CategorySynskarpa.categorySynskarpa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionBalanssinne.questionBalanssinne;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionBalanssinneBeskrivning.questionBalanssinneBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHjartsjukdom.questionHjartsjukdom;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHorsel.questionHorsel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHorselhjalpmedel.questionHorselhjalpmedel;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionHorselhjalpmedelPosition.questionHorselhjalpmedelPosition;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionIdentitet.questionIdentitet;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionRorlighet.questionRorlighet;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionRorlighetBeskrivning.questionRorlighetBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSjukdomEllerSynnedsattning.questionSjukdomEllerSynnedsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSjukdomEllerSynnedsattningBeskrivning.questionSjukdomEllerSynnedsattningBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSjukdomshistorik.questionSjukdomshistorik;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSjukdomshistorikBeskrivning.questionSjukdomshistorikBeskrivning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSynfunktioner.questionSynfunktioner;

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
public class CertificateModelFactoryTS8071 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;
  @Value("${certificate.model.ts8071.v1_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetots.logicaladdress}")
  private String tsLogicalAddress;

  private static final String TS_8071 = "ts8071";
  private static final String VERSION = "1.0";
  private static final String NAME = "Läkarintyg avseende högre körkortsbehörigheter eller taxiförarlegitimation";
  private static final String DESCRIPTION = """
      <b className="iu-fw-heading">Läkarintyg avseende högre körkortsbehörigheter eller taxiförarlegitimation</b> 1.0
            
      Transportstyrelsens läkarintyg ska användas vid förlängd giltighet av högre behörighet från 45 år, ansökan om körkortstillstånd för grupp II och III och vid ansökan om taxiförarlegitimation. Transportstyrelsens läkarintyg kan även användas när Transportstyrelsen i annat fall begärt ett allmänt läkarintyg avseende lämplighet att inneha körkort.
      """;
  private static final String DETAILED_DESCRIPTION = """
       <b className="iu-fw-heading">Läkarintyg avseende högre körkortsbehörigheter eller taxiförarlegitimation 1.0</b> 1.0
       Transportstyrelsens läkarintyg ska användas vid förlängd giltighet av högre behörighet från 45 år, ansökan om körkortstillstånd för grupp II och III och vid ansökan om taxiförarlegitimation. Transportstyrelsens läkarintyg kan även användas när Transportstyrelsen i annat fall begärt ett allmänt läkarintyg avseende lämplighet att inneha körkort.
            
       Specialistintyg finns bl.a. för alkohol, läkemedel, synfunktion, Alkolås m.m. Se www.transportstyrelsen.se.
            
       Läkaren ska uppmärksamma Transportstyrelsens föreskrifter och allmänna råd (TSFS 2010:125) om medicinska krav för innehav av körkort m.m.
            
       Intyget ska vara utfärdat tidigast 2 månader före att ansökan kom in till Transportstyrelsen och i enlighet med vad som sägs i 17 kapitlet.
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
        .type(
            new Code(
                "TSBAS",
                "f6fb361a-e31d-48b8-8657-99b63912dd9b",
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
                categoryIntygetAvser(),
                categoryIntygetBaseratPa(),
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
                    )
                ),
                categoryHjartOchKarlsjukdomar(
                    questionHjartsjukdom()
                ),
                categoryDiabetes(),
                categoryNeurologiskaSjukdomar(),
                categoryEpilepsi(),
                categoryNjursjukdomar(),
                categoryDemensOchAndraKognitivaStorningar(),
                categorySomnOchVakenhetsstorningar(),
                categoryAlkoholNarkotikaOchLakemedel(),
                categoryPsykiskaSjukdomarOchStorningar(),
                categoryAdhdAutismPsykiskUtvecklingsstorning(),
                categoryOvrigMedicinering(),
                categoryOvrigKommentar(),
                categoryBedomning()
            )
        )
        .recipient(CertificateRecipientFactory.transp(tsLogicalAddress))
        .certificateActionSpecifications(TS8071CertificateActionSpecification.create())
        .certificateActionFactory(certificateActionFactory)
        .build();
  }
}
