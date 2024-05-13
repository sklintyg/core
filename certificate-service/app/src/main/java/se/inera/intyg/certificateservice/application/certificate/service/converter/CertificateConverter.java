package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRecipientDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationsDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

@Component
@RequiredArgsConstructor
public class CertificateConverter {

  /**
   * Default values until functionality has been implemented in domain model
   */
  private static final boolean FORWARDED = false;
  private static final boolean LATEST_MAJOR_VERSION = true;
  private static final boolean TEST_CERTIFICATE = false;
  private static final CertificateRelationsDTO RELATIONS = CertificateRelationsDTO.builder()
      .build();
  private final CertificateDataConverter certificateDataConverter;
  private final CertificateUnitConverter certificateUnitConverter;

  public CertificateDTO convert(Certificate certificate, List<ResourceLinkDTO> resourceLinks) {
    return CertificateDTO.builder()
        .metadata(
            CertificateMetadataDTO.builder()
                .id(certificate.id().id())
                .type(certificate.certificateModel().id().type().type())
                .version(certificate.revision().value())
                .typeName(certificate.certificateModel().id().type().type())
                .typeVersion(certificate.certificateModel().id().version().version())
                .name(certificate.certificateModel().name())
                .description(certificate.certificateModel().description())
                .validForSign(certificate.isDraft() && certificate.validate().isValid())
                .created(certificate.created())
                .patient(
                    toPatientDTO(certificate)
                )
                .unit(
                    certificateUnitConverter.convert(
                        certificate.certificateMetaData().issuingUnit(),
                        certificate.elementData().stream()
                            .filter(data -> data.id().equals(UNIT_CONTACT_INFORMATION))
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
                        .firstName(
                            certificate.certificateMetaData().issuer().name().firstName()
                        )
                        .middleName(
                            certificate.certificateMetaData().issuer().name().middleName()
                        )
                        .lastName(
                            certificate.certificateMetaData().issuer().name().lastName()
                        )
                        .fullName(
                            certificate.certificateMetaData().issuer().name().fullName()
                        )
                        .build()
                )
                .forwarded(FORWARDED)
                .latestMajorVersion(LATEST_MAJOR_VERSION)
                .sent(certificate.sent() != null)
                .sentTo(certificate.sent() != null ? certificate.sent().recipient().name() : null)
                .recipient(toCertificateRecipientDTO(certificate))
                .status(toCertificateStatusTypeDTO(certificate.status()))
                .testCertificate(TEST_CERTIFICATE)
                .relations(RELATIONS)
                .signed(certificate.signed())
                .modified(certificate.modified())
                .externalReference(
                    certificate.externalReference() != null
                        ? certificate.externalReference().value() : null
                )
                .relations(
                    toRelations(certificate.parent(), certificate.children())
                )
                .build()
        )
        .data(
            certificateDataConverter.convert(certificate)
        )
        .links(resourceLinks)
        .build();
  }

  private CertificateRelationsDTO toRelations(Relation parent, List<Relation> children) {
    return CertificateRelationsDTO.builder()
        .parent(
            toRelation(parent)
        )
        .children(
            children.stream()
                .filter(Objects::nonNull)
                .filter(relation -> !Status.REVOKED.equals(relation.certificate().status()))
                .map(this::toRelation)
                .toList()
        )
        .build();
  }

  private CertificateRelationDTO toRelation(Relation relation) {
    if (relation == null) {
      return null;
    }

    return CertificateRelationDTO.builder()
        .certificateId(relation.certificate().id().id())
        .type(CertificateRelationTypeDTO.toType(relation.type()))
        .status(toCertificateStatusTypeDTO(relation.certificate().status()))
        .created(relation.created())
        .build();
  }

  private CertificateRecipientDTO toCertificateRecipientDTO(Certificate certificate) {
    final var sent = certificate.sent();
    if (sent == null) {
      return CertificateRecipientDTO.builder()
          .id(certificate.certificateModel().recipient().id().id())
          .name(certificate.certificateModel().recipient().name())
          .build();
    }

    return CertificateRecipientDTO.builder()
        .id(sent.recipient().id().id())
        .name(sent.recipient().name())
        .sent(sent.sentAt())
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

  private CertificateStatusTypeDTO toCertificateStatusTypeDTO(Status status) {
    return switch (status) {
      case DRAFT, DELETED_DRAFT -> CertificateStatusTypeDTO.UNSIGNED;
      case LOCKED_DRAFT -> CertificateStatusTypeDTO.LOCKED;
      case SIGNED -> CertificateStatusTypeDTO.SIGNED;
      case REVOKED -> CertificateStatusTypeDTO.REVOKED;
    };
  }
}
