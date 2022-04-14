package se.inera.intyg.cts.infrastructure.persistence.entity;

import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationBuilder;
import se.inera.intyg.cts.domain.model.TerminationStatus;

public class TerminationEntityMapper {

  public static TerminationEntity toEntity(Termination termination) {
    return new TerminationEntity(
        termination.terminationId().id(),
        termination.created(),
        termination.creator().hsaId().id(),
        termination.creator().name(),
        termination.careProvider().hsaId().id(),
        termination.careProvider().organizationNumber().number(),
        termination.export().organizationRepresentative().personId().id(),
        termination.export().organizationRepresentative().phoneNumber().number(),
        termination.status().name());
  }

  public static Termination toDomain(TerminationEntity terminationEntity) {
    return TerminationBuilder.getInstance()
        .terminationId(terminationEntity.getTerminationId())
        .created(terminationEntity.getCreated())
        .creatorHSAId(terminationEntity.getCreatorHSAId())
        .creatorName(terminationEntity.getCreatorName())
        .careProviderHSAId(terminationEntity.getHsaId())
        .careProviderOrganizationNumber(terminationEntity.getOrganizationNumber())
        .careProviderOrganizationRepresentativePersonId(
            terminationEntity.getPersonId())
        .careProviderOrganizationRepresentativePhoneNumber(terminationEntity.getPhoneNumber())
        .status(TerminationStatus.valueOf(terminationEntity.getStatus()))
        .create();
  }
}
