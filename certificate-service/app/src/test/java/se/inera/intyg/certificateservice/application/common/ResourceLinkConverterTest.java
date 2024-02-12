package se.inera.intyg.certificateservice.application.common;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

class ResourceLinkConverterTest {

  private static final String NAME = "Skapa intyg";
  private static final String DESCRIPTION = "Skapa ett intygsutkast";
  private ResourceLinkConverter resourceLinkConverter;

  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION_CREATE =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.CREATE)
          .build();
  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION_READ =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.READ)
          .build();

  @BeforeEach
  void setUp() {
    resourceLinkConverter = new ResourceLinkConverter();
  }

  @Nested
  class ConvertCertificateActionCreate {

    @Test
    void shallConvertTypeCreate() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .type(ResourceLinkTypeDTO.CREATE_CERTIFICATE)
          .build();

      final var certificateActionCreate = CertificateActionFactory.create(
          CERTIFICATE_ACTION_SPECIFICATION_CREATE);

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate);
      assertEquals(resourceLinkDTO.getType(), actualResult.getType());
    }

    @Test
    void shallConvertTypeRead() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .type(ResourceLinkTypeDTO.READ_CERTIFICATE)
          .build();

      final var certificateActionCreate = CertificateActionFactory.create(
          CERTIFICATE_ACTION_SPECIFICATION_READ);

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate);
      assertEquals(resourceLinkDTO.getType(), actualResult.getType());
    }

    @Test
    void shallConvertName() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .name(NAME)
          .build();

      final var certificateActionCreate = CertificateActionFactory.create(
          CERTIFICATE_ACTION_SPECIFICATION_CREATE);

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate);
      assertEquals(resourceLinkDTO.getName(), actualResult.getName());
    }

    @Test
    void shallConvertDescription() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .description(DESCRIPTION)
          .build();

      final var certificateActionCreate = CertificateActionFactory.create(
          CERTIFICATE_ACTION_SPECIFICATION_CREATE);

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate);
      assertEquals(resourceLinkDTO.getDescription(), actualResult.getDescription());
    }

    @Test
    void shallConvertEnabled() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .enabled(true)
          .build();

      final var certificateActionCreate = CertificateActionFactory.create(
          CERTIFICATE_ACTION_SPECIFICATION_CREATE);

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

      final var certificateActionCreate = CertificateActionFactory.create(
          CERTIFICATE_ACTION_SPECIFICATION_CREATE);

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate);
      assertEquals(resourceLinkDTO, actualResult);
    }
  }
}
