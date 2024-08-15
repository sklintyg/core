package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateDataConverter;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateMetadataConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@ExtendWith(MockitoExtension.class)
class CertificateConverterTest {

  private final List<ResourceLinkDTO> resourceLinkDTOs = Collections.emptyList();
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  @Mock
  private CertificateMetadataConverter certificateMetadataConverter;
  @Mock
  private CertificateDataConverter certificateDataConverter;
  @InjectMocks
  private CertificateConverter certificateConverter;

  private Certificate certificate;
  private static final String KEY = "key";

  @BeforeEach
  void setUp() {
    certificate = Certificate.builder().build();
  }

  @Nested
  class CertificateMetadataTest {

    @Test
    void shallIncludeMetadata() {
      final var expectedMetadata = CertificateMetadataDTO.builder().build();
      doReturn(expectedMetadata).when(certificateMetadataConverter)
          .convert(certificate, ACTION_EVALUATION);
      final var actualMetadata = certificateMetadataConverter.convert(certificate,
          ACTION_EVALUATION);
      assertEquals(expectedMetadata, actualMetadata);
    }
  }

  @Nested
  class CertificateData {

    @Test
    void shallIncludeData() {
      final var expectedValue = Map.of(KEY, CertificateDataElement.builder().build());

      doReturn(expectedValue).when(certificateDataConverter)
          .convert(certificate);

      assertEquals(expectedValue,
          certificateConverter.convert(certificate, resourceLinkDTOs, ACTION_EVALUATION).getData());
    }
  }

  @Nested
  class CertificateResourceLinks {

    @Test
    void shallIncludeLinks() {
      final var resourceLinkDTO = ResourceLinkDTO.builder().build();
      final var expectedLinks = List.of(resourceLinkDTO);
      assertEquals(expectedLinks,
          certificateConverter.convert(certificate, expectedLinks, ACTION_EVALUATION).getLinks());
    }
  }
}
