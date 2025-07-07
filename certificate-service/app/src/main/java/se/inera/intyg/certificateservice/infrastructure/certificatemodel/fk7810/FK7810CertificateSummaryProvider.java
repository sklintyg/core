package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810;


import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionDiagnos.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionDiagnos.DIAGNOS_1;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummary;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummaryProvider;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.DiagnosisSummaryValue;

public class FK7810CertificateSummaryProvider implements CertificateSummaryProvider {

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
