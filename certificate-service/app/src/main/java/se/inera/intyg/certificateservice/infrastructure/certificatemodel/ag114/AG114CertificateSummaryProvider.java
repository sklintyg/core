package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodBedomning.QUESTION_PERIOD_BEDOMNING_ID;

import java.time.format.DateTimeFormatter;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummary;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummaryProvider;

public class AG114CertificateSummaryProvider implements CertificateSummaryProvider {

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

    final var elementData = certificate.elementData()
        .stream()
        .filter(
            data -> QUESTION_PERIOD_BEDOMNING_ID.equals(data.id())
        )
        .findFirst();

    if (elementData.isEmpty()) {
      return "";
    }

    if (!(elementData.get()
        .value() instanceof ElementValueDateRange elementValueDateRange)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementData.get().value())
      );
    }

    if (elementValueDateRange.fromDate() == null ||
        elementValueDateRange.toDate() == null) {
      return "";
    }

    final var startDate = elementValueDateRange.fromDate()
        .format(DateTimeFormatter.ISO_LOCAL_DATE);
    final var endDate = elementValueDateRange.toDate()
        .format(DateTimeFormatter.ISO_LOCAL_DATE);

    return String.format("%s - %s", startDate, endDate);
  }
}
