package se.inera.intyg.certificateservice.application.certificate.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationsDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Component
public class CertificateConverter {

  /**
   * Default values until functionality has been implemented in domain model
   */
  private static final boolean FORWARDED = false;
  private static final boolean LATEST_MAJOR_VERSION = true;
  private static final boolean SENT = false;
  private static final CertificateStatusTypeDTO STATUS = CertificateStatusTypeDTO.UNSIGNED;
  private static final boolean TEST_CERTIFICATE = false;
  private static final int VERSION = 0;
  private static final CertificateRelationsDTO RELATIONS = CertificateRelationsDTO.builder()
      .build();

  public CertificateDTO convert(Certificate certificate) {
    return CertificateDTO.builder()
        .metadata(
            CertificateMetadataDTO.builder()
                .id(certificate.id().id())
                .type(certificate.certificateModel().getId().getType().type())
                .typeName(certificate.certificateModel().getId().getType().type())
                .typeVersion(certificate.certificateModel().getId().getVersion().version())
                .name(certificate.certificateModel().getName())
                .description(certificate.certificateModel().getDescription())
                .created(certificate.created())
                .patient(
                    toPatientDTO(certificate)
                )
                .unit(
                    UnitDTO.builder()
                        .unitId(certificate.certificateMetaData().getIssuingUnit().getHsaId().id())
                        .unitName(
                            certificate.certificateMetaData().getIssuingUnit().getName().name()
                        )
                        .address(
                            certificate.certificateMetaData().getIssuingUnit().getAddress()
                                .getAddress()
                        )
                        .city(
                            certificate.certificateMetaData().getIssuingUnit().getAddress()
                                .getCity()
                        )
                        .zipCode(
                            certificate.certificateMetaData().getIssuingUnit().getAddress()
                                .getZipCode()
                        )
                        .phoneNumber(
                            certificate.certificateMetaData().getIssuingUnit().getContactInfo()
                                .getPhoneNumber()
                        )
                        .email(
                            certificate.certificateMetaData().getIssuingUnit().getContactInfo()
                                .getEmail()
                        )
                        .isInactive(
                            certificate.certificateMetaData().getIssuingUnit().getInactive()
                                .value()
                        )
                        .build()
                )
                .careUnit(
                    UnitDTO.builder()
                        .unitId(certificate.certificateMetaData().getCareUnit().getHsaId().id())
                        .unitName(
                            certificate.certificateMetaData().getCareUnit().getName().name()
                        )
                        .build()
                )
                .careProvider(
                    UnitDTO.builder()
                        .unitId(certificate.certificateMetaData().getCareProvider().getHsaId().id())
                        .unitName(
                            certificate.certificateMetaData().getCareProvider().getName().name()
                        )
                        .build()
                )
                .issuedBy(
                    StaffDTO.builder()
                        .personId(
                            certificate.certificateMetaData().getIssuer().getHsaId().id()
                        )
                        .fullName(
                            certificate.certificateMetaData().getIssuer().getName().fullName()
                        )
                        .build()
                )
                .forwarded(FORWARDED)
                .latestMajorVersion(LATEST_MAJOR_VERSION)
                .sent(SENT)
                .status(STATUS)
                .testCertificate(TEST_CERTIFICATE)
                .version(VERSION)
                .relations(RELATIONS)
                .build()
        )
        .build();
  }

  private PatientDTO toPatientDTO(Certificate certificate) {
    final var patient = certificate.certificateMetaData().getPatient();
    return PatientDTO.builder()
        .personId(
            PersonIdDTO.builder()
                .id(patient.getId().getId())
                .type(patient.getId().getType().name())
                .build()
        )
        .firstName(patient.getName().getFirstName())
        .middleName(patient.getName().getMiddleName())
        .lastName(patient.getName().getLastName())
        .fullName(patient.getName().fullName())
        .street(patient.getAddress().getStreet())
        .city(patient.getAddress().getCity())
        .zipCode(patient.getAddress().getZipCode())
        .testIndicated(patient.getTestIndicated().value())
        .deceased(patient.getDeceased().value())
        .protectedPerson(patient.getProtectedPerson().value())
        .build();
  }
}
