package se.inera.intyg.certificateservice.infrastructure.certificatemodel.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.configuration.limitedcertificatefunctionality.dto.LimitedCertificateFunctionalityConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessConfiguration;
import se.inera.intyg.certificateservice.infrastructure.certificateaction.InMemoryCertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.infrastructure.configuration.GetLimitedCertificateFunctionalityConfiguration;
import se.inera.intyg.certificateservice.infrastructure.configuration.UnitAccessConfiguration;

@ExtendWith(MockitoExtension.class)
class InMemoryCertificateActionConfigurationRepositoryTest {

  private static final String TYPE = "type";
  private static final String VERSION = "1.0";
  @Mock
  GetLimitedCertificateFunctionalityConfiguration getLimitedCertificateFunctionalityConfiguration;
  @Mock
  UnitAccessConfiguration unitAccessConfiguration;
  @InjectMocks
  InMemoryCertificateActionConfigurationRepository inMemoryCertificateActionConfigurationRepository;
  private CertificateModel.CertificateModelBuilder certificateModelBuilder;

  @BeforeEach
  void setUp() {
    this.certificateModelBuilder = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(new CertificateType(TYPE))
                .version(new CertificateVersion(VERSION))
                .build()
        );
  }

  @Nested
  class FindAccessConfigurationTests {

    @Test
    void shallReturnEmptyConfigurationIfNoConfigurationIsFound() {
      final var certificateModel = certificateModelBuilder.build();

      doReturn(Collections.emptyList()).when(unitAccessConfiguration).get();

      final var result = inMemoryCertificateActionConfigurationRepository.findAccessConfiguration(
          certificateModel.id().type()
      );

      assertTrue(result.isEmpty());
    }

    @Test
    void shallReturnConfigurationOfMatchingType() {
      final var certificateModel = certificateModelBuilder
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE))
                  .build()
          )
          .build();

      final var expectedConfiguration = CertificateAccessConfiguration.builder()
          .certificateType(TYPE)
          .build();

      final var certificateUnitAccessConfigurations = List.of(
          expectedConfiguration
      );

      doReturn(certificateUnitAccessConfigurations).when(unitAccessConfiguration).get();

      final var result = inMemoryCertificateActionConfigurationRepository.findAccessConfiguration(
          certificateModel.id().type());

      assertEquals(List.of(expectedConfiguration), result);
    }

    @Test
    void shallReturnEmptyListIfNoConfigurationIsFoundForType() {
      final var certificateModel = certificateModelBuilder
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType("wrongType"))
                  .build()
          )
          .build();
      final var certificateUnitAccessConfigurations = List.of(
          CertificateAccessConfiguration.builder().certificateType(TYPE).build());

      doReturn(certificateUnitAccessConfigurations).when(unitAccessConfiguration).get();

      final var result = inMemoryCertificateActionConfigurationRepository.findAccessConfiguration(
          certificateModel.id().type()
      );

      assertTrue(result.isEmpty());
    }
  }

  @Nested
  class FindInactiveConfigurationTests {

    @Test
    void shallReturnEmptyConfigurationIfNoConfigurationIsFound() {
      final var certificateModel = certificateModelBuilder.build();

      doReturn(Collections.emptyList()).when(getLimitedCertificateFunctionalityConfiguration).get();

      final var result = inMemoryCertificateActionConfigurationRepository.findLimitedCertificateFunctionalityConfiguration(
          certificateModel.id()
      );

      assertNull(result);
    }

    @Test
    void shallReturnConfigurationOfMatchingTypeAndVersion() {
      final var certificateModel = certificateModelBuilder
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE))
                  .version(new CertificateVersion(VERSION))
                  .build()
          )
          .build();

      final var expectedConfiguration = LimitedCertificateFunctionalityConfiguration.builder()
          .certificateType(TYPE)
          .version(List.of(VERSION))
          .build();

      final var certificateInactiveConfigurations = List.of(
          expectedConfiguration
      );

      doReturn(certificateInactiveConfigurations).when(
          getLimitedCertificateFunctionalityConfiguration).get();

      final var result = inMemoryCertificateActionConfigurationRepository.findLimitedCertificateFunctionalityConfiguration(
          certificateModel.id()
      );

      assertEquals(expectedConfiguration, result);
    }

    @Test
    void shallReturnEmptyListIfNoConfigurationIsFoundForTypeWithOtherVersion() {
      final var certificateModel = certificateModelBuilder
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE))
                  .version(new CertificateVersion("2.0"))
                  .build()
          )
          .build();

      final var certificateInactiveConfigurations = List.of(
          LimitedCertificateFunctionalityConfiguration.builder()
              .certificateType(TYPE)
              .version(List.of(VERSION))
              .build()
      );

      doReturn(certificateInactiveConfigurations).when(
          getLimitedCertificateFunctionalityConfiguration).get();

      final var result = inMemoryCertificateActionConfigurationRepository.findLimitedCertificateFunctionalityConfiguration(
          certificateModel.id()
      );

      assertNull(result);
    }
  }
}