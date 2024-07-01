package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.DIAGNOS_1;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummary;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummaryProvider;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.DiagnosisSummaryValue;

public class FK7809CertificateSummaryProvider implements CertificateSummaryProvider {

  @Override
  public CertificateSummary summaryOf(Certificate certificate) {
    return CertificateSummary.builder()
        .label("Avser diagnos")
        .value(
            certificate.signed() != null
                ? DiagnosisSummaryValue.value(DIAGNOSIS_ID, DIAGNOS_1, certificate)
                : "")
        .build();
  }
}
