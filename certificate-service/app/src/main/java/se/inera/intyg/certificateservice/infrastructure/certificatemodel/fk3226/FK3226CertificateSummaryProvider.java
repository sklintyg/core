package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionDiagnos.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionDiagnos.DIAGNOS_1;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummary;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummaryProvider;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.DiagnosisSummaryValue;

public class FK3226CertificateSummaryProvider implements CertificateSummaryProvider {

  @Override
  public CertificateSummary summaryOf(Certificate certificate) {
    return CertificateSummary.builder()
        .label("Avser diagnos")
        .value(getValue(certificate))
        .build();
  }

  private String getValue(Certificate certificate) {
    if (certificate.signed() == null) {
      return "";
    }

    return DiagnosisSummaryValue.value(DIAGNOSIS_ID, DIAGNOS_1, certificate);
  }
}
