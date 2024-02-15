package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.CareProvider;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.patient.model.Deceased;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.patient.model.ProtectedPerson;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateEntityMapper;

class CertificateEntityMapperTest {

  private final static Certificate CERTIFICATE = Certificate.builder()
      .id(new CertificateId("ID"))
      .created(LocalDateTime.now().minusDays(2))
      .revision(new Revision(3L))
      .build();

  private final static CertificateModel MODEL = CertificateModel.builder()
      .name("NAME")
      .id(CertificateModelId.builder()
          .version(new CertificateVersion("VERSION"))
          .type(new CertificateType("TYPE"))
          .build()
      )
      .build();

  private static final LocalDate NOW = LocalDate.now();

  private final static String JSON =
      "[{\"id\":\"F10\",\"value\":{\"type\":\"DATE\",\"date\":[" + NOW.getYear() + ","
          + NOW.getMonthValue() + "," + NOW.getDayOfMonth() + "]}}]";

  // TODO: Create test factory for these to not copy paste

  public static final CertificateEntity CERTIFICATE_ENTITY = CertificateEntity.builder()
      .revision(1L)
      .modified(LocalDateTime.now())
      .certificateId("ID")
      .created(LocalDateTime.now())
      .careProvider(
          UnitEntity.builder()
              .type(
                  UnitTypeEntity.builder()
                      .type(UnitType.CARE_PROVIDER.name())
                      .key(UnitType.CARE_PROVIDER.getKey())
                      .build()
              )
              .hsaId("HSA_ID_PROVIDER")
              .name("NAME_PROVIDER")
              .build()
      )
      .careUnit(
          UnitEntity.builder()
              .type(
                  UnitTypeEntity.builder()
                      .type(UnitType.CARE_UNIT.name())
                      .key(UnitType.CARE_UNIT.getKey())
                      .build()
              )
              .hsaId("HSA_ID_UNIT")
              .name("NAME_UNIT")
              .build()
      )
      .issuedOnUnit(
          UnitEntity.builder()
              .type(
                  UnitTypeEntity.builder()
                      .type(UnitType.SUB_UNIT.name())
                      .key(UnitType.SUB_UNIT.getKey())
                      .build()
              )
              .hsaId("HSA_ID_ISSUED")
              .name("NAME_ISSUED")
              .build()
      )
      .issuedBy(
          StaffEntity.builder()
              .name("NAME")
              .hsaId("HSA_ID")
              .build()
      )
      .patient(
          PatientEntity.builder()
              .id("ID")
              .protectedPerson(false)
              .testIndicated(false)
              .deceased(false)
              .type(PatientIdTypeEntity.builder()
                  .type(PersonIdType.PERSONAL_IDENTITY_NUMBER.name())
                  .key(1)
                  .build())
              .firstName("FIRST")
              .middleName("MIDDLE")
              .lastName("LAST")
              .build()
      )
      .data(
          new CertificateDataEntity(JSON)
      )
      .build();

  @Nested
  class ToEntity {

    @Test
    void shouldMapId() {
      final var response = CertificateEntityMapper.toEntity(CERTIFICATE);

      assertEquals(CERTIFICATE.id().id(), response.getCertificateId());
    }

    @Test
    void shouldMapCreated() {
      final var response = CertificateEntityMapper.toEntity(CERTIFICATE);

      assertEquals(CERTIFICATE.created(), response.getCreated());
    }

  }

  @Nested
  class UpdateEntity {

    @Test
    void shouldSetRevision() {
      final var response = CertificateEntityMapper.updateEntity(CERTIFICATE_ENTITY);
      assertEquals(CERTIFICATE_ENTITY.getRevision(), response.getRevision());

    }

    @Test
    void shouldSetModified() {
      final var response = CertificateEntityMapper.updateEntity(CERTIFICATE_ENTITY);
      assertEquals(CERTIFICATE_ENTITY.getModified(), response.getModified());

    }
  }

  @Nested
  class ToDomain {

    @Test
    void shouldMapId() {
      final var response = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY, MODEL);

      assertEquals(CERTIFICATE_ENTITY.getCertificateId(), response.id().id());
    }

    @Test
    void shouldMapCreated() {
      final var response = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY, MODEL);

      assertEquals(CERTIFICATE_ENTITY.getCreated(), response.created());
    }

    @Test
    void shouldMapRevision() {
      final var response = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY, MODEL);

      assertEquals(CERTIFICATE_ENTITY.getRevision(), response.revision().value());
    }

    @Test
    void shouldMapModel() {
      final var response = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY, MODEL);

      assertEquals(MODEL, response.certificateModel());
    }

    @Test
    void shouldMapCareProvider() {
      final var expected = CareProvider.builder()
          .hsaId(new HsaId("HSA_ID_PROVIDER"))
          .name(new UnitName("NAME_PROVIDER"))
          .build();

      final var response = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY, MODEL);

      assertEquals(expected, response.certificateMetaData().careProvider());
    }

    @Test
    void shouldMapCareUnit() {
      final var expected = CareUnit.builder()
          .hsaId(new HsaId("HSA_ID_UNIT"))
          .name(new UnitName("NAME_UNIT"))
          .build();

      final var response = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY, MODEL);

      assertEquals(expected, response.certificateMetaData().careUnit());
    }

    @Test
    void shouldMapIssuedOnUnitAsSubUnit() {
      final var expected = SubUnit.builder()
          .hsaId(new HsaId("HSA_ID_ISSUED"))
          .name(new UnitName("NAME_ISSUED"))
          .build();

      final var response = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY, MODEL);

      assertEquals(expected, response.certificateMetaData().issuingUnit());
    }

    @Test
    void shouldMapIssuedOnUnitAsCareUnit() {
      CERTIFICATE_ENTITY.setIssuedOnUnit(
          UnitEntity.builder()
              .type(
                  UnitTypeEntity.builder()
                      .type(UnitType.CARE_UNIT.name())
                      .key(UnitType.CARE_UNIT.getKey())
                      .build()
              )
              .hsaId("HSA_ID_ISSUED")
              .name("NAME_ISSUED")
              .build()
      );

      final var expected = CareUnit.builder()
          .hsaId(new HsaId("HSA_ID_ISSUED"))
          .name(new UnitName("NAME_ISSUED"))
          .build();

      final var response = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY, MODEL);

      assertEquals(expected, response.certificateMetaData().issuingUnit());
    }

    @Test
    void shouldMapIssuer() {
      final var expected = Staff.builder()
          .hsaId(new HsaId("HSA_ID"))
          .name(Name.builder()
              .lastName("NAME")
              .build())
          .build();

      final var response = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY, MODEL);

      assertEquals(expected, response.certificateMetaData().issuer());
    }

    @Test
    void shouldMapPatient() {
      final var expected = Patient.builder()
          .id(PersonId.builder()
              .id("ID")
              .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
              .build())
          .name(
              Name.builder()
                  .firstName("FIRST")
                  .lastName("LAST")
                  .middleName("MIDDLE")
                  .build()
          )
          .protectedPerson(new ProtectedPerson(false))
          .deceased(new Deceased(false))
          .testIndicated(new TestIndicated(false))
          .build();

      final var response = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY, MODEL);

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
                  .build())
              .build()
      );

      final var response = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY, MODEL);

      assertEquals(expected, response.elementData());
    }
  }

}