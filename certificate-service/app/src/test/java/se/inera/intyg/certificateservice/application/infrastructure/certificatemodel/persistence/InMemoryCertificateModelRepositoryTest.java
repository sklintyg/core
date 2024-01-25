package se.inera.intyg.certificateservice.application.infrastructure.certificatemodel.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.model.CertificateModel;
import se.inera.intyg.certificateservice.model.CertificateModelId;
import se.inera.intyg.certificateservice.model.CertificateType;
import se.inera.intyg.certificateservice.model.CertificateVersion;

@ExtendWith(MockitoExtension.class)
class InMemoryCertificateModelRepositoryTest {

  @Mock
  private CertificateModelFactory certificateModelFactoryOne;

  @Mock
  private CertificateModelFactory certificateModelFactoryTwo;

  private InMemoryCertificateModelRepository inMemoryCertificateModelRepository;

  @Test
  void shallReturnActiveCertificateModels() {
    inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
        List.of(certificateModelFactoryOne)
    );

    final var expectedModel = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(new CertificateType("type1"))
                .version(new CertificateVersion("version1"))
                .build()
        )
        .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
        .build();

    doReturn(expectedModel).when(certificateModelFactoryOne).create();

    final var actualModels = inMemoryCertificateModelRepository.findAllActive();

    assertEquals(List.of(expectedModel), actualModels);
  }

  @Test
  void shallExcludeInactiveCertificateModels() {
    inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
        List.of(certificateModelFactoryOne)
    );

    final var expectedModel = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(new CertificateType("type1"))
                .version(new CertificateVersion("version1"))
                .build()
        )
        .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(1))
        .build();

    doReturn(expectedModel).when(certificateModelFactoryOne).create();

    final var actualModels = inMemoryCertificateModelRepository.findAllActive();

    assertEquals(Collections.emptyList(), actualModels);
  }

  @Test
  void shallReturnListOfActiveCertificateModels() {
    inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
        List.of(certificateModelFactoryOne, certificateModelFactoryTwo)
    );

    final var expectedModelOne = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(new CertificateType("type1"))
                .version(new CertificateVersion("version1"))
                .build()
        )
        .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
        .build();

    doReturn(expectedModelOne).when(certificateModelFactoryOne).create();

    final var expectedModelTwo = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(new CertificateType("type2"))
                .version(new CertificateVersion("version1"))
                .build()
        )
        .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
        .build();

    doReturn(expectedModelTwo).when(certificateModelFactoryTwo).create();

    final var actualModels = inMemoryCertificateModelRepository.findAllActive();

    assertEquals(2, actualModels.size());
    assertTrue(actualModels.contains(expectedModelOne),
        "Expected model with id: %s".formatted(expectedModelOne.getId())
    );
    assertTrue(actualModels.contains(expectedModelTwo),
        "Expected model with id: %s".formatted(expectedModelTwo.getId())
    );
  }
}