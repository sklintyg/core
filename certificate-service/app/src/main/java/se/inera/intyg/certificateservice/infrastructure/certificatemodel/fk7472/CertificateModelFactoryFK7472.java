package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType.EN_ATTONDEL;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType.EN_FJARDEDEL;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType.HALVA;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType.HELA;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType.TRE_FJARDEDELAR;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.WorkCapacityType;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRangeList;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationUnitContactInformation;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateRecipientFactory;

@Component
public class CertificateModelFactoryFK7472 implements CertificateModelFactory {

  private static final short LIMIT = 318;
  @Value("${certificate.model.fk7472.v1_0.active.from}")
  private LocalDateTime activeFrom;
  private static final String TYPE = "fk7472";
  private static final String VERSION = "1.0";
  private static final String NAME = "Intyg om tillfällig föräldrapenning";
  private static final String DESCRIPTION = """
      <p className="iu-fw-heading">Vad är Intyg om tillfällig föräldrapenning?</p>
      <p>När ett barn är sjukt kan den förälder som behöver avstå från sitt arbete för att vårda barnet få tillfällig föräldrapenning. Vårdperioden räknas från och med den första dagen man får tillfällig föräldrapenning för barnet.</p>
      <p className="iu-fw-heading">Förutsättningar för att få tillfällig föräldrapenning:</p>
      <p className="iu-fw-heading">Om barnet är under 12 år</p>
      <p>Från den åttonde kalenderdagen i barnets vårdperiod behöver ett intyg från läkare eller sjuksköterska skickas till Försäkringskassan.</p>
      <p className="iu-fw-heading">Om barnet har fyllt 12 men inte 16 år</p>
      <p>När föräldern har ett förhandsbesked från Försäkringskassan behöver ett intyg från läkare eller sjuksköterska skickas till Försäkringskassan från och med den åttonde dagen i vårdperioden.\s
      För barn som är 12-16 år krävs vanligtvis ett läkarutlåtande från första dagen i vårdperioden. Försäkringskassan kan besluta att föräldrar till ett barn som har ett utökat vård- eller tillsynsbehov inte behöver visa läkarutlåtande från första dagen. Det kallas för förhandsbeslut.</p>
      <p className="iu-fw-heading">Om barnet har fyllt 16 men inte 21 år</p>
      <p>För barn som omfattas av LSS (lagen om stöd och service till vissa funktionshindrade) behöver ett intyg från läkare eller sjuksköterska skickas till Försäkringskassan från och med den åttonde dagen i vårdperioden.\s
      Intyget behövs vid tillkommande sjukdom eller en försämring av grundsjukdomen.</p>
      """;
  public static final CertificateModelId FK7472_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(TYPE))
      .version(new CertificateVersion(VERSION))
      .build();
  public static final ElementId QUESTION_SYMPTOM_CATEGORY_ID = new ElementId(
      "KAT_1");
  public static final ElementId QUESTION_SYMPTOM_ID = new ElementId("55");
  private static final FieldId QUESTION_SYMPTOM_FIELD_ID = new FieldId("55.1");

  private static final ElementId QUESTION_PERIOD_CATEGORY_ID = new ElementId("KAT_2");
  public static final ElementId QUESTION_PERIOD_ID = new ElementId("56");
  private static final String QUESTION_PERIOD_FIELD_ID = "56.1";
  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk7472/schematron/itfp.v1.sch");

  public static final String PDF_FK_7472_PDF = "fk7472/pdf/fk7472_v1.pdf";

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK7472_V1_0)
        .type(
            new Code(
                "ITFP",
                "b64ea353-e8f6-4832-b563-fc7d46f29548",
                NAME
            )
        )
        .name(NAME)
        .description(DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(false)
        .rolesWithAccess(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
            Role.CARE_ADMIN))
        .recipient(CertificateRecipientFactory.fkassa())
        .certificateActionSpecifications(
            List.of(
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.CREATE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.READ)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.UPDATE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.DELETE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.SIGN)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.SEND)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.PRINT)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.REVOKE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.REPLACE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.REPLACE_CONTINUE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.RENEW)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.RECEIVE_COMPLEMENT)
                    .build()
            )
        )
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
        .pdfTemplatePath(PDF_FK_7472_PDF)
        .schematronPath(SCHEMATRON_PATH)
        .build();
  }

  private static ElementSpecification categorySymptom(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_SYMPTOM_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Barnets diagnos eller symtom")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }

  private static ElementSpecification categoryPeriod(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_PERIOD_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Period som barnet inte bör vårdas i ordinarie tillsynsform")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }

  private static ElementSpecification questionSymptom() {
    return ElementSpecification.builder()
        .id(QUESTION_SYMPTOM_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Ange diagnos eller symtom")
                .id(QUESTION_SYMPTOM_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_SYMPTOM_ID,
                    QUESTION_SYMPTOM_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(QUESTION_SYMPTOM_ID, LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(318)
                    .build()
            )
        )
        .build();
  }

  private static ElementSpecification questionPeriod() {
    final var dateRanges = List.of(
        getDateRange(EN_ATTONDEL),
        getDateRange(EN_FJARDEDEL),
        getDateRange(HALVA),
        getDateRange(TRE_FJARDEDELAR),
        getDateRange(HELA)
    );

    return ElementSpecification.builder()
        .id(QUESTION_PERIOD_ID)
        .includeWhenRenewing(false)
        .configuration(
            ElementConfigurationCheckboxDateRangeList.builder()
                .name("Jag bedömer att barnet inte ska vårdas i ordinarie tillsynsform")
                .label("Andel av ordinarie tid:")
                .id(new FieldId(QUESTION_PERIOD_FIELD_ID))
                .dateRanges(dateRanges)
                .min(Period.ofMonths(-1))
                .hideWorkingHours(true)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PERIOD_ID,
                    dateRanges.stream().map(CheckboxDateRange::id).toList()
                )
            )
        )
        .validations(
            List.of(
                ElementValidationDateRangeList.builder()
                    .min(Period.ofMonths(-1))
                    .mandatory(true)
                    .build()
            )
        )
        .build();
  }

  private static ElementSpecification issuingUnitContactInfo() {
    return ElementSpecification.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .configuration(
            ElementConfigurationUnitContactInformation.builder().build()
        )
        .validations(
            List.of(
                ElementValidationUnitContactInformation.builder().build()
            )
        )
        .build();
  }

  private static CheckboxDateRange getDateRange(WorkCapacityType type) {
    return new CheckboxDateRange(new FieldId(type.toString()), type.label());
  }
}