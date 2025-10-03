package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionNedsattningArbetsformaga.QUESTION_NEDSATTNING_ARBETSFORMAGA_ID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummary;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummaryProvider;

public class AG7804CertificateSummaryProvider implements CertificateSummaryProvider {

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
            data -> QUESTION_NEDSATTNING_ARBETSFORMAGA_ID.equals(data.id())
        )
        .findFirst();

    if (elementData.isEmpty()) {
      return "";
    }

    if (!(elementData.get()
        .value() instanceof ElementValueDateRangeList elementValueDateRangeList)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementData.get().value())
      );
    }

    final var startDate = elementValueDateRangeList.dateRangeList().stream()
        .map(DateRange::from)
        .min(LocalDate::compareTo)
        .orElseThrow(() -> new IllegalStateException("No start date found"))
        .format(DateTimeFormatter.ISO_LOCAL_DATE);

    final var endDate = elementValueDateRangeList.dateRangeList().stream()
        .map(DateRange::to)
        .max(LocalDate::compareTo)
        .orElseThrow(() -> new IllegalStateException("No end date found"))
        .format(DateTimeFormatter.ISO_LOCAL_DATE);

    return startDate + " - "
        + endDate;
  }
}
