package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.CategoryAktivitetsbegransningar.categoryAktivitetsbegransningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.CategoryDiagnos.categoryDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.CategoryFunktionsnedsattning.categoryFunktionsnedsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.CategoryGrundForMedicinsktUnderlag.categoryGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.CategoryMedicinskBehandling.categoryMedicinskBehandling;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.CategoryOvrigt.categoryOvrigt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.CategoryPrognos.categoryPrognos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionAktivitetsbegransningar.questionAktivitetsbegransningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionAnnanGrundForMedicinsktUnderlag.questionAnnanGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionAnnanKroppsligFunktionMotivering.questionAnnanKroppsligFunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionBaseratPaAnnatMedicinsktUnderlag.questionBaseratPaAnnatMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionDiagnos.questionDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionDiagnosHistorik.questionDiagnosHistorik;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.questionFunktionsnedsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionHorselFunktionMotivering.questionHorselFunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionIntellektuellFunktionMotivering.questionIntellektuellFunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionKommunikationSocialInteraktionMotivering.questionKommunikationSocialInteraktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionKoordinationMotivering.questionKoordinationMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionOvrigt.questionOvrigt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionPagaendeOchPlaneradeBehandlingar.questionPagaendeOchPlaneradeBehandlingar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionPrognos.questionPrognos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionPsykiskFunktionMotivering.questionPsykiskFunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionRelationTillPatienten.questionRelationTillPatienten;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionSinnesfunktionMotivering.questionSinnesfunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionSynfunktionMotivering.questionSynfunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionUppmarksamhetMotivering.questionUppmarksamhetMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionUtredningEllerUnderlag.questionUtredningEllerUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionVardenhetOchTidplan.questionVardenhetOchTidplan;

import java.time.LocalDateTime;
import java.util.Collections;
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
public class CertificateModelFactoryFK7809 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;
  @Value("${certificate.model.fk7809.v1_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetofk.logicaladdress}")
  private String fkLogicalAddress;

  private final DiagnosisCodeRepository diagnosisCodeRepository;

  private static final String FK_7809 = "fk7809";
  private static final String VERSION = "1.0";
  private static final CertificateTypeName FK7809_TYPE_NAME = new CertificateTypeName("FK7809");
  private static final String NAME = "Läkarutlåtande för merkostnadsersättning";
  private static final String DESCRIPTION = """
      <b className="iu-fw-heading">Vem kan få merkostnadsersättning?</b>
      
      En person kan ha rätt till merkostnadsersättning för kostnader som beror på att hen fått en varaktig funktionsnedsättning som kan antas finnas i minst ett år. Funktionsnedsättningen ska ha uppstått innan hen fyllde 66 år. Om personen är född 1957 eller tidigare kan hen även få ersättning för kostnader som beror på att hen fått en funktionsnedsättning innan hen fyllde 65 år. För att få merkostnadsersättning ska merkostnaderna uppgå till minst 25 procent av ett prisbasbelopp per år.
      
      Den som anses vara blind eller gravt hörselskadad kan få en garanterad nivå av merkostnadsersättning utan att ha några merkostnader.
      """;
  private static final String DETAILED_DESCRIPTION = """
       <b className="iu-fw-heading">Vem kan få merkostnadsersättning?</b>
      
       En person kan ha rätt till merkostnadsersättning för kostnader som beror på att hen har en funktionsnedsättning. Funktionsnedsättningen ska antas finnas i minst ett år.
      
       Personen måste ha fått sin funktionsnedsättning innan hen fyllde 66 år. Om personen är född 1957 eller tidigare måste hen ha fått sin funktionsnedsättning innan hen fyllde 65 år.
      
       För att ha rätt till merkostnadsersättning ska merkostnaderna uppgå till minst 25 procent av ett prisbasbelopp per år.
      
       Merkostnader delas in sju olika kategorier:
       <ul><li>hälsa, vård och kost</li><li>slitage och rengöring</li><li>resor</li><li>hjälpmedel</li><li>hjälp i den dagliga livsföringen</li><li>boende</li><li>övriga ändamål</li></ul>
       Den som anses vara blind eller gravt hörselskadad kan få en garanterad nivå av merkostnadsersättning utan att ha några merkostnader.
      """;
  private static final String PREAMBLE_TEXT =
      "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. "
          + "Har du frågor kontaktar du den som skrivit ditt intyg.";

  public static final CertificateModelId FK7809_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(FK_7809))
      .version(new CertificateVersion(VERSION))
      .build();

  private static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk7809/schematron/lumek.v1.sch");

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK7809_V1_0)
        .type(CodeSystemKvIntygstyp.FK7809)
        .typeName(FK7809_TYPE_NAME)
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .schematronPath(SCHEMATRON_PATH)
        .activeFrom(activeFrom)
        .availableForCitizen(true)
        .summaryProvider(new FK7809CertificateSummaryProvider())
        .confirmationModalProvider(new FK7809CertificateConfirmationModalProvider())
        .texts(
            List.of(
                CertificateText.builder()
                    .text(PREAMBLE_TEXT)
                    .type(CertificateTextType.PREAMBLE_TEXT)
                    .links(Collections.emptyList())
                    .build()
            )
        )
        .recipient(CertificateRecipientFactory.fkassa(fkLogicalAddress))
        .messageTypes(
            List.of(
                CertificateMessageType.builder()
                    .type(MessageType.MISSING)
                    .subject(new Subject(MessageType.MISSING.displayName()))
                    .build(),
                CertificateMessageType.builder()
                    .type(MessageType.CONTACT)
                    .subject(new Subject(MessageType.CONTACT.displayName()))
                    .build(),
                CertificateMessageType.builder()
                    .type(MessageType.OTHER)
                    .subject(new Subject(MessageType.OTHER.displayName()))
                    .build()
            )
        )
        .certificateActionSpecifications(FK7809CertificateActionSpecification.create())
        .messageActionSpecifications(FK7809MessageActionSpecification.create())
        .elementSpecifications(
            List.of(
                categoryGrundForMedicinsktUnderlag(
                    questionGrundForMedicinsktUnderlag(
                        questionRelationTillPatienten(),
                        questionAnnanGrundForMedicinsktUnderlag()
                    ),
                    questionBaseratPaAnnatMedicinsktUnderlag(),
                    questionUtredningEllerUnderlag()
                ),
                categoryDiagnos(
                    questionDiagnos(diagnosisCodeRepository),
                    questionDiagnosHistorik()
                ),
                categoryFunktionsnedsattning(
                    questionFunktionsnedsattning(),
                    questionIntellektuellFunktionMotivering(),
                    questionKommunikationSocialInteraktionMotivering(),
                    questionUppmarksamhetMotivering(),
                    questionPsykiskFunktionMotivering(),
                    questionHorselFunktionMotivering(),
                    questionSynfunktionMotivering(),
                    questionSinnesfunktionMotivering(),
                    questionKoordinationMotivering(),
                    questionAnnanKroppsligFunktionMotivering()
                ),
                categoryAktivitetsbegransningar(
                    questionAktivitetsbegransningar()
                ),
                categoryMedicinskBehandling(
                    questionPagaendeOchPlaneradeBehandlingar(
                        questionVardenhetOchTidplan()
                    )
                ),
                categoryPrognos(
                    questionPrognos()
                ),
                categoryOvrigt(
                    questionOvrigt()
                ),
                issuingUnitContactInfo()
            )
        )
        .certificateActionFactory(certificateActionFactory)
        .pdfSpecification(FK7809PdfSpecification.create())
        .build();
  }
}