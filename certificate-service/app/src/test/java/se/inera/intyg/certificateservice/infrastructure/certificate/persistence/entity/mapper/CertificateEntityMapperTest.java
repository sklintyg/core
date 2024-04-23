package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateEntity.CERTIFICATE_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateRevokedEntity.REVOKED_MESSAGE;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.ALFA_MEDICINCENTRUM_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REF;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7211_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7211_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReason.INCORRECT_PATIENT;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.patient.model.Deceased;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.patient.model.ProtectedPerson;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.domain.unit.model.UnitName;
import se.inera.intyg.certificateservice.domain.user.model.ExternalReference;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.PatientRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.StaffRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.UnitRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateModelEntityRepository;


@ExtendWith(MockitoExtension.class)
class CertificateEntityMapperTest {

  @InjectMocks
  private CertificateEntityMapper certificateEntityMapper;
  @Mock
  CertificateEntityRepository certificateEntityRepository;

  @Mock
  private PatientRepository patientRepository;

  @Mock
  private UnitRepository unitRepository;

  @Mock
  private StaffRepository staffRepository;

  @Mock
  private CertificateModelEntityRepository certificateModelEntityRepository;

  @Mock
  private CertificateDataEntityMapper certificateDataEntityMapper;

  private static final LocalDate NOW = LocalDate.now();
  private static final String DATE_ID = "dateId";
  private final static String XML = "<xml/>";

  @Nested
  class ToEntity {

    @BeforeEach
    void setUp() {
      doReturn(Optional.of(CERTIFICATE_ENTITY))
          .when(certificateEntityRepository)
          .findByCertificateId(FK7211_CERTIFICATE.id().id());
      doReturn(CertificateDataEntity.builder().build())
          .when(certificateDataEntityMapper).toEntity(any());
    }

    @Test
    void shouldMapPatient() {
      final var response = certificateEntityMapper.toEntity(FK7211_CERTIFICATE);

      assertEquals(CERTIFICATE_ENTITY.getPatient(), response.getPatient());
    }

    @Test
    void shouldMapCareProvider() {
      final var response = certificateEntityMapper.toEntity(FK7211_CERTIFICATE);

      assertEquals(CERTIFICATE_ENTITY.getCareProvider(), response.getCareProvider());
    }

    @Test
    void shouldMapCareUnit() {
      final var response = certificateEntityMapper.toEntity(FK7211_CERTIFICATE);

      assertEquals(CERTIFICATE_ENTITY.getCareUnit(), response.getCareUnit());
    }

    @Test
    void shouldMapIssuedOnUnit() {
      final var response = certificateEntityMapper.toEntity(FK7211_CERTIFICATE);

      assertEquals(CERTIFICATE_ENTITY.getIssuedOnUnit(), response.getIssuedOnUnit());
    }

    @Test
    void shouldMapIssuedBy() {
      final var response = certificateEntityMapper.toEntity(FK7211_CERTIFICATE);

      assertEquals(CERTIFICATE_ENTITY.getIssuedBy(), response.getIssuedBy());
    }

    @Test
    void shouldMapXmlIfXmlIsNotNull() {
      final var response = certificateEntityMapper.toEntity(FK7211_CERTIFICATE);

      assertEquals(CERTIFICATE_ENTITY.getXml(), response.getXml());
    }

    @Test
    void shouldMapSentByIfSentIsNotNull() {
      final var response = certificateEntityMapper.toEntity(FK7211_CERTIFICATE);

      assertEquals(CERTIFICATE_ENTITY.getSentBy(), response.getSentBy());
    }

    @Test
    void shouldMapSentIfSentIsNotNull() {
      final var response = certificateEntityMapper.toEntity(FK7211_CERTIFICATE);

      assertEquals(CERTIFICATE_ENTITY.getSent(), response.getSent());
    }

    @Nested
    class RevokedIsNotNull {

      @Test
      void shouldMapRevokedBy() {
        final var response = certificateEntityMapper.toEntity(FK7211_CERTIFICATE);

        assertEquals(CERTIFICATE_ENTITY.getRevokedBy(), response.getRevokedBy());
      }

      @Test
      void shouldMapRevoked() {
        final var response = certificateEntityMapper.toEntity(FK7211_CERTIFICATE);

        assertEquals(CERTIFICATE_ENTITY.getRevoked(), response.getRevoked());
      }

      @Test
      void shouldMapRevokedReason() {
        final var response = certificateEntityMapper.toEntity(FK7211_CERTIFICATE);

        assertEquals(CERTIFICATE_ENTITY.getRevokedReason(), response.getRevokedReason());
      }

      @Test
      void shouldMapRevokedMessage() {
        final var response = certificateEntityMapper.toEntity(FK7211_CERTIFICATE);

        assertEquals(CERTIFICATE_ENTITY.getRevokedMessage(), response.getRevokedMessage());
      }
    }

    @Test
    void shouldMapExternalReference() {
      final var response = certificateEntityMapper.toEntity(FK7211_CERTIFICATE);
      assertEquals(CERTIFICATE_ENTITY.getExternalReference(), response.getExternalReference());
    }
  }

  @Nested
  class ToDomain {

    @Test
    void shouldMapId() {
      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(CERTIFICATE_ENTITY.getCertificateId(), response.id().id());
    }

    @Test
    void shouldMapStatus() {
      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(Status.SIGNED, response.status());
    }

    @Test
    void shouldMapCreated() {
      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(CERTIFICATE_ENTITY.getCreated(), response.created());
    }

    @Test
    void shouldMapSigned() {
      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(CERTIFICATE_ENTITY.getSigned(), response.signed());
    }

    @Test
    void shouldMapModified() {
      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(CERTIFICATE_ENTITY.getModified(), response.modified());
    }

    @Test
    void shouldMapRevision() {
      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(CERTIFICATE_ENTITY.getRevision(), response.revision().value());
    }

    @Test
    void shouldMapModel() {
      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(FK7211_CERTIFICATE_MODEL, response.certificateModel());
    }

    @Test
    void shouldMapCareProvider() {
      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(ALFA_REGIONEN, response.certificateMetaData().careProvider());
    }

    @Test
    void shouldMapCareUnit() {
      final var expected = CareUnit.builder()
          .hsaId(new HsaId(ALFA_MEDICINCENTRUM_ID))
          .name(new UnitName(ALFA_MEDICINCENTRUM_NAME))
          .build();

      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(expected, response.certificateMetaData().careUnit());
    }

    @Test
    void shouldMapIssuedOnUnitAsSubUnit() {
      final var expected = SubUnit.builder()
          .hsaId(new HsaId(ALFA_ALLERGIMOTTAGNINGEN_ID))
          .name(new UnitName(ALFA_ALLERGIMOTTAGNINGEN_NAME))
          .build();

      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(expected, response.certificateMetaData().issuingUnit());
    }

    @Test
    void shouldMapIssuedOnUnitAsCareUnit() {
      CERTIFICATE_ENTITY.setIssuedOnUnit(ALFA_MEDICINCENTRUM_ENTITY);

      final var expected = CareUnit.builder()
          .hsaId(new HsaId(ALFA_MEDICINCENTRUM_ID))
          .name(new UnitName(ALFA_MEDICINCENTRUM_NAME))
          .build();

      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(expected, response.certificateMetaData().issuingUnit());
    }

    @Test
    void shouldMapIssuer() {
      final var expected = Staff.builder()
          .hsaId(new HsaId(AJLA_DOCTOR_HSA_ID))
          .name(
              Name.builder()
                  .firstName(AJLA_DOCTOR_FIRST_NAME)
                  .middleName(AJLA_DOCTOR_MIDDLE_NAME)
                  .lastName(AJLA_DOCTOR_LAST_NAME)
                  .build()
          )
          .role(Role.DOCTOR)
          .build();

      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(expected, response.certificateMetaData().issuer());
    }

    @Test
    void shouldMapPatient() {
      final var expected = Patient.builder()
          .id(PersonId.builder()
              .id(ATHENA_REACT_ANDERSSON_ID)
              .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
              .build())
          .name(
              Name.builder()
                  .firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
                  .lastName(ATHENA_REACT_ANDERSSON_LAST_NAME)
                  .middleName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
                  .build()
          )
          .protectedPerson(new ProtectedPerson(false))
          .deceased(new Deceased(false))
          .testIndicated(new TestIndicated(false))
          .build();

      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(expected, response.certificateMetaData().patient());
    }

    @Test
    void shouldMapData() {
      final var expected = List.of(
          ElementData.builder()
              .id(new ElementId("F10"))
              .value(ElementValueDate
                  .builder()
                  .date(NOW)
                  .dateId(new FieldId(DATE_ID))
                  .build())
              .build()
      );

      doReturn(expected).when(certificateDataEntityMapper).toDomain(any());

      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(expected, response.elementData());
    }

    @Test
    void shouldMapXml() {
      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(XML, response.xml().xml());
    }

    @Test
    void shouldMapSentBy() {
      final var expected = Staff.builder()
          .hsaId(new HsaId(AJLA_DOCTOR_HSA_ID))
          .name(
              Name.builder()
                  .firstName(AJLA_DOCTOR_FIRST_NAME)
                  .middleName(AJLA_DOCTOR_MIDDLE_NAME)
                  .lastName(AJLA_DOCTOR_LAST_NAME)
                  .build()
          )
          .role(Role.DOCTOR)
          .build();

      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(expected, response.sent().sentBy());
    }

    @Test
    void shouldMapSent() {
      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertNotNull(response.sent().sentAt());
    }

    @Test
    void shouldMapRevokedAt() {
      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);
      assertNotNull(response.revoked().revokedAt());
    }

    @Test
    void shouldMapRevokedBy() {
      final var expected = Staff.builder()
          .hsaId(new HsaId(AJLA_DOCTOR_HSA_ID))
          .name(
              Name.builder()
                  .firstName(AJLA_DOCTOR_FIRST_NAME)
                  .middleName(AJLA_DOCTOR_MIDDLE_NAME)
                  .lastName(AJLA_DOCTOR_LAST_NAME)
                  .build()
          )
          .role(Role.DOCTOR)
          .build();

      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(expected, response.revoked().revokedBy());
    }

    @Test
    void shouldMapRevokedInformation() {
      final var expectedRevokedInformation = new RevokedInformation(
          INCORRECT_PATIENT.name(),
          REVOKED_MESSAGE
      );

      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);

      assertEquals(expectedRevokedInformation, response.revoked().revokedInformation());
    }

    @Test
    void shouldMapExternalReference() {
      final var expectedRef = new ExternalReference(EXTERNAL_REF);

      final var response = certificateEntityMapper.toDomain(CERTIFICATE_ENTITY,
          FK7211_CERTIFICATE_MODEL);
      
      assertEquals(expectedRef, response.externalReference());
    }
  }
}
