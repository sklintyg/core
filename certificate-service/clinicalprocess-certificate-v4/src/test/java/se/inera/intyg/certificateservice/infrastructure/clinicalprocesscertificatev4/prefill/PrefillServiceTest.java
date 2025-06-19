package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

@ExtendWith(MockitoExtension.class)
class PrefillServiceTest {

  @Mock
  private PrefillHandler prefillHandler;

  @InjectMocks
  private PrefillService prefillService;

  @Test
  void shouldReturnEmptySetIfPrefillXmlIsNull() {
    final var certificateModel = mock(CertificateModel.class);

    final var result = prefillService.prefill(certificateModel, null);

    assertTrue(result.isEmpty());
  }

  @Test
  void shouldReturnEmptySetIfUnmarshalledPrefillIsEmpty() {
    final var certificateModel = mock(CertificateModel.class);
    final var xml = mock(Xml.class);
    when(xml.decode()).thenReturn("xmlstring");

    mockStatic(PrefillUnmarshaller.class).when(
        () -> PrefillUnmarshaller.forifyllnadType("xmlstring")
    ).thenReturn(Optional.empty());

    final var result = prefillService.prefill(certificateModel, xml);

    assertTrue(result.isEmpty());
  }
}
