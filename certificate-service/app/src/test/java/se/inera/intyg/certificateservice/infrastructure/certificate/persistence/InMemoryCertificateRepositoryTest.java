package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ALVE_REACT_ALFREDSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

class InMemoryCertificateRepositoryTest {

  private static final String NAME = "modelName";
  private static final String CERTIFICATE_ID = "certificateId";
  private static final String WRONG_CERTIFICATE_ID = "WRONG_CERTIFICATE_ID";
  private static final HsaId CARE_UNIT_ID = new HsaId("careUnitId");
  private static final HsaId ANOTHER_CARE_UNIT_ID = new HsaId("anotherCareUnitId");
  private static final String PATIENT_ID = "patientId";
  private static final String ANOTHER_CERTIFICATE_ID = "anotherCertificateId";
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

    @Test
    void shallReturnCertificateWithRevison() {
      final var expectedModel = CertificateModel.builder()
          .name(NAME)
          .build();
      final var actualModel = certificateRepository.create(expectedModel);

      assertEquals(0, actualModel.revision().value());
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
      final var expectedCertificate = Certificate.builder()
          .id(new CertificateId(CERTIFICATE_ID))
          .build();

      final var certificate = certificateRepository.save(expectedCertificate);
      final var actualCertificate = certificateRepository.getById(certificate.id());

      assertEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void shouldDeleteCertificate() {
      final var certificate = Certificate.builder()
          .id(new CertificateId(CERTIFICATE_ID))
          .build();

      final var deletedCertificate = Certificate.builder()
          .id(new CertificateId(CERTIFICATE_ID))
          .status(Status.DELETED_DRAFT)
          .build();

      certificateRepository.save(certificate);
      certificateRepository.save(deletedCertificate);

      assertFalse(certificateRepository.exists(new CertificateId(CERTIFICATE_ID)));
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
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificateRepository.getById(certificateId)
      );
      assertEquals(
          "CertificateId '%s' not present in repository".formatted(certificateId),
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowExceptionIfCertificateIdIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificateRepository.getById(null)
      );
      assertEquals("CertificateId is null!", illegalArgumentException.getMessage());
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

    @Test
    void shouldThrowExceptionIfCertificateIdIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificateRepository.exists(null)
      );
      assertEquals("CertificateId is null!", illegalArgumentException.getMessage());
    }
  }

  @Nested
  class CertificateFindByPatientByCareUnit {

    @Test
    void shallReturnListOfCertificatesByPatientAndByCareUnit() {
      final var expectedCertificate = Certificate.builder()
          .id(new CertificateId(CERTIFICATE_ID))
          .certificateMetaData(
              CertificateMetaData.builder()
                  .patient(
                      ATHENA_REACT_ANDERSSON
                  )
                  .careUnit(
                      ALFA_MEDICINCENTRUM
                  )
                  .build()
          )
          .build();

      final var certificateOnAnotherCareUnit = Certificate.builder()
          .id(new CertificateId(ANOTHER_CERTIFICATE_ID))
          .certificateMetaData(
              CertificateMetaData.builder()
                  .patient(
                      ATHENA_REACT_ANDERSSON
                  )
                  .careUnit(
                      ALFA_VARDCENTRAL
                  )
                  .build()
          )
          .build();

      certificateRepository.save(expectedCertificate);
      certificateRepository.save(certificateOnAnotherCareUnit);

      final var result = certificateRepository.findByPatientByCareUnit(
          ATHENA_REACT_ANDERSSON,
          ALFA_MEDICINCENTRUM
      );

      assertEquals(List.of(expectedCertificate), result);
    }

    @Test
    void shallReturnEmptyListOfCertificatesIfPatientDoesNotHaveAnyCertificates() {

      final var certificate = Certificate.builder()
          .id(new CertificateId(CERTIFICATE_ID))
          .certificateMetaData(
              CertificateMetaData.builder()
                  .patient(
                      ALVE_REACT_ALFREDSSON
                  )
                  .careUnit(
                      ALFA_MEDICINCENTRUM
                  )
                  .build()
          )
          .build();

      certificateRepository.save(certificate);
      final var result = certificateRepository.findByPatientByCareUnit(
          ATHENA_REACT_ANDERSSON,
          ALFA_MEDICINCENTRUM
      );

      assertTrue(result.isEmpty(), "Shall return empty list if no certificate found");
    }
  }

  @Nested
  class CertificateFindByPatientBySubUnit {

    @Test
    void shallReturnListOfCertificatesByPatientAndByCareUnit() {
      final var expectedCertificate = Certificate.builder()
          .id(new CertificateId(CERTIFICATE_ID))
          .certificateMetaData(
              CertificateMetaData.builder()
                  .patient(
                      ATHENA_REACT_ANDERSSON
                  )
                  .issuingUnit(
                      ALFA_ALLERGIMOTTAGNINGEN
                  )
                  .build()
          )
          .build();

      final var certificateOnAnotherCareUnit = Certificate.builder()
          .id(new CertificateId(ANOTHER_CERTIFICATE_ID))
          .certificateMetaData(
              CertificateMetaData.builder()
                  .patient(
                      ATHENA_REACT_ANDERSSON
                  )
                  .issuingUnit(
                      ALFA_VARDCENTRAL
                  )
                  .build()
          )
          .build();

      certificateRepository.save(expectedCertificate);
      certificateRepository.save(certificateOnAnotherCareUnit);

      final var result = certificateRepository.findByPatientBySubUnit(
          ATHENA_REACT_ANDERSSON,
          ALFA_ALLERGIMOTTAGNINGEN
      );

      assertEquals(List.of(expectedCertificate), result);
    }

    @Test
    void shallReturnEmptyListOfCertificatesIfPatientDoesNotHaveAnyCertificates() {
      final var certificate = Certificate.builder()
          .id(new CertificateId(CERTIFICATE_ID))
          .certificateMetaData(
              CertificateMetaData.builder()
                  .patient(
                      ALVE_REACT_ALFREDSSON
                  )
                  .issuingUnit(
                      ALFA_ALLERGIMOTTAGNINGEN
                  )
                  .build()
          )
          .build();

      certificateRepository.save(certificate);
      final var result = certificateRepository.findByPatientBySubUnit(
          ATHENA_REACT_ANDERSSON,
          ALFA_ALLERGIMOTTAGNINGEN);

      assertTrue(result.isEmpty(), "Shall return empty list if no certificate found");
    }
  }
}
