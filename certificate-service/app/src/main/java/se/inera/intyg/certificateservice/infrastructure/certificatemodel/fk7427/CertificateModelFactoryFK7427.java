package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.CategoryDiagnos.categoryDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.CategoryGrundForMedicinsktUnderlag.categoryGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.CategoryHalsotillstand.categoryHalsotillstand;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionAnnanGrundForMedicinsktUnderlag.questionAnnanGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionDiagnos.questionDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionHalsotillstand.questionHalsotillstand;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionSymtom.questionSymtom;

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
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryFK7427 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;
  @Value("${certificate.model.fk7427.v1_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetofk.logicaladdress}")
  private String fkLogicalAddress;

  private final DiagnosisCodeRepository diagnosisCodeRepository;

  private static final String TYPE = "fk7427";
  private static final String VERSION = "1.0";
  private static final String NAME = "Läkarutlåtande tillfällig föräldrapenning barn 12–16 år";
  private static final String DESCRIPTION = """
      När ett barn mellan 12 och 16 år är sjukt kan den förälder som behöver avstå från sitt arbete för att vårda barnet få tillfällig föräldrapenning om barnet har ett särskilt behov av vård eller tillsyn. Läkarutlåtandet behövs från den första dagen i barnets vårdperiod.
      """;
  private static final String DETAILED_DESCRIPTION = """
      <b className="iu-fw-heading">Vad är Läkarutlåtande tillfällig föräldrapenning barn 12 - 16 år?</b><br>
      <p>När en förälder behöver avstå från arbete för att vårda ett sjukt barn som fyllt 12 men inte 16 år behövs ett läkarutlåtande från och med den första ersättningsdagen i vårdperioden.</p>
      <b className="iu-fw-heading">Förutsättningar för att få tillfällig föräldrapenning för barn 12 – 16 år</b><br>
      <p>Föräldrar till ett barn som fyllt 12 men inte 16 år kan få tillfällig föräldrapenning om barnet har ett särskilt behov av vård eller tillsyn.</p>
      <p>Det särskilda behovet av vård eller tillsyn ska styrkas med ett läkarutlåtande från den första ersättningsdagen.</p>
      <p>Ett särskilt behov av vård eller tillsyn kan finnas om en tillfällig sjukdom gör att barnet inte klarar sig självt. Barn med tidigare kända långvariga sjukdomar eller funktionsnedsättningar kan vid tillkommande sjukdomstillstånd också ha ett utökat behov av vård eller tillsyn.</p>
      <p>Försäkringskassan kan besluta att föräldrar till ett barn som har ett utökat vård- eller tillsynsbehov inte behöver visa läkarutlåtande från första dagen. Det kallas för förhandsbeslut.</p>
      <p>När föräldern har ett förhandsbeslut från Försäkringskassan behöver ett intyg från läkare eller sjuksköterska skickas till Försäkringskassan från och med den åttonde dagen i vårdperioden.</p>
      <b className="iu-fw-heading">Läkarbesök</b><br>
      <p>Vid läkarbesök behövs ett intyg om att förälderns närvaro var nödvändig vid besöket. Intygandet behöver inte ha något särskilt format utan kan göras på ett läkarvårdskvitto eller liknande. Det går också bra att använda Läkarutlåtande tillfällig föräldrapenning barn 12–16 år vid läkarbesök.</p>
      """;

  public static final CertificateModelId FK7427_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(TYPE))
      .version(new CertificateVersion(VERSION))
      .build();


  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK7427_V1_0)
        .type(
            new Code(
                "LU_TFP_B12_16",
                "b64ea353-e8f6-4832-b563-fc7d46f29548",
                NAME
            )
        )
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(false)
        .recipient(CertificateRecipientFactory.fkassa(fkLogicalAddress))
        .certificateActionSpecifications(FK7427CertificateActionSpecification.create())
        .messageActionSpecifications(FK7427MessageActionSpecification.create())
        .elementSpecifications(
            List.of(
                categoryGrundForMedicinsktUnderlag(
                    questionGrundForMedicinsktUnderlag(
                        questionAnnanGrundForMedicinsktUnderlag()
                    )
                ),
                categoryDiagnos(
                    questionDiagnos(
                        diagnosisCodeRepository
                    ),
                    questionSymtom()
                ),
                categoryHalsotillstand(
                    questionHalsotillstand()
                ),
                issuingUnitContactInfo()
            )
        )
        .certificateActionFactory(certificateActionFactory)
        .confirmationModalProvider(new FK7427CertificateConfirmationModalProvider())
        .build();
  }
}