package se.inera.intyg.certificateservice.infrastructure.certificatemodel.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
  private static final String WRONG_CERTIFICATE_ID = "WRONG_CERTIFICATE_ID";
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
    void shallReturnCertificateWithGeneratedId() {
      final var certificateModel = CertificateModel.builder()
          .name(NAME)
          .build();

      final var expectedId = UUID.randomUUID();

      try (MockedStatic<UUID> uuidMockedStatic = mockStatic(
          UUID.class)) {
        uuidMockedStatic.when(UUID::randomUUID).thenReturn(expectedId);

        final var actualId = certificateRepository.create(certificateModel).id().id();

        assertEquals(expectedId.toString(), actualId);
      }
    }

    @Test
    void shallReturnCertificateWithProvidedModel() {
      final var certificateModel = CertificateModel.builder()
          .name(NAME)
          .build();

      final var certificate = certificateRepository.create(certificateModel);

      assertNotNull(certificate.created(), "Expected created on new certificate, was null");
    }

    @Test
    void shallReturnCertificateWithCreated() {
      final var expectedModel = CertificateModel.builder()
          .name(NAME)
          .build();
      final var actualModel = certificateRepository.create(expectedModel).certificateModel();

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
      final var actualCertificate = certificateRepository.getById(certificate.id());

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

  @Nested
  class CertificateExists {

    @Test
    void shouldReturnTrueIfCertificateExists() {
      final var expectedCertificate = Certificate.builder()
          .id(new CertificateId(CERTIFICATE_ID))
          .build();

      certificateRepository.save(expectedCertificate);
      final var result = certificateRepository.exists(new CertificateId(CERTIFICATE_ID));

      assertTrue(result);
    }

    @Test
    void shouldReturnEmptyIfCertificateDontExists() {
      final var expectedCertificate = Certificate.builder()
          .id(new CertificateId(CERTIFICATE_ID))
          .build();

      certificateRepository.save(expectedCertificate);
      final var result = certificateRepository.exists(new CertificateId(WRONG_CERTIFICATE_ID));

      assertFalse(result);
    }
  }
}
