package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7804CertificateBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@ExtendWith(MockitoExtension.class)
class AG7804UpdateDraftFromCertificateContentProviderTest {

  @Mock
  private Certificate certificate;

  @InjectMocks
  private AG7804UpdateDraftFromCertificateContentProvider provider;

  @Test
  void shallReturnBodyBasedOnCandidateCertificate() {
    final var expected = LocalDate.now();
    when(certificate.candidateForUpdate()).thenReturn(
        Optional.of(
            fk7804CertificateBuilder()
                .signed(expected.atTime(LocalTime.now()))
                .build()
        )
    );

    final var actual = provider.body(certificate);
    assertEquals(
        """
            <p>Det finns ett Läkarintyg för sjukpenning för denna patient som är utfärdat <span class='iu-fw-bold'>%s</span> på en enhet som du har åtkomst till. Vill du kopiera de svar som givits i det intyget till detta intygsutkast?</p>
            """.formatted(expected),
        actual
    );
  }

  @Test
  void shallReturnBodyIfNoCandidateCertificate() {
    final var actual = provider.body(certificate);
    assertNull(actual);
  }

  @Test
  void shallReturnNullBodyIfCertificateNull() {
    final var actual = provider.body(null);
    assertNull(actual);
  }

  @Test
  void shallReturnName() {
    final var expected = "Hjälp med ifyllnad?";
    final var actual = provider.name(certificate);
    assertEquals(expected, actual);
  }
}