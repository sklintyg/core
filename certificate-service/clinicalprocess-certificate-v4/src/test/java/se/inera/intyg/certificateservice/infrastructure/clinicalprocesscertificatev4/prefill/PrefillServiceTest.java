package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@ExtendWith(MockitoExtension.class)
class PrefillServiceTest {

  @Mock
  private PrefillHandler prefillHandler;

  @InjectMocks
  private PrefillService prefillService;

  @Test
  void shouldReturnEmptySetIfPrefillXmlIsNull() {
    final var certificateModel = mock(CertificateModel.class);

    final var result = prefillService.prefill(certificateModel, null, CERTIFICATE_ID);

    assertTrue(result.isEmpty());
  }

  @Test
  void shouldReturnEmptySetIfUnmarshalledPrefillIsEmpty() {
    final var certificateModel = mock(CertificateModel.class);
    final var xml = mock(Xml.class);
    when(xml.decode()).thenReturn("xmlstring");
    try (var mocked = mockStatic(PrefillUnmarshaller.class)) {
      mocked.when(() -> PrefillUnmarshaller.forifyllnadType("xmlstring"))
          .thenReturn(Optional.empty());

      final var result = prefillService.prefill(certificateModel, xml, CERTIFICATE_ID);

      assertTrue(result.isEmpty());
    }
  }

  @Test
  void shouldPrefillAndReturnElementData() {
    final var elementData = Set.of(ElementData.builder().build());
    final var certificateModel = mock(CertificateModel.class);
    final var xml = mock(Xml.class);
    when(xml.decode()).thenReturn("xmlstring");
    final var forifyllnad = mock(Forifyllnad.class);

    try (var mockedUnmarshaller = mockStatic(PrefillUnmarshaller.class)) {
      mockedUnmarshaller.when(() -> PrefillUnmarshaller.forifyllnadType("xmlstring"))
          .thenReturn(Optional.of(forifyllnad));

      final var prefillResult = mock(PrefillResult.class);
      when(prefillResult.prefilledElements()).thenReturn(elementData);

      try (var mockedResult = mockStatic(PrefillResult.class)) {
        mockedResult.when(() -> PrefillResult.create(certificateModel, forifyllnad, prefillHandler))
            .thenReturn(prefillResult);

        final var result = prefillService.prefill(certificateModel, xml, CERTIFICATE_ID);
        verify(prefillResult, times(1)).prefill();

        assertEquals(elementData, result);
      }
    }
  }
}