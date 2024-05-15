package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7211;

import java.time.format.DateTimeFormatter;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummary;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummaryProvider;

public class FK7211CertificateSummaryProvider implements CertificateSummaryProvider {

  @Override
  public CertificateSummary summaryOf(Certificate certificate) {
    return CertificateSummary.builder()
        .value(getValue(certificate))
        .label("Gäller intygsperiod")
        .build();
  }

  private String getValue(Certificate certificate) {
    if (certificate.signed() == null) {
      return "";
    }

    final var elementDataDate = certificate.elementData()
        .stream()
        .filter(
            elementData -> elementData.id().id().equals("1")
        )
        .findFirst();

    if (elementDataDate.isEmpty()) {
      return "";
    }

    if (!(elementDataDate.get().value() instanceof ElementValueDate elementValueDate)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementDataDate.get().value())
      );
    }

    return certificate.signed().format(DateTimeFormatter.ISO_DATE) + " - "
        + elementValueDate.date().format(DateTimeFormatter.ISO_DATE);
  }
}
