package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.elements.CategoryBeraknatFodelsedatum.categoryBeraknatFodelsedatum;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.elements.QuestionBeraknatFodelsedatum.questionBeraknatFodelsedatum;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.CertificateLink;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;

@Component
public class CertificateModelFactoryFK7210 implements CertificateModelFactory {

  @Value("${certificate.model.fk7210.v1_0.active.from}")
  private LocalDateTime activeFrom;
  private static final String FK_7210 = "fk7210";
  private static final String VERSION = "1.0";
  private static final String NAME = "Intyg om graviditet";

  private static final Code IGRAV = new Code(
      "IGRAV",
      "b64ea353-e8f6-4832-b563-fc7d46f29548",
      NAME
  );

  private static final String DESCRIPTION = """
          När en person är gravid ska hen få ett intyg om graviditet av hälso- och sjukvården. Intyget behövs om den gravida begär ersättning från Försäkringskassan innan barnet är fött.
      """;
  private static final String DETAILED_DESCRIPTION = """
      <b>Vad är intyg om graviditet?</b>
      När en person är gravid ska hen få ett intyg om graviditet av hälso- och sjukvården. Intyget behövs om den gravida begär ersättning från Försäkringskassan innan barnet är fött.<br><br> Intyget skickas till Försäkringskassan digitalt av hälso- och sjukvården eller av den gravida.
      """;

  public static final CertificateModelId FK7210_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(FK_7210))
      .version(new CertificateVersion(VERSION))
      .build();

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
        .type(IGRAV)
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(true)
        .recipient(CertificateRecipientFactory.fkassa())
        .certificateActionSpecifications(FK7210CertificateActionSpecification.create())
        .schematronPath(SCHEMATRON_PATH)
        .summaryProvider(new FK7210CertificateSummaryProvider())
        .pdfSpecification(FK7210PdfSpecification.create())
        .rolesWithAccess(
            List.of(
                Role.DOCTOR,
                Role.PRIVATE_DOCTOR,
                Role.NURSE,
                Role.MIDWIFE,
                Role.CARE_ADMIN
            )
        )
        .texts(
            List.of(
                CertificateText.builder()
                    .text(PREAMBLE_TEXT)
                    .type(CertificateTextType.PREAMBLE_TEXT)
                    .links(List.of(CertificateLink.builder()
                        .url(URL_FK)
                        .id(LINK_FK_ID)
                        .name(FK_NAME)
                        .build()))
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
        .build();
  }
}
