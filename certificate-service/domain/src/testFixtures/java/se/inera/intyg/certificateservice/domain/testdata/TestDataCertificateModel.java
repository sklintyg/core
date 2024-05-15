package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_PDF_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_RECIPIENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7443_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7443_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7443_PDF_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7443_SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7443_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7443_VERSION;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;

public class TestDataCertificateModel {

  public static final CertificateModel FK7210_CERTIFICATE_MODEL = fk7210certificateModelBuilder().build();
  public static final CertificateModel FK7443_CERTIFICATE_MODEL = fk7443certificateModelBuilder().build();

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


  public static CertificateModel.CertificateModelBuilder fk7443certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(FK7443_TYPE)
                .version(FK7443_VERSION)
                .build()
        )
        .name(FK7443_NAME)
        .type(FK7443_CODE_TYPE)
        .availableForCitizen(false)
        .pdfTemplatePath(FK7443_PDF_PATH)
        .schematronPath(FK7443_SCHEMATRON_PATH);
  }
}
