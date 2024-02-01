package se.inera.intyg.certificateservice.application.certificate.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;

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
                                .inactive()
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
                            certificate.certificateMetaData().getIssuer().getName().getFullName()
                        )
                        .build()
                )
                .forwarded(FORWARDED)
                .latestMajorVersion(LATEST_MAJOR_VERSION)
                .sent(SENT)
                .status(STATUS)
                .testCertificate(TEST_CERTIFICATE)
                .version(VERSION)
                .build()
        )
        .build();
  }

  private PatientDTO toPatientDTO(Certificate certificate) {
    return PatientDTO.builder()
        .id(
            PersonIdDTO.builder()
                .id(certificate.certificateMetaData().getPatient().getId().getId())
                .type(
                    toPersonIdTypeDTO(
                        certificate.certificateMetaData().getPatient().getId()
                            .getType()
                    )
                )
                .build()
        )
        .firstName(certificate.certificateMetaData().getPatient().getName().getFirstName())
        .middleName(certificate.certificateMetaData().getPatient().getName().getMiddleName())
        .lastName(certificate.certificateMetaData().getPatient().getName().getLastName())
        .fullName(certificate.certificateMetaData().getPatient().getName().getFullName())
        .street(certificate.certificateMetaData().getPatient().getAddress().getStreet())
        .city(certificate.certificateMetaData().getPatient().getAddress().getCity())
        .zipCode(certificate.certificateMetaData().getPatient().getAddress().getZipCode())
        .build();
  }

  private PersonIdTypeDTO toPersonIdTypeDTO(PersonIdType type) {
    return switch (type) {
      case PERSONAL_IDENTITY_NUMBER -> PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER;
      case COORDINATION_NUMBER -> PersonIdTypeDTO.COORDINATION_NUMBER;
    };
  }
}
