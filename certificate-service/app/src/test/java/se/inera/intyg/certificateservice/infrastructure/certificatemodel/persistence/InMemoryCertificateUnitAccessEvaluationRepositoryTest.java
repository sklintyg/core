package se.inera.intyg.certificateservice.infrastructure.certificatemodel.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.unitaccess.dto.CertificateAccessConfiguration;
import se.inera.intyg.certificateservice.infrastructure.configuration.UnitAccessConfiguration;
import se.inera.intyg.certificateservice.infrastructure.unitaccess.InMemoryCertificateUnitAccessEvaluationRepository;

@ExtendWith(MockitoExtension.class)
class InMemoryCertificateUnitAccessEvaluationRepositoryTest {

  private static final String TYPE = "type";
  @Mock
  UnitAccessConfiguration unitAccessConfiguration;
  @InjectMocks
  InMemoryCertificateUnitAccessEvaluationRepository inMemoryCertificateUnitAccessEvaluationRepository;
  private CertificateModel.CertificateModelBuilder certificateModelBuilder;

  @BeforeEach
  void setUp() {
    this.certificateModelBuilder = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(new CertificateType(TYPE))
                .build()
        );
  }

  @Test
  void shallReturnEmptyConfigurationIfNoConfigurationIsFound() {
    final var certificateModel = certificateModelBuilder.build();

    doReturn(Collections.emptyList()).when(unitAccessConfiguration).get();

    final var result = inMemoryCertificateUnitAccessEvaluationRepository.get(
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

    final var result = inMemoryCertificateUnitAccessEvaluationRepository.get(
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

    final var result = inMemoryCertificateUnitAccessEvaluationRepository.get(
        certificateModel.id().type()
    );

    assertTrue(result.isEmpty());
  }
}
