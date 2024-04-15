package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7211_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7211_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7211_PDF_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7211_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7211_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7443_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7443_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7443_PDF_PATH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7443_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7443_VERSION;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;

public class TestDataCertificateModel {

  public static final CertificateModel FK7211_CERTIFICATE_MODEL = certificateModelBuilder().build();

  public static CertificateModel.CertificateModelBuilder certificateModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(FK7211_TYPE)
                .version(FK7211_VERSION)
                .build()
        )
        .name(FK7211_NAME)
        .type(FK7211_CODE_TYPE)
        .pdfTemplatePath(FK7211_PDF_PATH);
  }

  public static final CertificateModel FK7443_CERTIFICATE_MODEL = certificateFk7443ModelBuilder().build();

  public static CertificateModel.CertificateModelBuilder certificateFk7443ModelBuilder() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(FK7443_TYPE)
                .version(FK7443_VERSION)
                .build()
        )
        .name(FK7443_NAME)
        .type(FK7443_CODE_TYPE)
        .pdfTemplatePath(FK7443_PDF_PATH);
  }

}
