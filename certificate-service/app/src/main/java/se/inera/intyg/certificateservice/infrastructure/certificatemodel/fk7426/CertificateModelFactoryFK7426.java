package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.CategoryBehandling.categoryBehandling;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.CategoryDiagnos.categoryDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.CategoryGrundForBedomning.categoryGrundForBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.CategoryGrundForMedicinsktUnderlag.categoryGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.CategoryHalsotillstand.categoryHalsotillstand;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.CategoryPeriodSjukdom.categoryPeriodSjukdom;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.CategoryVard.categoryVard;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionAnnanGrundForMedicinsktUnderlag.questionAnnanGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionDiagnos.questionDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionGrundForBedomning.questionGrundForBedomning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionHalsotillstandPsykiska.questionHalsotillstandPsykiska;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionHalsotillstandSomatiska.questionHalsotillstandSomatiska;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPagaendeBehandlingar.questionPagaendeBehandlingar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPeriodSjukdom.questionPeriodSjukdom;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPeriodSjukdomMotivering.questionPeriodSjukdomMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPeriodVardasBarnInneliggandePaSjukhus.questionPeriodVardasBarnInneliggandePaSjukhus;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPeriodVardasBarnetInskrivetMedHemsjukvard.questionPeriodVardasBarnetInskrivetMedHemsjukvard;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPlaneradeBehandlingar.questionPlaneradeBehandlingar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionSymtom.questionSymtom;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionVardasBarnetInneliggandePaSjukhus.questionVardasBarnetInneliggandePaSjukhus;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionVardasBarnetInskrivetMedHemsjukvard.questionVardasBarnetInskrivetMedHemsjukvard;

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
public class CertificateModelFactoryFK7426 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;
  @Value("${certificate.model.fk7426.v1_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetofk.logicaladdress}")
  private String fkLogicalAddress;

  private final DiagnosisCodeRepository diagnosisCodeRepository;

  private static final String FK_7426 = "fk7426";
  private static final String VERSION = "1.0";
  private static final CertificateTypeName FK7426_TYPE_NAME = new CertificateTypeName("FK7426");
  private static final String NAME = "Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har fyllt 18 år";
  private static final String DESCRIPTION = """
          När ett barn är allvarligt sjukt kan föräldrar som behöver avstå från sitt arbete få tillfällig föräldrapenning under ett obegränsat antal dagar. Läkarutlåtandet behövs från den första dagen som barnet bedöms vara allvarligt sjukt.
      """;
  private static final String DETAILED_DESCRIPTION = """
      <b className="iu-fw-heading">Vad är Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har fyllt 18 år?</b><br>
      <p>Läkarutlåtandet används när ett barn är allvarligt sjukt. För att barnet ska bedömas vara allvarligt sjukt måste någon av följande punkter vara uppfyllda:</p>
      <ul><li>Barnet misstänks lida av en sjukdom som är förenad med ett påtagligt hot mot barnets liv.</li><li>Barnet har diagnosticerats med en sjukdom som är förenad med ett påtagligt hot mot barnets liv.</li><li>Barnet har lidit av en sådan sjukdom som är förenad med ett påtagligt hot mot barnets liv och barnets hälsotillstånd under eftervårdsfasen är allvarligt påverkat.</li></ul>
      <b className="iu-fw-heading">Förutsättningar för att få tillfällig föräldrapenning</b><br>
      <p>Tillfällig föräldrapenning kan betalas ut om en förälder behöver avstå från arbete för att vårda ett sjukt barn.</p>
      <p>När ett barn bedöms vara allvarligt sjukt gäller särskilda regler för tillfällig föräldrapenning. Ersättningen kan då betalas ut under ett obegränsat antal dagar och båda föräldrarna kan få ersättning samtidigt, för samma barn.</p>
      <p>Läkarutlåtande behövs från den första dagen som en förälder vill ansöka om tillfällig föräldrapenning för ett barn som bedöms allvarligt sjukt.</p>
      """;

  public static final CertificateModelId FK7426_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(FK_7426))
      .version(new CertificateVersion(VERSION))
      .build();

  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk7426/schematron/lu_tfp_asb_18.v1.sch");

  public static final short TEXT_FIELD_LIMIT = 4000;

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK7426_V1_0)
        .type(CodeSystemKvIntygstyp.FK7426)
        .typeName(FK7426_TYPE_NAME)
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .activeFrom(activeFrom)
        .schematronPath(SCHEMATRON_PATH)
        .availableForCitizen(false)
        .recipient(CertificateRecipientFactory.fkassa(fkLogicalAddress))
        .certificateActionSpecifications(FK7426CertificateActionSpecification.create())
        .messageActionSpecifications(FK7426MessageActionSpecification.create())
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
        .elementSpecifications(
            List.of(
                categoryGrundForMedicinsktUnderlag(
                    questionGrundForMedicinsktUnderlag(
                        questionAnnanGrundForMedicinsktUnderlag()
                    )
                ),
                categoryDiagnos(
                    questionDiagnos(diagnosisCodeRepository),
                    questionSymtom()
                ),
                categoryHalsotillstand(
                    questionHalsotillstandSomatiska(),
                    questionHalsotillstandPsykiska()
                ),
                categoryGrundForBedomning(
                    questionGrundForBedomning()
                ),
                categoryBehandling(
                    questionPagaendeBehandlingar(),
                    questionPlaneradeBehandlingar()
                ),
                categoryPeriodSjukdom(
                    questionPeriodSjukdom(),
                    questionPeriodSjukdomMotivering()
                ),
                categoryVard(
                    questionVardasBarnetInneliggandePaSjukhus(
                        questionPeriodVardasBarnInneliggandePaSjukhus()
                    ),
                    questionVardasBarnetInskrivetMedHemsjukvard(
                        questionPeriodVardasBarnetInskrivetMedHemsjukvard()
                    )
                ),
                issuingUnitContactInfo()
            )
        )
        .certificateActionFactory(certificateActionFactory)
        .confirmationModalProvider(new FK7426CertificateConfirmationModalProvider())
        .pdfSpecification(
            FK7426PdfSpecification.create()
        )
        .build();
  }
}