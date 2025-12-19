package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.AG114_TYPE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.AG114_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.AG7804_TYPE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3221_TYPE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3221_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_TYPE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_TYPE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7426_TYPE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7426_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7427_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7427_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7427_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7427_TYPE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7427_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_TYPE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7804_TYPE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7804_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7809_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7809_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7809_SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7809_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7809_TYPE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7809_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7810_TYPE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7810_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK_RECIPIENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecification.FK3226_PDF_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecification.FK7210_PDF_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecification.FK7472_PDF_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecification.FK7809_PDF_SPECIFICATION;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;

public class TestDataCertificateModel {

  public static final CertificateModel FK7210_CERTIFICATE_MODEL = fk7210certificateModelBuilder().build();
  public static final CertificateModel FK3226_CERTIFICATE_MODEL = fk3226certificateModelBuilder().build();
  public static final CertificateModel FK7472_CERTIFICATE_MODEL = fk7472certificateModelBuilder().build();
  public static final CertificateModel FK7809_CERTIFICATE_MODEL = fk7809certificateModelBuilder().build();
  public static final CertificateModel FK7427_CERTIFICATE_MODEL = fk7427certificateModelBuilder().build();
  public static final CertificateModel FK7426_CERTIFICATE_MODEL = fk7426certificateModelBuilder().build();
  public static final CertificateModel FK3221_CERTIFICATE_MODEL = fk3221certificateModelBuilder().build();
  public static final CertificateModel FK7810_CERTIFICATE_MODEL = fk7810certificateModelBuilder().build();
  public static final CertificateModel FK7804_CERTIFICATE_MODEL = fk7804certificateModelBuilder().build();
  public static final CertificateModel AG7804_CERTIFICATE_MODEL = ag7804certificateModelBuilder().build();
  public static final CertificateModel AG114_CERTIFICATE_MODEL = ag114certificateModelBuilder().build();
  public static final CertificateModelId FK7804_CERTIFICATE_MODEL_ID = CertificateModelId.builder()
      .type(TestDataCertificateModelConstants.FK7804_TYPE)
      .version(FK7804_VERSION)
      .build();

  public static CertificateModel.CertificateModelBuilder fk7210certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(FK7210_TYPE)
                .version(FK7210_VERSION)
                .build()
        )
        .name(FK7210_NAME)
        .type(FK7210_CODE_TYPE)
        .typeName(FK7210_TYPE_NAME)
        .availableForCitizen(true)
        .certificateActionSpecifications(Collections.emptyList())
        .schematronPath(FK7210_SCHEMATRON_PATH)
        .recipient(FK_RECIPIENT)
        .pdfSpecification(FK7210_PDF_SPECIFICATION)
        .certificateActionFactory(new CertificateActionFactory(null))
        .activeFrom(LocalDateTime.now().minusDays(1));
  }

  public static CertificateModel.CertificateModelBuilder fk3226certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(FK3226_TYPE)
                .version(FK3226_VERSION)
                .build()
        )
        .certificateActionSpecifications(Collections.emptyList())
        .name(FK3226_NAME)
        .type(FK3226_CODE_TYPE)
        .typeName(FK3226_TYPE_NAME)
        .availableForCitizen(true)
        .schematronPath(FK3226_SCHEMATRON_PATH)
        .recipient(FK_RECIPIENT)
        .pdfSpecification(FK3226_PDF_SPECIFICATION)
        .certificateActionFactory(new CertificateActionFactory(null))
        .activeFrom(LocalDateTime.now().minusDays(1));
  }


  public static CertificateModel.CertificateModelBuilder fk7472certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(FK7472_TYPE)
                .version(FK7472_VERSION)
                .build()
        )
        .name(FK7472_NAME)
        .certificateActionSpecifications(Collections.emptyList())
        .type(FK7472_CODE_TYPE)
        .typeName(FK7472_TYPE_NAME)
        .availableForCitizen(false)
        .schematronPath(FK7472_SCHEMATRON_PATH)
        .recipient(FK_RECIPIENT)
        .pdfSpecification(FK7472_PDF_SPECIFICATION)
        .certificateActionFactory(new CertificateActionFactory(null))
        .activeFrom(LocalDateTime.now().minusDays(1));
  }

  public static CertificateModel.CertificateModelBuilder fk7809certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(FK7809_TYPE)
                .version(FK7809_VERSION)
                .build()
        )
        .certificateActionSpecifications(Collections.emptyList())
        .name(FK7809_NAME)
        .type(FK7809_CODE_TYPE)
        .typeName(FK7809_TYPE_NAME)
        .availableForCitizen(true)
        .schematronPath(FK7809_SCHEMATRON_PATH)
        .pdfSpecification(FK7809_PDF_SPECIFICATION)
        .recipient(FK_RECIPIENT)
        .certificateActionFactory(new CertificateActionFactory(null))
        .activeFrom(LocalDateTime.now().minusDays(1));
  }

  public static CertificateModel.CertificateModelBuilder fk7427certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(FK7427_TYPE)
                .version(FK7427_VERSION)
                .build()
        )
        .certificateActionSpecifications(Collections.emptyList())
        .name(FK7427_NAME)
        .type(FK7427_CODE_TYPE)
        .typeName(FK7427_TYPE_NAME)
        .availableForCitizen(false)
        .recipient(FK_RECIPIENT)
        .certificateActionFactory(new CertificateActionFactory(null))
        .activeFrom(LocalDateTime.now().minusDays(1));
  }

  public static CertificateModel.CertificateModelBuilder fk7426certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(TestDataCertificateModelConstants.FK7426_TYPE)
                .version(FK7426_VERSION)
                .build()
        )
        .certificateActionSpecifications(Collections.emptyList())
        .name(TestDataCertificateModelConstants.FK7426_NAME)
        .type(TestDataCertificateModelConstants.FK7426_CODE_TYPE)
        .typeName(FK7426_TYPE_NAME)
        .availableForCitizen(false)
        .schematronPath(TestDataCertificateModelConstants.FK7426_SCHEMATRON_PATH)
        .recipient(TestDataCertificateModelConstants.FK_RECIPIENT)
        .certificateActionFactory(new CertificateActionFactory(null))
        .activeFrom(LocalDateTime.now().minusDays(1));
  }

  public static CertificateModel.CertificateModelBuilder fk3221certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(TestDataCertificateModelConstants.FK3221_TYPE)
                .version(FK3221_VERSION)
                .build()
        )
        .certificateActionSpecifications(Collections.emptyList())
        .name(TestDataCertificateModelConstants.FK3221_NAME)
        .type(TestDataCertificateModelConstants.FK3221_CODE_TYPE)
        .typeName(FK3221_TYPE_NAME)
        .availableForCitizen(false)
        .schematronPath(TestDataCertificateModelConstants.FK3221_SCHEMATRON_PATH)
        .recipient(TestDataCertificateModelConstants.FK_RECIPIENT)
        .certificateActionFactory(new CertificateActionFactory(null))
        .activeFrom(LocalDateTime.now().minusDays(1));
  }

  public static CertificateModel.CertificateModelBuilder fk7810certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(TestDataCertificateModelConstants.FK7810_TYPE)
                .version(FK7810_VERSION)
                .build()
        )
        .certificateActionSpecifications(Collections.emptyList())
        .name(TestDataCertificateModelConstants.FK7810_NAME)
        .type(TestDataCertificateModelConstants.FK7810_CODE_TYPE)
        .typeName(FK7810_TYPE_NAME)
        .availableForCitizen(true)
        .schematronPath(TestDataCertificateModelConstants.FK7810_SCHEMATRON_PATH)
        .recipient(TestDataCertificateModelConstants.FK_RECIPIENT)
        .certificateActionFactory(new CertificateActionFactory(null))
        .messageTypes(
            List.of(
                CertificateMessageType.builder()
                    .type(MessageType.CONTACT)
                    .subject(new Subject(MessageType.CONTACT.displayName()))
                    .build()
            )
        )
        .activeFrom(LocalDateTime.now().minusDays(1));
  }

  public static CertificateModel.CertificateModelBuilder fk7804certificateModelBuilder() {
    return CertificateModel.builder()
        .id(FK7804_CERTIFICATE_MODEL_ID)
        .certificateActionSpecifications(Collections.emptyList())
        .name(TestDataCertificateModelConstants.FK7804_NAME)
        .type(TestDataCertificateModelConstants.FK7804_CODE_TYPE)
        .typeName(FK7804_TYPE_NAME)
        .availableForCitizen(true)
        .schematronPath(TestDataCertificateModelConstants.FK7804_SCHEMATRON_PATH)
        .recipient(TestDataCertificateModelConstants.FK_RECIPIENT)
        .certificateActionFactory(new CertificateActionFactory(null))
        .activeFrom(LocalDateTime.now().minusDays(1));
  }

  public static CertificateModel.CertificateModelBuilder ag7804certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(TestDataCertificateModelConstants.AG7804_TYPE)
                .version(TestDataCertificateModelConstants.AG7804_VERSION)
                .build()
        )
        .certificateActionSpecifications(Collections.emptyList())
        .name(TestDataCertificateModelConstants.AG7804_NAME)
        .type(TestDataCertificateModelConstants.AG7804_CODE_TYPE)
        .typeName(AG7804_TYPE_NAME)
        .availableForCitizen(true)
        .ableToCreateDraftForModel(FK7804_CERTIFICATE_MODEL_ID)
        .recipient(TestDataCertificateModelConstants.SKR_RECIPIENT)
        .elementSpecifications(
            List.of(
                ElementSpecification.builder()
                    .id(new ElementId("28"))
                    .configuration(
                        ElementConfigurationCheckboxMultipleCode.builder()
                            .list(
                                List.of(
                                    new ElementConfigurationCode(new FieldId("NUVARANDE_ARBETE"),
                                        "Nuvarande arbete",
                                        new Code("NUVARANDE_ARBETE", "CS", "Nuvarande arbete"))
                                )
                            )
                            .build()
                    )
                    .build()
            )
        )
        .certificateActionFactory(new CertificateActionFactory(null))
        .activeFrom(LocalDateTime.now().minusDays(1));
  }

  public static CertificateModel.CertificateModelBuilder ag114certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(TestDataCertificateModelConstants.AG114_TYPE)
                .version(AG114_VERSION)
                .build()
        )
        .certificateActionSpecifications(Collections.emptyList())
        .name(TestDataCertificateModelConstants.AG114_NAME)
        .type(TestDataCertificateModelConstants.AG114_CODE_TYPE)
        .typeName(AG114_TYPE_NAME)
        .availableForCitizen(true)
        .recipient(TestDataCertificateModelConstants.SKR_RECIPIENT)
        .certificateActionFactory(new CertificateActionFactory(null))
        .activeFrom(LocalDateTime.now().minusDays(1));
  }
}