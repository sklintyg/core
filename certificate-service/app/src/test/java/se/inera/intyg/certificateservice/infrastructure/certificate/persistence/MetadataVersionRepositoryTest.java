package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientVersionEntity.ATHENA_REACT_ANDERSSON_VERSION_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_META_DATA_AJLA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.ajlaMetadataBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.PatientVersionEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffVersionEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitVersionEntityRepository;

@ExtendWith(MockitoExtension.class)
class MetadataVersionRepositoryTest {

  @Mock
  StaffEntityRepository staffEntityRepository;
  @Mock
  UnitEntityRepository unitEntityRepository;
  @Mock
  PatientEntityRepository patientEntityRepository;
  @Mock
  StaffVersionEntityRepository staffVersionEntityRepository;
  @Mock
  PatientVersionEntityRepository patientVersionEntityRepository;
  @Mock
  UnitVersionEntityRepository unitVersionEntityRepository;
  @InjectMocks
  private MetadataVersionRepository metadataVersionRepository;


  private static final LocalDateTime TIMESTAMP = LocalDateTime.now()
      .truncatedTo(ChronoUnit.SECONDS);


  @Nested
  class GetMetadataFromSignInstance {

    @Test
    void shouldThrowIllegalStateExceptionWhenNotSigned() {
      assertThrows(IllegalStateException.class, () -> {
        metadataVersionRepository.getMetadataFromSignInstance(CERTIFICATE_META_DATA_AJLA, null);
      });
    }

    @Test
    void shouldReturnMetadataWhenSigned() {

      CertificateMetaData metaData = ajlaMetadataBuilder().build();

      doReturn(Optional.of(ATHENA_REACT_ANDERSSON_VERSION_ENTITY)).when(
              patientVersionEntityRepository)
          .findFirstCoveringTimestampOrderByMostRecent(metaData.patient().id().idWithoutDash(),
              TIMESTAMP);

      final var result = metadataVersionRepository.getMetadataFromSignInstance(metaData, TIMESTAMP);

      assertEquals(ATHENA_REACT_ANDERSSON, result.patient());
    }


  }

  @Nested
  class UpdateStaffVersion {

  }

  @Nested
  class UpdatePatientVersion {

  }

  @Nested
  class UpdateUnitVersion {

  }


}