package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;

class FK7211CertificateSummaryProviderTest {

  private static final LocalDateTime SIGNED = LocalDateTime.now();
  private static final LocalDate DATE = LocalDate.now().plusMonths(1);

  @Test
  void shallIncludeCertificateSummary() {
    final var certificate = fk7211CertificateBuilder()
        .signed(SIGNED)
        .elementData(List.of(ElementData.builder()
            .id(new ElementId("1"))
            .value(ElementValueDate.builder()
                .date(DATE)
                .build())
            .build()))
        .build();

    final var certificateSummary = CertificateSummary.builder()
        .label("Gäller intygsperiod")
        .value(SIGNED.format(DateTimeFormatter.ISO_DATE) + " - " + DATE.format(
            DateTimeFormatter.ISO_DATE))
        .build();

    assertEquals(certificateSummary,
        new FK7211CertificateSummaryProvider().summaryOf(certificate)
    );
  }

}