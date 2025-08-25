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
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.testability.certificate.service.repository.TestabilityCertificateModelRepository;

@ExtendWith(MockitoExtension.class)
class InMemoryCertificateModelRepositoryTest {

  private static final String TYPE_ONE = "type1";
  private static final String VERSION_ONE = "version1";
  private static final String TYPE_TWO = "type2";
  private static final String VERSION_TWO = "version2";
  private static final String DISPLAY_NAME = "displayName";
  private static final String CODE_1 = "code1";
  private static final String CODE_SYSTEM_1 = "codeSystem1";
  private static final String CODE_2 = "code2";
  private static final String CODE_SYSTEM_2 = "codeSystem2";
  @Mock
  private CertificateModelFactory certificateModelFactoryOne;

  @Mock
  private CertificateModelFactory certificateModelFactoryTwo;

  private CertificateModelRepository inMemoryCertificateModelRepository;

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
      initCertificateModelMap(inMemoryCertificateModelRepository);

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
      initCertificateModelMap(inMemoryCertificateModelRepository);

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
      initCertificateModelMap(inMemoryCertificateModelRepository);

      final var actualModels = inMemoryCertificateModelRepository.findAllActive();

      assertEquals(2, actualModels.size());
      assertTrue(actualModels.contains(expectedModelOne),
          "Expected model with id: %s".formatted(expectedModelOne.id())
      );
      assertTrue(actualModels.contains(expectedModelTwo),
          "Expected model with id: %s".formatted(expectedModelTwo.id())
      );
    }
  }

  private void initCertificateModelMap(CertificateModelRepository repository) {
    try {
      final var postConstruct = InMemoryCertificateModelRepository.class.getDeclaredMethod(
          "initCertificateModelMap");
      postConstruct.setAccessible(true);
      postConstruct.invoke(repository);
    } catch (Exception ignored) {

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
      initCertificateModelMap(inMemoryCertificateModelRepository);

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
      initCertificateModelMap(inMemoryCertificateModelRepository);

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
          .type(
              new Code(CODE_1, CODE_SYSTEM_1, null)
          )
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
          .build();

      doReturn(model).when(certificateModelFactoryOne).create();
      initCertificateModelMap(inMemoryCertificateModelRepository);

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
      initCertificateModelMap(inMemoryCertificateModelRepository);

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
  class FindLastestActiveByCode {

    @Test
    void shallReturnCertificateModelIfActiveAndMatchCode() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var expectedModel = CertificateModel.builder()
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE_TWO))
                  .version(new CertificateVersion(VERSION_ONE))
                  .build()
          )
          .type(new Code(TYPE_ONE, CODE_SYSTEM_1, null))
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
          .build();

      doReturn(expectedModel).when(certificateModelFactoryOne).create();
      initCertificateModelMap(inMemoryCertificateModelRepository);

      final var actualModel = inMemoryCertificateModelRepository.findLatestActiveByType(
          new CertificateType(TYPE_ONE));

      assertEquals(expectedModel, actualModel.orElse(null));
    }

    @Test
    void shallReturnEmptyIfNotActiveAndMatchCode() {
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
          .type(new Code(TYPE_ONE, CODE_SYSTEM_1, null))
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(1))
          .build();

      doReturn(model).when(certificateModelFactoryOne).create();
      initCertificateModelMap(inMemoryCertificateModelRepository);

      final var actualModel = inMemoryCertificateModelRepository.findLatestActiveByType(
          new CertificateType(TYPE_ONE));

      assertTrue(actualModel.isEmpty(),
          () -> "Expect model to be empty but retured %s".formatted(actualModel.orElseThrow())
      );
    }

    @Test
    void shallReturnEmptyIfActiveAndDifferentCode() {
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
          .type(new Code(CODE_1, CODE_SYSTEM_1, null))
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
          .build();

      doReturn(model).when(certificateModelFactoryOne).create();
      initCertificateModelMap(inMemoryCertificateModelRepository);

      final var actualModel = inMemoryCertificateModelRepository.findLatestActiveByType(
          new CertificateType(TYPE_ONE));

      assertTrue(actualModel.isEmpty(),
          () -> "Expect model to be empty but retured %s".formatted(actualModel.orElseThrow())
      );
    }

    @Test
    void shallReturnLatestCertificateModelIfActiveAndMatchCode() {
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
                  .type(new CertificateType(TYPE_TWO))
                  .version(new CertificateVersion(VERSION_TWO))
                  .build()
          )
          .type(new Code(TYPE_ONE, CODE_SYSTEM_1, null))
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
          .build();

      doReturn(modelOne).when(certificateModelFactoryOne).create();
      doReturn(expectedModel).when(certificateModelFactoryTwo).create();
      initCertificateModelMap(inMemoryCertificateModelRepository);

      final var actualModel = inMemoryCertificateModelRepository.findLatestActiveByType(
          new CertificateType(TYPE_ONE));

      assertEquals(expectedModel, actualModel.orElse(null));
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
      initCertificateModelMap(inMemoryCertificateModelRepository);

      final var actualModel = inMemoryCertificateModelRepository.getById(expectedModel.id());

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
      initCertificateModelMap(inMemoryCertificateModelRepository);

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

      final var certificateModelId = expectedModel.id();

      doReturn(expectedModel).when(certificateModelFactoryOne).create();
      initCertificateModelMap(inMemoryCertificateModelRepository);

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> inMemoryCertificateModelRepository.getById(certificateModelId)
      );

      assertEquals(
          "CertificateModel with id '%s' not active until '%s'".formatted(
              expectedModel.id(),
              expectedModel.activeFrom()
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

  @Nested
  class TestTestabilityCertificateModelRepository {

    private TestabilityCertificateModelRepository testabilityCertificateModelRepository;

    @Test
    void shallReturnListOfAllCertificateModelsEvenIfTheyAreNotActive() {
      testabilityCertificateModelRepository = new InMemoryCertificateModelRepository(
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
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(1))
          .build();

      doReturn(expectedModelTwo).when(certificateModelFactoryTwo).create();
      initCertificateModelMap(testabilityCertificateModelRepository);

      final var actualModels = testabilityCertificateModelRepository.all();

      assertEquals(2, actualModels.size());
      assertTrue(actualModels.contains(expectedModelOne),
          "Expected model with id: %s".formatted(expectedModelOne.id())
      );
      assertTrue(actualModels.contains(expectedModelTwo),
          "Expected model with id: %s".formatted(expectedModelTwo.id())
      );
    }
  }

  @Nested
  class FindLatestActiveByExternalTypeTests {

    @Test
    void shallThrowIfCodeIsNull() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      assertThrows(IllegalArgumentException.class,
          () -> inMemoryCertificateModelRepository.findLatestActiveByExternalType(null));
    }

    @Test
    void shallReturnCertificateModelIfActiveAndMatchCode() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var expectedModel = CertificateModel.builder()
          .type(new Code(CODE_1, CODE_SYSTEM_1, DISPLAY_NAME))
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
          .build();

      doReturn(expectedModel).when(certificateModelFactoryOne).create();
      initCertificateModelMap(inMemoryCertificateModelRepository);

      final var actualModel = inMemoryCertificateModelRepository.findLatestActiveByExternalType(
          new Code(CODE_1, CODE_SYSTEM_1, null)
      );

      assertEquals(expectedModel, actualModel.orElse(null));
    }

    @Test
    void shallReturnEmptyIfNotActiveAndMatchType() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var expectedModel = CertificateModel.builder()
          .type(new Code(CODE_1, CODE_SYSTEM_1, DISPLAY_NAME))
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(1))
          .build();

      doReturn(expectedModel).when(certificateModelFactoryOne).create();
      initCertificateModelMap(inMemoryCertificateModelRepository);

      final var actualModel = inMemoryCertificateModelRepository.findLatestActiveByExternalType(
          new Code(CODE_1, CODE_SYSTEM_1, null)
      );

      assertTrue(actualModel.isEmpty(),
          () -> "Expect model to be empty but retured %s".formatted(actualModel.orElseThrow())
      );
    }

    @Test
    void shallReturnEmptyIfActiveAndDifferentCode() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var expectedModel = CertificateModel.builder()
          .type(new Code(CODE_1, CODE_SYSTEM_1, DISPLAY_NAME))
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
          .build();

      doReturn(expectedModel).when(certificateModelFactoryOne).create();
      initCertificateModelMap(inMemoryCertificateModelRepository);

      final var actualModel = inMemoryCertificateModelRepository.findLatestActiveByExternalType(
          new Code(CODE_2, CODE_SYSTEM_1, null)
      );
      assertTrue(actualModel.isEmpty(),
          () -> "Expect model to be empty but retured %s".formatted(actualModel.orElseThrow())
      );
    }

    @Test
    void shallReturnEmptyIfActiveAndDifferentCodeSystem() {
      inMemoryCertificateModelRepository = new InMemoryCertificateModelRepository(
          List.of(certificateModelFactoryOne)
      );

      final var expectedModel = CertificateModel.builder()
          .type(new Code(CODE_1, CODE_SYSTEM_1, DISPLAY_NAME))
          .activeFrom(LocalDateTime.now(ZoneId.systemDefault()).minusMinutes(1))
          .build();

      doReturn(expectedModel).when(certificateModelFactoryOne).create();
      initCertificateModelMap(inMemoryCertificateModelRepository);

      final var actualModel = inMemoryCertificateModelRepository.findLatestActiveByExternalType(
          new Code(CODE_1, CODE_SYSTEM_2, null)
      );
      assertTrue(actualModel.isEmpty(),
          () -> "Expect model to be empty but retured %s".formatted(actualModel.orElseThrow())
      );
    }
  }
}