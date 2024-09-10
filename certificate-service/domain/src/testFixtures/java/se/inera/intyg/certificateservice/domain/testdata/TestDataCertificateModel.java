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
}
