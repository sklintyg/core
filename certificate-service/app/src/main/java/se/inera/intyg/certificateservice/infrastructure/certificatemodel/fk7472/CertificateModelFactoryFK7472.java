package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.CategoryPeriod.categoryPeriod;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.CategorySymptom.categorySymptom;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.QuestionPeriod.questionPeriod;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.QuestionSymptom.questionSymptom;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfMcid;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;

@Component
public class CertificateModelFactoryFK7472 implements CertificateModelFactory {

  @Value("${certificate.model.fk7472.v1_0.active.from}")
  private LocalDateTime activeFrom;
  private static final String TYPE = "fk7472";
  private static final String VERSION = "1.0";
  private static final String NAME = "Intyg om tillfällig föräldrapenning";
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
  private static final String DESCRIPTION = """
         När ett barn är sjukt kan den förälder som behöver avstå från sitt arbete för att vårda barnet få tillfällig föräldrapenning. Från och med den åttonde dagen i barnets vårdperiod behöver ett intyg från en läkare eller sjuksköterska skickas till Försäkringskassan.                                                                                                                                                         \s
      """;
  public static final CertificateModelId FK7472_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(TYPE))
      .version(new CertificateVersion(VERSION))
      .build();

  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk7472/schematron/itfp.v1.sch");

  public static final String PDF_FK_7472_PDF = "fk7472/pdf/fk7472_v1.pdf";
  public static final String PDF_NO_ADDRESS_FK_7472_PDF = "fk7472/pdf/fk7472_v1_no_address.pdf";
  private static final PdfFieldId PDF_PATIENT_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtPersonNrBarn[0]");
  private static final PdfMcid PDF_PDF_MCID = new PdfMcid(120);
  private static final PdfTagIndex PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX = new PdfTagIndex(50);
  private static final PdfTagIndex PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX = new PdfTagIndex(42);
  private static final int PDF_SIGNATURE_PAGE_INDEX = 0;
  private static final PdfFieldId PDF_SIGNED_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_datUnderskrift[0]");
  private static final PdfFieldId PDF_SIGNED_BY_NAME_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtNamnfortydligande[0]");
  private static final PdfFieldId PDF_PA_TITLE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtBefattning[0]");
  private static final PdfFieldId PDF_SPECIALTY_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtEventuellSpecialistkompetens[0]");
  private static final PdfFieldId PDF_HSA_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtLakarensHSA-ID[0]");
  private static final PdfFieldId PDF_WORKPLACE_CODE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtArbetsplatskod[0]");
  private static final PdfFieldId PDF_CONTACT_INFORMATION = new PdfFieldId(
      "form1[0].#subform[0].flt_txtVardgivarensNamnAdressTelefon[0]");

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
        .detailedDescription(DETAILED_DESCRIPTION)
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
                    .allowedRoles(
                        List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.SEND)
                    .allowedRoles(
                        List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.PRINT)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.REVOKE)
                    .allowedRoles(
                        List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
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
                    .certificateActionType(CertificateActionType.SEND_AFTER_SIGN)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.MESSAGES)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.MESSAGES_ADMINISTRATIVE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.RECEIVE_COMPLEMENT)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.RECEIVE_REMINDER)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.COMPLEMENT)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.CANNOT_COMPLEMENT)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.FORWARD_MESSAGE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.HANDLE_COMPLEMENT)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.RESPONSIBLE_ISSUER)
                    .allowedRoles(List.of(Role.CARE_ADMIN))
                    .build()
            )
        )
        .messageActionSpecifications(
            List.of(
                MessageActionSpecification.builder()
                    .messageActionType(MessageActionType.HANDLE_COMPLEMENT)
                    .build(),
                MessageActionSpecification.builder()
                    .messageActionType(MessageActionType.COMPLEMENT)
                    .build(),
                MessageActionSpecification.builder()
                    .messageActionType(MessageActionType.CANNOT_COMPLEMENT)
                    .build(),
                MessageActionSpecification.builder()
                    .messageActionType(MessageActionType.FORWARD)
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
        .schematronPath(SCHEMATRON_PATH)
        .pdfSpecification(PdfSpecification.builder()
            .pdfTemplatePath(PDF_FK_7472_PDF)
            .pdfNoAddressTemplatePath(PDF_NO_ADDRESS_FK_7472_PDF)
            .patientIdFieldId(PDF_PATIENT_ID_FIELD_ID)
            .signature(PdfSignature.builder()
                .signaturePageIndex(PDF_SIGNATURE_PAGE_INDEX)
                .signatureWithAddressTagIndex(PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX)
                .signatureWithoutAddressTagIndex(PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX)
                .signedDateFieldId(PDF_SIGNED_DATE_FIELD_ID)
                .signedByNameFieldId(PDF_SIGNED_BY_NAME_FIELD_ID)
                .paTitleFieldId(PDF_PA_TITLE_FIELD_ID)
                .specialtyFieldId(PDF_SPECIALTY_FIELD_ID)
                .hsaIdFieldId(PDF_HSA_ID_FIELD_ID)
                .workplaceCodeFieldId(PDF_WORKPLACE_CODE_FIELD_ID)
                .contactInformation(PDF_CONTACT_INFORMATION)
                .build())
            .pdfMcid(PDF_PDF_MCID)
            .build())
        .build();
  }
}
