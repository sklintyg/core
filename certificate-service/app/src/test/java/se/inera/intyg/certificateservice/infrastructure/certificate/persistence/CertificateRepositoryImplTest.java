package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7804_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateExportPage;
import se.inera.intyg.certificateservice.domain.certificate.model.PlaceholderCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PlaceholderCertificateRequest;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.service.PatientInformationProvider;

@ExtendWith(MockitoExtension.class)
class CertificateRepositoryImplTest {

  @Mock
  JpaCertificateRepository jpaCertificateRepository;
  @Mock
  PatientInformationProvider patientInformationProvider;
  @InjectMocks
  CertificateRepositoryImpl certificateRepository;


  @Nested
  class CreateTests {

    @Test
    void shouldReturnCertificateFromJpaCertificateRepository() {
      certificateRepository.create(FK7804_CERTIFICATE_MODEL);
      verify(jpaCertificateRepository).create(FK7804_CERTIFICATE_MODEL, certificateRepository);
    }
  }

  @Nested
  class CreateFromPlaceholderTests {

    @Test
    void shouldReturnCertificateFromJpaCertificateRepository() {
      final var request = PlaceholderCertificateRequest.builder().build();
      certificateRepository.createFromPlaceholder(request, FK7804_CERTIFICATE_MODEL);
      verify(jpaCertificateRepository).createFromPlaceholder(request, FK7804_CERTIFICATE_MODEL,
          certificateRepository);
    }
  }

  @Nested
  class SaveTests {

    @Test
    void shouldReturnCertificateFromJpaCertificateRepository() {
      when(jpaCertificateRepository.save(FK7210_CERTIFICATE, certificateRepository))
          .thenReturn(FK7210_CERTIFICATE);

      certificateRepository.save(FK7210_CERTIFICATE);
      verify(jpaCertificateRepository).save(FK7210_CERTIFICATE, certificateRepository);
    }

    @Test
    void shouldUpdatePatient() {
      when(jpaCertificateRepository.save(FK7210_CERTIFICATE, certificateRepository))
          .thenReturn(FK7210_CERTIFICATE);
      when(patientInformationProvider.findPatient(
          FK7210_CERTIFICATE.certificateMetaData().patient().id()))
          .thenReturn(Optional.of(ATHENA_REACT_ANDERSSON));

      final var result = certificateRepository.save(FK7210_CERTIFICATE);
      assertEquals(ATHENA_REACT_ANDERSSON, result.certificateMetaData().patient());
    }
  }

  @Nested
  class GetByIdTests {

    @Test
    void shouldReturnCertificateFromJpaCertificateRepository() {
      final var certificateId = FK7210_CERTIFICATE.id();
      when(jpaCertificateRepository.getById(certificateId, certificateRepository))
          .thenReturn(FK7210_CERTIFICATE);

      certificateRepository.getById(certificateId);
      verify(jpaCertificateRepository).getById(certificateId, certificateRepository);
    }

    @Test
    void shouldUpdatePatient() {
      final var certificateId = FK7210_CERTIFICATE.id();
      when(jpaCertificateRepository.getById(certificateId, certificateRepository))
          .thenReturn(FK7210_CERTIFICATE);
      when(patientInformationProvider.findPatient(
          FK7210_CERTIFICATE.certificateMetaData().patient().id()))
          .thenReturn(Optional.of(ATHENA_REACT_ANDERSSON));

      final var result = certificateRepository.getById(certificateId);
      assertEquals(ATHENA_REACT_ANDERSSON, result.certificateMetaData().patient());
    }
  }

  @Nested
  class GetByIdsTests {

    @Test
    void shouldReturnCertificatesFromJpaCertificateRepository() {
      final var certificateIds = List.of(FK7210_CERTIFICATE.id());
      when(jpaCertificateRepository.getByIds(certificateIds, certificateRepository))
          .thenReturn(List.of(FK7210_CERTIFICATE));

      certificateRepository.getByIds(certificateIds);
      verify(jpaCertificateRepository).getByIds(certificateIds, certificateRepository);
    }

    @Test
    void shouldUpdatePatient() {
      final var certificateIds = List.of(FK7210_CERTIFICATE.id());
      when(jpaCertificateRepository.getByIds(certificateIds, certificateRepository))
          .thenReturn(List.of(FK7210_CERTIFICATE));
      when(patientInformationProvider.findPatients(List.of(
          FK7210_CERTIFICATE.certificateMetaData().patient().id())))
          .thenReturn(List.of(ATHENA_REACT_ANDERSSON));

      final var result = certificateRepository.getByIds(certificateIds);
      assertEquals(ATHENA_REACT_ANDERSSON, result.getFirst().certificateMetaData().patient());
    }
  }

  @Nested
  class FindByIdsTests {

    @Test
    void shouldReturnCertificatesFromJpaCertificateRepository() {
      final var certificateIds = List.of(FK7210_CERTIFICATE.id());
      when(jpaCertificateRepository.findByIds(certificateIds, certificateRepository))
          .thenReturn(List.of(FK7210_CERTIFICATE));

      certificateRepository.findByIds(certificateIds);
      verify(jpaCertificateRepository).findByIds(certificateIds, certificateRepository);
    }

    @Test
    void shouldUpdatePatient() {
      final var certificateIds = List.of(FK7210_CERTIFICATE.id());
      when(jpaCertificateRepository.findByIds(certificateIds, certificateRepository))
          .thenReturn(List.of(FK7210_CERTIFICATE));
      when(patientInformationProvider.findPatients(List.of(
          FK7210_CERTIFICATE.certificateMetaData().patient().id())))
          .thenReturn(List.of(ATHENA_REACT_ANDERSSON));

      final var result = certificateRepository.findByIds(certificateIds);
      assertEquals(ATHENA_REACT_ANDERSSON, result.getFirst().certificateMetaData().patient());
    }
  }

  @Nested
  class ExistsTests {

    @Test
    void shouldReturnTrueWhenCertificateExists() {
      final var certificateId = FK7210_CERTIFICATE.id();
      when(jpaCertificateRepository.exists(certificateId)).thenReturn(true);

      final var result = certificateRepository.exists(certificateId);
      assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenCertificateDoesNotExist() {
      final var certificateId = FK7210_CERTIFICATE.id();
      when(jpaCertificateRepository.exists(certificateId)).thenReturn(false);

      final var result = certificateRepository.exists(certificateId);
      assertFalse(result);
    }
  }

  @Nested
  class FindByCertificatesRequestTests {

    @Test
    void shouldReturnCertificatesFromJpaCertificateRepository() {
      final var request = CertificatesRequest.builder().build();
      when(jpaCertificateRepository.findByCertificatesRequest(request, certificateRepository))
          .thenReturn(List.of(FK7210_CERTIFICATE));

      certificateRepository.findByCertificatesRequest(request);
      verify(jpaCertificateRepository).findByCertificatesRequest(request, certificateRepository);
    }

    @Test
    void shouldUpdatePatient() {
      final var request = CertificatesRequest.builder().build();
      when(jpaCertificateRepository.findByCertificatesRequest(request, certificateRepository))
          .thenReturn(List.of(FK7210_CERTIFICATE));
      when(patientInformationProvider.findPatients(List.of(
          FK7210_CERTIFICATE.certificateMetaData().patient().id())))
          .thenReturn(List.of(ATHENA_REACT_ANDERSSON));

      final var result = certificateRepository.findByCertificatesRequest(request);
      assertEquals(ATHENA_REACT_ANDERSSON, result.getFirst().certificateMetaData().patient());
    }
  }

  @Nested
  class GetExportByCareProviderIdTests {

    @Test
    void shouldReturnExportPageFromJpaCertificateRepository() {
      final var careProviderId = new HsaId("HSA123");
      final var page = 0;
      final var size = 10;
      final var expectedPage = CertificateExportPage.builder().build();
      when(jpaCertificateRepository.getExportByCareProviderId(careProviderId, page, size,
          certificateRepository))
          .thenReturn(expectedPage);

      final var result = certificateRepository.getExportByCareProviderId(careProviderId, page,
          size);
      assertEquals(expectedPage, result);
    }
  }

  @Nested
  class DeleteByCareProviderIdTests {

    @Test
    void shouldReturnDeletedCountFromJpaCertificateRepository() {
      final var careProviderId = new HsaId("HSA123");
      when(jpaCertificateRepository.deleteByCareProviderId(careProviderId)).thenReturn(5L);

      final var result = certificateRepository.deleteByCareProviderId(careProviderId);
      assertEquals(5L, result);
    }
  }

  @Nested
  class PlaceholderExistsTests {

    @Test
    void shouldReturnTrueWhenPlaceholderExists() {
      final var certificateId = FK7210_CERTIFICATE.id();
      when(jpaCertificateRepository.placeholderExists(certificateId)).thenReturn(true);

      final var result = certificateRepository.placeholderExists(certificateId);
      assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenPlaceholderDoesNotExist() {
      final var certificateId = FK7210_CERTIFICATE.id();
      when(jpaCertificateRepository.placeholderExists(certificateId)).thenReturn(false);

      final var result = certificateRepository.placeholderExists(certificateId);
      assertFalse(result);
    }
  }

  @Nested
  class GetPlaceholderByIdTests {

    @Test
    void shouldReturnPlaceholderFromJpaCertificateRepository() {
      final var certificateId = FK7210_CERTIFICATE.id();
      final var placeholder = PlaceholderCertificate.builder().build();
      when(jpaCertificateRepository.getPlaceholderById(certificateId)).thenReturn(placeholder);

      final var result = certificateRepository.getPlaceholderById(certificateId);
      assertEquals(placeholder, result);
    }
  }

  @Nested
  class SavePlaceholderTests {

    @Test
    void shouldReturnSavedPlaceholderFromJpaCertificateRepository() {
      final var placeholder = PlaceholderCertificate.builder().build();
      when(jpaCertificateRepository.save(placeholder)).thenReturn(placeholder);

      final var result = certificateRepository.save(placeholder);
      assertEquals(placeholder, result);
    }
  }

  @Nested
  class GetMetadataFromSignInstanceTests {

    @Test
    void shouldReturnMetadataFromJpaCertificateRepository() {
      final var metadata = FK7210_CERTIFICATE.certificateMetaData();
      final var signed = LocalDateTime.now();
      when(jpaCertificateRepository.getMetadataFromSignInstance(metadata, signed))
          .thenReturn(metadata);

      final var result = certificateRepository.getMetadataFromSignInstance(metadata, signed);
      assertEquals(metadata, result);
    }
  }

  @Nested
  class InsertTests {

    @Test
    void shouldReturnCertificateFromJpaCertificateRepository() {
      final var revision = new Revision(1L);
      when(jpaCertificateRepository.insert(FK7210_CERTIFICATE, revision, certificateRepository))
          .thenReturn(FK7210_CERTIFICATE);

      final var result = certificateRepository.insert(FK7210_CERTIFICATE, revision);
      assertEquals(FK7210_CERTIFICATE, result);
    }
  }

  @Nested
  class RemoveTests {

    @Test
    void shouldCallJpaCertificateRepositoryRemove() {
      final var certificateIds = List.of(FK7210_CERTIFICATE.id());

      certificateRepository.remove(certificateIds);
      verify(jpaCertificateRepository).remove(certificateIds);
    }
  }

  @Nested
  class FindValidSickLeavesByIdsTests {

    @Test
    void shouldCallJpaCertificateRepositoryFindValidSickLeavesByIds() {
      final var certificateIds = List.of(FK7210_CERTIFICATE.id());

      certificateRepository.findValidSickLeavesCertificateIdsByIds(certificateIds);
      verify(jpaCertificateRepository).findValidSickLeavesByIds(certificateIds);
    }
  }
}