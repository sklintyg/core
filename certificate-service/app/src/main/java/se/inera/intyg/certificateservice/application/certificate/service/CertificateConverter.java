package se.inera.intyg.certificateservice.application.certificate.service;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIssuingUnit.ISSUING_UNIT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationsDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Component
@RequiredArgsConstructor
public class CertificateConverter {

  /**
   * Default values until functionality has been implemented in domain model
   */
  private static final boolean FORWARDED = false;
  private static final boolean LATEST_MAJOR_VERSION = true;
  private static final boolean SENT = false;
  private static final CertificateStatusTypeDTO STATUS = CertificateStatusTypeDTO.UNSIGNED;
  private static final boolean TEST_CERTIFICATE = false;
  private static final CertificateRelationsDTO RELATIONS = CertificateRelationsDTO.builder()
      .build();
  private final CertificateDataConverter certificateDataConverter;
  private final CertificateMetaDataUnitConverter certificateMetaDataUnitConverter;

  public CertificateDTO convert(Certificate certificate, List<ResourceLinkDTO> resourceLinks) {
    return CertificateDTO.builder()
        .metadata(
            CertificateMetadataDTO.builder()
                .id(certificate.id().id())
                .type(certificate.certificateModel().id().type().type())
                .version(certificate.version())
                .typeName(certificate.certificateModel().id().type().type())
                .typeVersion(certificate.certificateModel().id().version().version())
                .name(certificate.certificateModel().name())
                .description(certificate.certificateModel().description())
                .created(certificate.created())
                .patient(
                    toPatientDTO(certificate)
                )
                .unit(
                    certificateMetaDataUnitConverter.convert(
                        certificate.certificateMetaData().issuingUnit(),
                        certificate.elementData().stream()
                            .filter(data -> data.id().equals(ISSUING_UNIT))
                            .findFirst()
                    )
                )
                .careUnit(
                    UnitDTO.builder()
                        .unitId(certificate.certificateMetaData().careUnit().hsaId().id())
                        .unitName(
                            certificate.certificateMetaData().careUnit().name().name()
                        )
                        .build()
                )
                .careProvider(
                    UnitDTO.builder()
                        .unitId(certificate.certificateMetaData().careProvider().hsaId().id())
                        .unitName(
                            certificate.certificateMetaData().careProvider().name().name()
                        )
                        .build()
                )
                .issuedBy(
                    StaffDTO.builder()
                        .personId(
                            certificate.certificateMetaData().issuer().hsaId().id()
                        )
                        .fullName(
                            certificate.certificateMetaData().issuer().name().fullName()
                        )
                        .build()
                )
                .forwarded(FORWARDED)
                .latestMajorVersion(LATEST_MAJOR_VERSION)
                .sent(SENT)
                .status(STATUS)
                .testCertificate(TEST_CERTIFICATE)
                .relations(RELATIONS)
                .build()
        )
        .data(
            certificateDataConverter.convert(
                certificate.certificateModel(),
                certificate.elementData()
            )
        )
        .links(resourceLinks)
        .build();
  }

  private PatientDTO toPatientDTO(Certificate certificate) {
    final var patient = certificate.certificateMetaData().patient();
    return PatientDTO.builder()
        .personId(
            PersonIdDTO.builder()
                .id(patient.id().id())
                .type(patient.id().type().name())
                .build()
        )
        .firstName(patient.name().firstName())
        .middleName(patient.name().middleName())
        .lastName(patient.name().lastName())
        .fullName(patient.name().fullName())
        .street(patient.address() != null ? patient.address().street() : "")
        .city(patient.address() != null ? patient.address().city() : "")
        .zipCode(patient.address() != null ? patient.address().zipCode() : "")
        .testIndicated(patient.testIndicated().value())
        .deceased(patient.deceased().value())
        .protectedPerson(patient.protectedPerson().value())
        .build();
  }
}
