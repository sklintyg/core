package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.CategoryAktivitetsbegransningar.categoryAktivitetsbegransningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.CategoryDiagnos.categoryDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.CategoryFunktionsnedsattning.categoryFunktionsnedsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.CategoryGrundForMedicinsktUnderlag.categoryGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.CategoryMedicinskBehandling.categoryMedicinskBehandling;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.CategoryOvrigt.categoryOvrigt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.CategoryPrognos.categoryPrognos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionAktivitetsbegransningar.questionAktivitetsbegransningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionAnnanGrundForMedicinsktUnderlag.questionAnnanGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionAnnanKroppsligFunktionMotivering.questionAnnanKroppsligFunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionBaseratPaAnnatMedicinsktUnderlag.questionBaseratPaAnnatMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionDiagnos.questionDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionDiagnosHistorik.questionDiagnosHistorik;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionFunktionsnedsattning.questionFunktionsnedsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionIntellektuellFunktionMotivering.questionIntellektuellFunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionKommunikationSocialInteraktionMotivering.questionKommunikationSocialInteraktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionKoordinationMotivering.questionKoordinationMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionOvrigt.questionOvrigt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionPagaendeOchPlaneradeBehandlingar.questionPagaendeOchPlaneradeBehandlingar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionPrognos.questionPrognos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionPsykiskFunktionMotivering.questionPsykiskFunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionRelationTillPatienten.questionRelationTillPatienten;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionSinnesfunktionMotivering.questionSinnesfunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionUppmarksamhetMotivering.questionUppmarksamhetMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionUtredningEllerUnderlag.questionUtredningEllerUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionVardenhetOchTidplan.questionVardenhetOchTidplan;

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
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygstyp;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryFK3221 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;
  @Value("${certificate.model.fk3221.v1_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetofk.logicaladdress}")
  private String fkLogicalAddress;

  private final DiagnosisCodeRepository diagnosisCodeRepository;

  private static final String FK_3221 = "fk3221";
  private static final String VERSION = "1.0";
  private static final CertificateTypeName FK3221_TYPE_NAME = new CertificateTypeName("FK3221");
  private static final String NAME = "Läkarutlåtande för omvårdnadsbidrag eller merkostnadsersättning";
  private static final String DESCRIPTION = """
      <b className="iu-fw-heading">Vem kan få omvårdnadsbidrag eller merkostnadsersättning för barn?</b>
      
      Omvårdnadsbidrag och merkostnadsersättning för barn är till för föräldrar som har barn med funktionsnedsättning.
      
      Omvårdnadsbidrag kan beviljas om barnet behöver mer omvårdnad och tillsyn än barn i samma ålder som inte har en funktionsnedsättning. Behoven ska antas finnas i minst 6 månader.
      
      Merkostnadsersättning kan beviljas om föräldern har merkostnader som beror på barnets funktionsnedsättning. Merkostnaderna ska uppgå till minst 25 procent av ett prisbasbelopp per år, och funktionsnedsättningen ska antas finnas i minst 6 månader.
      """;
  private static final String DETAILED_DESCRIPTION = """
       <b className="iu-fw-heading">Vem kan få omvårdnadsbidrag eller merkostnadsersättning för barn?</b>
      
       Föräldrar som har barn med funktionsnedsättning kan ha rätt till omvårdnadsbidrag eller merkostnadsersättning.
      
       Omvårdnadsbidrag kan beviljas om barnet har behov av mer omvårdnad och tillsyn jämfört med barn i samma ålder som inte har en funktionsnedsättning. Behoven ska antas finnas i minst 6 månader. Omvårdnad och tillsyn kan till exempel vara behov av praktisk hjälp i vardagen, hjälp med struktur och kommunikation eller att föräldern behöver ha extra uppsikt över barnet.
      
      Merkostnadsersättning kan beviljas om föräldern har merkostnader som beror på barnets funktionsnedsättning. Funktionsnedsättningen ska antas finnas i minst 6 månader. För att få merkostnadsersättning ska merkostnaderna uppgå till minst 25 procent av ett prisbasbelopp per år.
      
       Merkostnader delas in sju olika kategorier:
       <ul><li>hälsa, vård och kost</li><li>slitage och rengöring</li><li>resor</li><li>hjälpmedel</li><li>hjälp i den dagliga livsföringen</li><li>boende</li><li>övriga ändamål</li></ul>
      """;

  public static final CertificateModelId FK3221_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(FK_3221))
      .version(new CertificateVersion(VERSION))
      .build();

  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk3221/schematron/lu_omv_mek.v1.sch");

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK3221_V1_0)
        .type(CodeSystemKvIntygstyp.FK3221)
        .typeName(FK3221_TYPE_NAME)
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(false)
        .recipient(CertificateRecipientFactory.fkassa(fkLogicalAddress))
        .schematronPath(SCHEMATRON_PATH)
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
        .certificateActionSpecifications(FK3221CertificateActionSpecification.create())
        .messageActionSpecifications(FK3221MessageActionSpecification.create())
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
        .pdfSpecification(FK3221PdfSpecification.create())
        .build();
  }
}