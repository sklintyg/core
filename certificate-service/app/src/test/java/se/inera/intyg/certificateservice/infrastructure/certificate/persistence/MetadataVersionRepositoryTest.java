package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

class MetadataVersionRepositoryTest {

  //    @Test
//    void shouldThrowExceptionIfIdIsNull() {
//      assertThrows(IllegalArgumentException.class,
//          () -> jpaCertificateRepository.getByIdForPrint(null));
//    }
//
//    @Test
//    void shouldThrowExceptionIfCertificateIsNull() {
//      final var id = new CertificateId("ID");
//      assertThrows(IllegalArgumentException.class,
//          () -> jpaCertificateRepository.getByIdForPrint(id)
//      );
//    }
//
//    @Test
//    void shouldReturnCertificateFromRepository() {
//      stubDomain();
//
//      when(certificateEntityRepository.findByCertificateId("ID"))
//          .thenReturn(Optional.of(SIGNED_CERTIFICATE_ENTITY));
//
//      final var response = jpaCertificateRepository.getByIdForPrint(new CertificateId("ID"));
//
//      assertEquals(EXPECTED_CERTIFICATE, response);
//    }
//
//    @Test
//    void shouldReturnVersionedStaffFromRepository() {
//      stubDomain();
//
//      final var ISSUED_BY_OLD_NAME_ENTITY = StaffVersionEntity.builder()
//          .firstName("OLD")
//          .middleName("OLD")
//          .lastName("OLD")
//          .hsaId("HSA_ID")
//          .validTo(TIMESTAMP)
//          .build();
//
//      when(certificateEntityRepository.findByCertificateId("ID"))
//          .thenReturn(Optional.of(MUTABLE_SIGNED_CERTIFICATE_ENTITY));
//
//      when(staffVersionEntityRepository.findCoveringTimestampOrderByMostRecent(
//          ISSUED_BY_ENTITY.getHsaId(),
//          SIGNED_CERTIFICATE_ENTITY.getSigned())).thenReturn(List.of(ISSUED_BY_OLD_NAME_ENTITY));
//
//      final var response = jpaCertificateRepository.getByIdForPrint(new CertificateId("ID"));
//      assertEquals(EXPECTED_CERTIFICATE_VERSIONED.certificateMetaData().issuer(),
//          response.certificateMetaData().issuer());
//    }
//
//
//    @Test
//    void shouldReturnVersionedPatientFromRepository() {
//      stubDomain();
//
//      final var PATIENT_ENTITY_OLD_NAME = PatientVersionEntity.builder()
//          .id("ID")
//          .protectedPerson(false)
//          .testIndicated(false)
//          .deceased(false)
//          .type(PatientIdTypeEntity.builder()
//              .type(PersonIdType.PERSONAL_IDENTITY_NUMBER.name())
//              .key(1)
//              .build())
//          .firstName("OLD")
//          .middleName("OLD")
//          .lastName("OLD")
//          .validTo(TIMESTAMP)
//          .build();
//
//      when(certificateEntityRepository.findByCertificateId("ID"))
//          .thenReturn(Optional.of(MUTABLE_SIGNED_CERTIFICATE_ENTITY));
//
//      when(patientVersionEntityRepository
//          .findCoveringTimestampOrderByMostRecent(PATIENT.id().idWithoutDash(),
//              SIGNED_CERTIFICATE_ENTITY.getSigned())).thenReturn(List.of(PATIENT_ENTITY_OLD_NAME));
//
//      final var response = jpaCertificateRepository.getByIdForPrint(new CertificateId("ID"));
//      assertEquals(EXPECTED_CERTIFICATE_VERSIONED.certificateMetaData().patient(),
//          response.certificateMetaData().patient());
//    }
//
//    @Test
//    void shouldReturnVersionedUnitFromRepository() {
//
//      stubDomain();
//
//      final var ISSUED_ON_UNIT_ENTITY_OLD_NAME = UnitVersionEntity.builder()
//          .type(
//              UnitTypeEntity.builder()
//                  .type(UnitType.SUB_UNIT.name())
//                  .key(UnitType.SUB_UNIT.getKey())
//                  .build()
//          )
//          .hsaId("HSA_ID_ISSUED")
//          .name("OLD")
//          .validTo(TIMESTAMP)
//          .build();
//
//      when(certificateEntityRepository.findByCertificateId("ID"))
//          .thenReturn(Optional.of(MUTABLE_SIGNED_CERTIFICATE_ENTITY));
//
//      when(unitVersionEntityRepository
//          .findCoveringTimestampOrderByMostRecent(ISSUED_ON_UNIT_ENTITY.getHsaId(),
//              SIGNED_CERTIFICATE_ENTITY.getSigned())).thenReturn(
//          List.of(ISSUED_ON_UNIT_ENTITY_OLD_NAME));
//
//      final var response = jpaCertificateRepository.getByIdForPrint(new CertificateId("ID"));
//      assertEquals(EXPECTED_CERTIFICATE_VERSIONED.certificateMetaData().issuingUnit(),
//          response.certificateMetaData().issuingUnit());
//    }

}