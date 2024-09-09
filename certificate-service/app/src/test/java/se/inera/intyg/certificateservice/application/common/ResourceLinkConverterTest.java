package se.inera.intyg.certificateservice.application.common;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;

import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionCreate;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionSend;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

class ResourceLinkConverterTest {

  private static final String NAME = "Skapa intyg";
  private static final String DESCRIPTION = "Skapa ett intygsutkast.";
  private static final String BODY = "<p>Om du går vidare kommer intyget skickas direkt till Försäkringskassans system vilket ska göras i samråd med patienten.</p>";
  private ResourceLinkConverter resourceLinkConverter;

  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION_CREATE =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.CREATE)
          .build();
  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION_READ =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.READ)
          .build();

  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION_SEND =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.SEND)
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

      final var certificateActionCreate = CertificateActionCreate.builder()
          .certificateActionSpecification(CERTIFICATE_ACTION_SPECIFICATION_CREATE)
          .actionRules(Collections.emptyList())
          .build();

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate,
          Optional.empty(), ACTION_EVALUATION);
      assertEquals(resourceLinkDTO.getType(), actualResult.getType());
    }

    @Test
    void shallConvertTypeRead() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .type(ResourceLinkTypeDTO.READ_CERTIFICATE)
          .build();

      final var certificateActionRead = CertificateActionCreate.builder()
          .certificateActionSpecification(CERTIFICATE_ACTION_SPECIFICATION_READ)
          .actionRules(Collections.emptyList())
          .build();

      final var actualResult = resourceLinkConverter.convert(certificateActionRead,
          Optional.of(FK7210_CERTIFICATE), ACTION_EVALUATION);
      assertEquals(resourceLinkDTO.getType(), actualResult.getType());
    }

    @Test
    void shallConvertName() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .name(NAME)
          .build();

      final var certificateActionCreate = CertificateActionCreate.builder()
          .certificateActionSpecification(CERTIFICATE_ACTION_SPECIFICATION_CREATE)
          .actionRules(Collections.emptyList())
          .build();

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate,
          Optional.empty(), ACTION_EVALUATION);
      assertEquals(resourceLinkDTO.getName(), actualResult.getName());
    }

    @Test
    void shallConvertDescription() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .description(DESCRIPTION)
          .build();

      final var certificateActionCreate = CertificateActionCreate.builder()
          .certificateActionSpecification(CERTIFICATE_ACTION_SPECIFICATION_CREATE)
          .actionRules(Collections.emptyList())
          .build();

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate,
          Optional.of(FK7210_CERTIFICATE), ACTION_EVALUATION);
      assertEquals(resourceLinkDTO.getDescription(), actualResult.getDescription());
    }

    @Test
    void shallConvertBody() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .body(BODY)
          .build();

      final var certificateActionCreate = CertificateActionSend.builder()
          .certificateActionSpecification(CERTIFICATE_ACTION_SPECIFICATION_SEND)
          .actionRules(Collections.emptyList())
          .build();

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate,
          Optional.of(FK7210_CERTIFICATE), ACTION_EVALUATION);
      assertEquals(resourceLinkDTO.getBody(), actualResult.getBody());
    }

    @Test
    void shallConvertEnabled() {
      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .enabled(true)
          .build();

      final var certificateActionCreate = CertificateActionCreate.builder()
          .certificateActionSpecification(CERTIFICATE_ACTION_SPECIFICATION_CREATE)
          .actionRules(Collections.emptyList())
          .build();

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate,
          Optional.empty(), ACTION_EVALUATION);
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

      final var certificateActionCreate = CertificateActionCreate.builder()
          .certificateActionSpecification(CERTIFICATE_ACTION_SPECIFICATION_CREATE)
          .actionRules(Collections.emptyList())
          .build();

      final var actualResult = resourceLinkConverter.convert(certificateActionCreate,
          Optional.empty(), ACTION_EVALUATION);
      assertEquals(resourceLinkDTO, actualResult);
    }
  }
}
