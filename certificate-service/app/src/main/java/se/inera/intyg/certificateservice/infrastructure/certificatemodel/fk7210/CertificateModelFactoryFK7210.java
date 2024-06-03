package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;

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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.CertificateLink;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationUnitContactInformation;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateRecipientFactory;

@Component
public class CertificateModelFactoryFK7210 implements CertificateModelFactory {

  @Value("${certificate.model.fk7210.v1_0.active.from}")
  private LocalDateTime activeFrom;
  private static final String FK_7210 = "fk7210";
  private static final String VERSION = "1.0";
  private static final String NAME = "Intyg om graviditet";
  private static final String DETAILED_DESCRIPTION = """
      <b>Vad är intyg om graviditet?</b>
      När en person är gravid ska hen få ett intyg om graviditet av hälso- och sjukvården. Intyget behövs om den gravida begär ersättning från Försäkringskassan innan barnet är fött.<br><br> Intyget skickas till Försäkringskassan digitalt av hälso- och sjukvården eller av den gravida.
      """;
  private static final String DESCRIPTION = """
          När en person är gravid ska hen få ett intyg om graviditet av hälso- och sjukvården. Intyget behövs om den gravida begär ersättning från Försäkringskassan innan barnet är fött.
      """;
  public static final CertificateModelId FK7210_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(FK_7210))
      .version(new CertificateVersion(VERSION))
      .build();
  public static final ElementId QUESTION_BERAKNAT_FODELSEDATUM_CATEGORY_ID = new ElementId(
      "KAT_1");
  public static final ElementId QUESTION_BERAKNAT_FODELSEDATUM_ID = new ElementId("54");
  private static final FieldId QUESTION_BERAKNAT_FODELSEDATUM_FIELD_ID = new FieldId("54.1");
  public static final String PDF_FK_7210_PDF = "fk7210/pdf/fk7210_v1.pdf";
  public static final String PDF_NO_ADDRESS_FK_7210_PDF = "fk7210/pdf/fk7210_v1_no_address.pdf";
  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk7210/schematron/igrav.v1.sch");
  public static final String LINK_FK_ID = "LINK_FK";
  public static final String PREAMBLE_TEXT =
      "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. "
          + "Har du frågor kontaktar du den som skrivit ditt intyg. Om du vill ansöka om föräldrapenning, gör du det på {"
          + LINK_FK_ID + "}. \nDen externa länken leder till forsakringskassan.se";
  public static final String URL_FK = "https://www.forsakringskassan.se/";
  public static final String FK_NAME = "Försäkringskassan";

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK7210_V1_0)
        .type(
            new Code(
                "IGRAV",
                "b64ea353-e8f6-4832-b563-fc7d46f29548",
                NAME
            )
        )
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(true)
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
                    .build()
            )
        )
        .elementSpecifications(
            List.of(
                categoryBeraknatFodelsedatum(
                    questionBeraknatFodelsedatum()
                ),
                issuingUnitContactInfo()
            )
        )
        .pdfTemplatePath(PDF_FK_7210_PDF)
        .pdfNoAddressTemplatePath(PDF_NO_ADDRESS_FK_7210_PDF)
        .schematronPath(SCHEMATRON_PATH)
        .summaryProvider(new FK7210CertificateSummaryProvider())
        .texts(List.of(CertificateText.builder()
            .text(PREAMBLE_TEXT)
            .type(CertificateTextType.PREAMBLE_TEXT)
            .links(List.of(CertificateLink.builder()
                .url(URL_FK)
                .id(LINK_FK_ID)
                .name(FK_NAME)
                .build()))
            .build()))
        .build();
  }

  private static ElementSpecification categoryBeraknatFodelsedatum(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_BERAKNAT_FODELSEDATUM_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Beräknat födelsedatum")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }

  private static ElementSpecification questionBeraknatFodelsedatum() {
    return ElementSpecification.builder()
        .id(QUESTION_BERAKNAT_FODELSEDATUM_ID)
        .configuration(
            ElementConfigurationDate.builder()
                .name("Datum")
                .id(QUESTION_BERAKNAT_FODELSEDATUM_FIELD_ID)
                .min(Period.ofDays(0))
                .max(Period.ofYears(1))
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_BERAKNAT_FODELSEDATUM_ID,
                    QUESTION_BERAKNAT_FODELSEDATUM_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationDate.builder()
                    .mandatory(true)
                    .min(Period.ofDays(0))
                    .max(Period.ofYears(1))
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
}
