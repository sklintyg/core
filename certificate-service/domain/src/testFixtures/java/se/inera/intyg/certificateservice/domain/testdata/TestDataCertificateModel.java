package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_PDF_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_RECIPIENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_PDF_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_VERSION;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;

public class TestDataCertificateModel {

  public static final CertificateModel FK7210_CERTIFICATE_MODEL = fk7210certificateModelBuilder().build();
  public static final CertificateModel FK7472_CERTIFICATE_MODEL = fk7472certificateModelBuilder().build();

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
        .pdfTemplatePath(FK7210_PDF_PATH)
        .schematronPath(FK7210_SCHEMATRON_PATH)
        .recipient(FK7210_RECIPIENT);
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
        .type(FK7472_CODE_TYPE)
        .availableForCitizen(false)
        .pdfTemplatePath(FK7472_PDF_PATH)
        .schematronPath(FK7472_SCHEMATRON_PATH);
  }
}
