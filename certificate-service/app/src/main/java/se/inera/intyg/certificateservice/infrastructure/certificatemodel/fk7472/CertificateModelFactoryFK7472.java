package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.CategoryPeriod.categoryPeriod;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.CategorySymptom.categorySymptom;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.QuestionPeriod.questionPeriod;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.QuestionSymptom.questionSymptom;

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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygstyp;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryFK7472 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;
  @Value("${certificate.model.fk7472.v1_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetofk.logicaladdress}")
  private String fkLogicalAddress;

  private static final String TYPE = "fk7472";
  private static final String VERSION = "1.0";
  private static final CertificateTypeName FK7472_TYPE_NAME = new CertificateTypeName("FK7472");
  private static final String NAME = "Intyg om tillfällig föräldrapenning";
  private static final String DESCRIPTION = """
         När ett barn är sjukt kan den förälder som behöver avstå från sitt arbete för att vårda barnet få tillfällig föräldrapenning. Från och med den åttonde dagen i barnets vårdperiod behöver ett intyg från en läkare eller sjuksköterska skickas till Försäkringskassan.                                                                                                                                                         \s
      """;
  private static final String DETAILED_DESCRIPTION = """
      <b className="iu-fw-heading">Vad är Intyg om tillfällig föräldrapenning?</b>
      <p>När ett barn är sjukt kan den förälder som behöver avstå från sitt arbete för att vårda barnet få tillfällig föräldrapenning. Vårdperioden räknas från och med den första dagen man får tillfällig föräldrapenning för barnet.</p>
      <b className="iu-fw-heading">Förutsättningar för att få tillfällig föräldrapenning:</b><br>
      <b className="iu-fw-heading">Om barnet är under 12 år</b>
      <p>Från den åttonde kalenderdagen i barnets vårdperiod behöver ett intyg från läkare eller sjuksköterska skickas till Försäkringskassan.</p>
      <b className="iu-fw-heading">Om barnet har fyllt 12 men inte 16 år</b>
      <p>När föräldern har ett förhandsbesked från Försäkringskassan behöver ett intyg från läkare eller sjuksköterska skickas till Försäkringskassan från och med den åttonde dagen i vårdperioden.<br>
      För barn som är 12-16 år krävs vanligtvis ett läkarutlåtande från första dagen i vårdperioden. Försäkringskassan kan besluta att föräldrar till ett barn som har ett utökat vård- eller tillsynsbehov inte behöver visa läkarutlåtande från första dagen. Det kallas för förhandsbeslut.</p>
      <b className="iu-fw-heading">Om barnet har fyllt 16 men inte 21 år</b>
      <p>För barn som omfattas av LSS (lagen om stöd och service till vissa funktionshindrade) behöver ett intyg från läkare eller sjuksköterska skickas till Försäkringskassan från och med den åttonde dagen i vårdperioden.<br>
      Intyget behövs vid tillkommande sjukdom eller en försämring av grundsjukdomen.</p>
      """;

  public static final CertificateModelId FK7472_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(TYPE))
      .version(new CertificateVersion(VERSION))
      .build();

  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk7472/schematron/itfp.v1.sch");

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK7472_V1_0)
        .type(CodeSystemKvIntygstyp.FK7472)
        .typeName(FK7472_TYPE_NAME)
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(false)
        .recipient(CertificateRecipientFactory.fkassa(fkLogicalAddress))
        .schematronPath(SCHEMATRON_PATH)
        .certificateActionSpecifications(FK7472CertificateActionSpecification.create())
        .messageActionSpecifications(FK7472MessageActionSpecification.create())
        .pdfSpecification(FK7472PdfSpecification.create())
        .elementSpecifications(
            List.of(
                categorySymptom(
                    questionSymptom()
                ),
                categoryPeriod(
                    questionPeriod()
                ),
                issuingUnitContactInfo()
            )
        )
        .certificateActionFactory(certificateActionFactory)
        .build();
  }
}
