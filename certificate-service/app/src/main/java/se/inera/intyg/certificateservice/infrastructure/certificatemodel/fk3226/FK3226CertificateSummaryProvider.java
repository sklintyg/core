package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.DIAGNOS_1;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummary;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummaryProvider;

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

    final var elementDataDiagnosis = certificate.elementData()
        .stream()
        .filter(elementData -> DIAGNOSIS_ID.equals(elementData.id()))
        .findFirst();

    if (elementDataDiagnosis.isEmpty()) {
      return "";
    }

    if (!(elementDataDiagnosis.get()
        .value() instanceof ElementValueDiagnosisList elementValueDiagnosisList)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementDataDiagnosis.get().value())
      );
    }

    return elementValueDiagnosisList.diagnoses()
        .stream()
        .filter(elementValueDiagnosis -> elementValueDiagnosis.id().equals(DIAGNOS_1))
        .findFirst()
        .map(ElementValueDiagnosis::code)
        .orElse("");
  }
}
