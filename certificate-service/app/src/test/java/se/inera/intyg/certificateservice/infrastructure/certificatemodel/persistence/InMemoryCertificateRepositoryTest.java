package se.inera.intyg.certificateservice.infrastructure.certificatemodel.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

class InMemoryCertificateRepositoryTest {

  private static final String NAME = "modelName";
  private static final String CERTIFICATE_ID = "certificateId";
  private InMemoryCertificateRepository certificateRepository;

  @BeforeEach
  void setUp() {
    certificateRepository = new InMemoryCertificateRepository();
  }

  @Nested
  class CreateCertificate {

    @Test
    void shallThrowIfCertificateModelIsNull() {
      assertThrows(IllegalArgumentException.class, () -> certificateRepository.create(null)
      );
    }

    @Test
    void shallReturnCertificate() {
      final var expectedModel = CertificateModel.builder()
          .name(NAME)
          .build();

      final var certificateId = UUID.randomUUID();

      final var expectedCertificate = Certificate.builder()
          .certificateModel(expectedModel)
          .id(new CertificateId(certificateId.toString()))
          .build();

      try (MockedStatic<UUID> uuidMockedStatic = mockStatic(
          UUID.class)) {
        uuidMockedStatic.when(UUID::randomUUID).thenReturn(certificateId);

        final var actualCertificate = certificateRepository.create(expectedModel);

        assertEquals(expectedCertificate, actualCertificate);
      }
    }

    @Test
    void shallReturnCertificateWithGeneratedId() {
      final var certificateModel = CertificateModel.builder()
          .name(NAME)
          .build();

      final var expectedId = UUID.randomUUID();

      try (MockedStatic<UUID> uuidMockedStatic = mockStatic(
          UUID.class)) {
        uuidMockedStatic.when(UUID::randomUUID).thenReturn(expectedId);

        final var actualId = certificateRepository.create(certificateModel).getId().id();

        assertEquals(expectedId.toString(), actualId);
      }
    }

    @Test
    void shallReturnCertificateWithProvidedModel() {
      final var expectedModel = CertificateModel.builder()
          .name(NAME)
          .build();
      final var actualModel = certificateRepository.create(expectedModel)
          .getCertificateModel();

      assertEquals(expectedModel, actualModel);
    }
  }

  @Nested
  class SaveCertificate {

    @Test
    void shallReturnSavedCertificate() {
      final var expectedCertificate = Certificate.builder().build();

      final var actualCertificate = certificateRepository.save(expectedCertificate);

      assertEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void shallThrowIfCertificateIsNull() {
      assertThrows(IllegalArgumentException.class, () -> certificateRepository.save(null));
    }

    @Test
    void shallPersistCertificateOnSave() {
      final var expectedCertificate = Certificate.builder().build();

      final var certificate = certificateRepository.save(expectedCertificate);
      final var actualCertificate = certificateRepository.getById(certificate.getId());

      assertEquals(expectedCertificate, actualCertificate);
    }
  }

  @Nested
  class GetCertificate {

    @Test
    void shallReturnCertificateIfCertificateIdIsPresent() {
      final var certificateId = new CertificateId(CERTIFICATE_ID);
      final var expectedCertificate = certificateRepository.save(
          Certificate.builder()
              .id(certificateId)
              .build()
      );

      final var actualCertificate = certificateRepository.getById(certificateId);
      assertEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void shallThrowIfCertificateNotPresentInRepository() {
      final var certificateId = new CertificateId(CERTIFICATE_ID);
      assertThrows(IllegalStateException.class,
          () -> certificateRepository.getById(certificateId));
    }
  }
}
