package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummary;

@ExtendWith(MockitoExtension.class)
class FK3226CertificateSummaryProviderTest {

  @InjectMocks
  private FK3226CertificateSummaryProvider provider;

  @Test
  void shallIncludePlaceholderTextUntilRequirementsAreSet() {
    final var expectedSummary = CertificateSummary.builder()
        .label("Gäller intygsperiod")
        .value("<saknas period>")
        .build();

    assertEquals(expectedSummary,
        provider.summaryOf(Certificate.builder().build())
    );
  }
}