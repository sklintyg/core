package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.model.CertificateActionCreate;

class ResourceLinkConverterTest {

  private static final String NAME = "Skapa intyg";
  private static final String DESCRIPTION = "Skapa ett intygsutkast";
  private ResourceLinkConverter resourceLinkConverter;

  @BeforeEach
  void setUp() {
    resourceLinkConverter = new ResourceLinkConverter();
  }

  @Nested
  class ConvertCertificateActionCreate {

    @Test
    void shallConvertType() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .type(ResourceLinkTypeDTO.CREATE_CERTIFICATE)
          .build();

      final var certificateActionCreate = new CertificateActionCreate();

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate);
      assertEquals(resourceLinkDTO.getType(), actualResult.getType());
    }

    @Test
    void shallConvertName() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .name(NAME)
          .build();

      final var certificateActionCreate = new CertificateActionCreate();

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate);
      assertEquals(resourceLinkDTO.getName(), actualResult.getName());
    }

    @Test
    void shallConvertDescription() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .description(DESCRIPTION)
          .build();

      final var certificateActionCreate = new CertificateActionCreate();

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate);
      assertEquals(resourceLinkDTO.getDescription(), actualResult.getDescription());
    }

    @Test
    void shallConvertEnabled() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .enabled(true)
          .build();

      final var certificateActionCreate = new CertificateActionCreate();

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate);
      assertEquals(resourceLinkDTO.isEnabled(), actualResult.isEnabled());
    }

    @Test
    void shallConvertAllFields() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .name(NAME)
          .description(DESCRIPTION)
          .type(ResourceLinkTypeDTO.CREATE_CERTIFICATE)
          .enabled(true)
          .build();

      final var certificateActionCreate = new CertificateActionCreate();

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate);
      assertEquals(resourceLinkDTO, actualResult);
    }
  }
}
