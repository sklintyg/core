package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK3226_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7427_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7427_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7427_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7427_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7809_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7809_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7809_SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7809_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7809_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK_RECIPIENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecification.FK3226_PDF_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecification.FK7210_PDF_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecification.FK7472_PDF_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecification.FK7809_PDF_SPECIFICATION;

import java.util.Collections;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;

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
        .availableForCitizen(true)
        .certificateActionSpecifications(Collections.emptyList())
        .schematronPath(FK7210_SCHEMATRON_PATH)
        .recipient(FK_RECIPIENT)
        .pdfSpecification(FK7210_PDF_SPECIFICATION)
        .certificateActionFactory(new CertificateActionFactory(null));
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
        .availableForCitizen(true)
        .schematronPath(FK3226_SCHEMATRON_PATH)
        .recipient(FK_RECIPIENT)
        .pdfSpecification(FK3226_PDF_SPECIFICATION)
        .certificateActionFactory(new CertificateActionFactory(null));
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
        .availableForCitizen(false)
        .schematronPath(FK7472_SCHEMATRON_PATH)
        .recipient(FK_RECIPIENT)
        .pdfSpecification(FK7472_PDF_SPECIFICATION)
        .certificateActionFactory(new CertificateActionFactory(null));
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
        .availableForCitizen(true)
        .schematronPath(FK7809_SCHEMATRON_PATH)
        .pdfSpecification(FK7809_PDF_SPECIFICATION)
        .recipient(FK_RECIPIENT)
        .certificateActionFactory(new CertificateActionFactory(null));
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
        .availableForCitizen(false)
        .recipient(FK_RECIPIENT)
        .certificateActionFactory(new CertificateActionFactory(null));
  }

  public static CertificateModel.CertificateModelBuilder fk7426certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(TestDataCertificateModelConstants.FK7426_TYPE)
                .version(TestDataCertificateModelConstants.FK7426_VERSION)
                .build()
        )
        .certificateActionSpecifications(Collections.emptyList())
        .name(TestDataCertificateModelConstants.FK7426_NAME)
        .type(TestDataCertificateModelConstants.FK7426_CODE_TYPE)
        .availableForCitizen(false)
        .schematronPath(TestDataCertificateModelConstants.FK7426_SCHEMATRON_PATH)
        .recipient(TestDataCertificateModelConstants.FK_RECIPIENT)
        .certificateActionFactory(new CertificateActionFactory(null));
  }

  public static CertificateModel.CertificateModelBuilder fk3221certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(TestDataCertificateModelConstants.FK3221_TYPE)
                .version(TestDataCertificateModelConstants.FK3221_VERSION)
                .build()
        )
        .certificateActionSpecifications(Collections.emptyList())
        .name(TestDataCertificateModelConstants.FK3221_NAME)
        .type(TestDataCertificateModelConstants.FK3221_CODE_TYPE)
        .availableForCitizen(false)
        .schematronPath(TestDataCertificateModelConstants.FK3221_SCHEMATRON_PATH)
        .recipient(TestDataCertificateModelConstants.FK_RECIPIENT)
        .certificateActionFactory(new CertificateActionFactory(null));
  }

  public static CertificateModel.CertificateModelBuilder fk7810certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(TestDataCertificateModelConstants.FK7810_TYPE)
                .version(TestDataCertificateModelConstants.FK7810_VERSION)
                .build()
        )
        .certificateActionSpecifications(Collections.emptyList())
        .name(TestDataCertificateModelConstants.FK7810_NAME)
        .type(TestDataCertificateModelConstants.FK7810_CODE_TYPE)
        .availableForCitizen(true)
        .schematronPath(TestDataCertificateModelConstants.FK7810_SCHEMATRON_PATH)
        .recipient(TestDataCertificateModelConstants.FK_RECIPIENT)
        .certificateActionFactory(new CertificateActionFactory(null));
  }

  public static CertificateModel.CertificateModelBuilder fk7804certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(TestDataCertificateModelConstants.FK7804_TYPE)
                .version(TestDataCertificateModelConstants.FK7804_VERSION)
                .build()
        )
        .certificateActionSpecifications(Collections.emptyList())
        .name(TestDataCertificateModelConstants.FK7804_NAME)
        .type(TestDataCertificateModelConstants.FK7804_CODE_TYPE)
        .availableForCitizen(true)
        .schematronPath(TestDataCertificateModelConstants.FK7804_SCHEMATRON_PATH)
        .recipient(TestDataCertificateModelConstants.FK_RECIPIENT)
        .certificateActionFactory(new CertificateActionFactory(null));
  }
}