package se.inera.intyg.certificateservice.application.citizen.service.converter;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateLinkDTO;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateTextTypeDTO;
import se.inera.intyg.certificateservice.domain.common.model.CertificateLink;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;

@ExtendWith(MockitoExtension.class)
class CertificateTextConverterTest {

  @InjectMocks
  CertificateTextConverter certificateTextConverter;

  private static final String TEXT = "text";
  private static final String NAME = "name";
  private static final String ID = "id";
  private static final String URL = "url";
  private static final CertificateText CERTIFICATE_TEXT = CertificateText.builder()
      .text(TEXT)
      .type(CertificateTextType.PREAMBLE_TEXT)
      .links(List.of(CertificateLink.builder()
          .name(NAME)
          .id(ID)
          .url(URL)
          .build()))
      .build();

  @Test
  void shouldThrowIllegalArgumentExceptionIfTextTypeIsNull() {
    final var text = CertificateText.builder()
        .text(TEXT)
        .links(List.of(CertificateLink.builder()
            .name(NAME)
            .id(ID)
            .url(URL)
            .build()))
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> certificateTextConverter.convert(text));
  }

  @Test
  void shouldConvertText() {
    final var result = certificateTextConverter.convert(CERTIFICATE_TEXT);

    assertEquals(TEXT, result.getText());
  }

  @Test
  void shouldConvertType() {
    final var result = certificateTextConverter.convert(CERTIFICATE_TEXT);

    assertEquals(CertificateTextTypeDTO.PREAMBLE_TEXT, result.getType());
  }

  @Test
  void shouldConvertLinks() {
    final var expected = List.of(CertificateLinkDTO.builder()
        .name(NAME)
        .id(ID)
        .url(URL)
        .build()
    );

    final var result = certificateTextConverter.convert(CERTIFICATE_TEXT);

    assertEquals(expected, result.getLinks());
  }
}