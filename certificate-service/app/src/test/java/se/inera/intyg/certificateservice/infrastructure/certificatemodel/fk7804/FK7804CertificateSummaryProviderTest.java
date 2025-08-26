package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionNedsattningArbetsformaga.QUESTION_NEDSATTNING_ARBETSFORMAGA_ID;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummary;

class FK7804CertificateSummaryProviderTest {

  private static final LocalDateTime SIGNED = LocalDateTime.now();
  private static final LocalDate FROM = LocalDate.now();
  private static final LocalDate TO = LocalDate.now().plusMonths(1);

  @Test
  void shouldIncludeCertificateSummary() {
    final var elementValue = ElementValueDateRangeList.builder()
        .dateRangeList(List.of(
            DateRange.builder().from(FROM).to(TO).build()
        ))
        .build();
    final var certificate = MedicalCertificate.builder()
        .signed(SIGNED)
        .elementData(List.of(ElementData.builder()
            .id(QUESTION_NEDSATTNING_ARBETSFORMAGA_ID)
            .value(elementValue)
            .build()))
        .build();

    final var expected = CertificateSummary.builder()
        .label("Gäller intygsperiod")
        .value(FROM.format(DateTimeFormatter.ISO_LOCAL_DATE) + " - " + TO.format(
            DateTimeFormatter.ISO_LOCAL_DATE))
        .build();

    assertEquals(expected, new FK7804CertificateSummaryProvider().summaryOf(certificate));
  }

  @Test
  void shouldIncludeSummaryWithEarliestFromAndLatestTo() {
    final var elementValue = ElementValueDateRangeList.builder()
        .dateRangeList(List.of(
            DateRange.builder().from(FROM).to(TO).build(),
            DateRange.builder().from(FROM.minusDays(5)).to(TO.plusDays(5)).build()
        ))
        .build();
    final var certificate = MedicalCertificate.builder()
        .signed(SIGNED)
        .elementData(List.of(ElementData.builder()
            .id(QUESTION_NEDSATTNING_ARBETSFORMAGA_ID)
            .value(elementValue)
            .build()))
        .build();

    final var expected = CertificateSummary.builder()
        .label("Gäller intygsperiod")
        .value(FROM.minusDays(5).format(DateTimeFormatter.ISO_LOCAL_DATE) + " - " + TO.plusDays(
            5).format(DateTimeFormatter.ISO_LOCAL_DATE))
        .build();

    assertEquals(expected, new FK7804CertificateSummaryProvider().summaryOf(certificate));
  }

  @Test
  void shouldIncludeSummaryWithEarliestFromAndLatestToEvenIfTwoDifferentDateRanges() {
    final var elementValue = ElementValueDateRangeList.builder()
        .dateRangeList(List.of(
            DateRange.builder().from(FROM).to(TO.plusDays(10)).build(),
            DateRange.builder().from(FROM.minusDays(5)).to(TO.plusDays(5)).build(),
            DateRange.builder().from(FROM.minusDays(10)).to(TO).build()
        ))
        .build();
    final var certificate = MedicalCertificate.builder()
        .signed(SIGNED)
        .elementData(List.of(ElementData.builder()
            .id(QUESTION_NEDSATTNING_ARBETSFORMAGA_ID)
            .value(elementValue)
            .build()))
        .build();

    final var expected = CertificateSummary.builder()
        .label("Gäller intygsperiod")
        .value(FROM.minusDays(10).format(DateTimeFormatter.ISO_LOCAL_DATE) + " - " + TO.plusDays(
            10).format(DateTimeFormatter.ISO_LOCAL_DATE))
        .build();

    assertEquals(expected, new FK7804CertificateSummaryProvider().summaryOf(certificate));
  }
}