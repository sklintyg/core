package se.inera.intyg.certificateservice.infrastructure.certificatemodel.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;

@ExtendWith(MockitoExtension.class)
class InMemoryCertificateModelRepositoryTest {

  private static final String TYPE_ONE = "type1";
  private static final String VERSION_ONE = "version1";
  private static final String TYPE_TWO = "type2";
  private static final String VERSION_TWO = "version2";
  @Mock
  private CertificateModelFactory certificateModelFactoryOne;

  @Mock
  private CertificateModelFactory certificateModelFactoryTwo;

  private InMemoryCertificateModelRepository inMemoryCertificateModelRepository;

  @Nested
  class FindAllActive {

    @Test
    void shallReturnActiveCertificateModels() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var expectedModel = CertificateModel.builder()
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE_ONE))
                  .version(new CertificateVersion(VERSION_ONE))
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
                  .type(new CertificateType(TYPE_ONE))
                  .version(new CertificateVersion(VERSION_ONE))
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
                  .type(new CertificateType(TYPE_ONE))
                  .version(new CertificateVersion(VERSION_ONE))
                  .build()
          )
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
          .build();

      doReturn(expectedModelOne).when(certificateModelFactoryOne).create();

      final var expectedModelTwo = CertificateModel.builder()
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE_TWO))
                  .version(new CertificateVersion(VERSION_ONE))
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

  @Nested
  class FindLatestActiveByType {

    @Test
    void shallReturnCertificateModelIfActiveAndMatchType() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var expectedModel = CertificateModel.builder()
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE_ONE))
                  .version(new CertificateVersion(VERSION_ONE))
                  .build()
          )
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
          .build();

      doReturn(expectedModel).when(certificateModelFactoryOne).create();

      final var actualModel = inMemoryCertificateModelRepository.findLatestActiveByType(
          new CertificateType(TYPE_ONE));

      assertEquals(expectedModel, actualModel.orElse(null));
    }

    @Test
    void shallReturnEmptyIfNotActiveAndMatchType() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var model = CertificateModel.builder()
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE_ONE))
                  .version(new CertificateVersion(VERSION_ONE))
                  .build()
          )
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(1))
          .build();

      doReturn(model).when(certificateModelFactoryOne).create();

      final var actualModel = inMemoryCertificateModelRepository.findLatestActiveByType(
          new CertificateType(TYPE_ONE));

      assertTrue(actualModel.isEmpty(),
          () -> "Expect model to be empty but retured %s".formatted(actualModel.orElseThrow())
      );
    }

    @Test
    void shallReturnEmptyIfActiveAndDifferentType() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var model = CertificateModel.builder()
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE_TWO))
                  .version(new CertificateVersion(VERSION_ONE))
                  .build()
          )
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
          .build();

      doReturn(model).when(certificateModelFactoryOne).create();

      final var actualModel = inMemoryCertificateModelRepository.findLatestActiveByType(
          new CertificateType(TYPE_ONE));

      assertTrue(actualModel.isEmpty(),
          () -> "Expect model to be empty but retured %s".formatted(actualModel.orElseThrow())
      );
    }

    @Test
    void shallReturnLatestCertificateModelIfActiveAndMatchType() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne, certificateModelFactoryTwo)
      );

      final var modelOne = CertificateModel.builder()
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE_ONE))
                  .version(new CertificateVersion(VERSION_ONE))
                  .build()
          )
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(5))
          .build();

      final var expectedModel = CertificateModel.builder()
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE_ONE))
                  .version(new CertificateVersion(VERSION_TWO))
                  .build()
          )
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
          .build();

      doReturn(modelOne).when(certificateModelFactoryOne).create();
      doReturn(expectedModel).when(certificateModelFactoryTwo).create();

      final var actualModel = inMemoryCertificateModelRepository.findLatestActiveByType(
          new CertificateType(TYPE_ONE));

      assertEquals(expectedModel, actualModel.orElse(null));
    }

    @Test
    void shallThrowExceptionIfCertificateTypeIsNull() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> inMemoryCertificateModelRepository.findLatestActiveByType(null)
      );

      assertEquals("CertificateType is null!", illegalArgumentException.getMessage());
    }
  }

  @Nested
  class GetById {

    @Test
    void shallReturnCertificateModelIfIdExistsAndIsActive() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var expectedModel = CertificateModel.builder()
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE_ONE))
                  .version(new CertificateVersion(VERSION_ONE))
                  .build()
          )
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
          .build();

      doReturn(expectedModel).when(certificateModelFactoryOne).create();

      final var actualModel = inMemoryCertificateModelRepository.getById(expectedModel.getId());

      assertEquals(expectedModel, actualModel);
    }

    @Test
    void shallThrowExceptionIfIdIsMissing() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var expectedModel = CertificateModel.builder()
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE_ONE))
                  .version(new CertificateVersion(VERSION_ONE))
                  .build()
          )
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
          .build();

      doReturn(expectedModel).when(certificateModelFactoryOne).create();

      final var certificateModelId = CertificateModelId.builder()
          .type(new CertificateType(TYPE_TWO))
          .version(new CertificateVersion(VERSION_ONE))
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> inMemoryCertificateModelRepository.getById(certificateModelId)
      );

      assertEquals("CertificateModel missing: %s".formatted(certificateModelId),
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowExceptionIfCertificateModelNotActive() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var expectedModel = CertificateModel.builder()
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE_ONE))
                  .version(new CertificateVersion(VERSION_ONE))
                  .build()
          )
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(1))
          .build();

      final var certificateModelId = expectedModel.getId();

      doReturn(expectedModel).when(certificateModelFactoryOne).create();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> inMemoryCertificateModelRepository.getById(certificateModelId)
      );

      assertEquals(
          "CertificateModel with id '%s' not active until '%s'".formatted(
              expectedModel.getId(),
              expectedModel.getActiveFrom()
          ),
          illegalArgumentException.getMessage()
      );
    }

    @Test
    void shallThrowExceptionIfCertificateModelIdIsNull() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> inMemoryCertificateModelRepository.getById(null)
      );

      assertEquals("CertificateModelId is null!", illegalArgumentException.getMessage());
    }
  }
}