package se.inera.intyg.cts.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TerminationDTO(UUID terminationId,
                             LocalDateTime created,
                             String creatorHSAId,
                             String creatorName,
                             String status,
                             String hsaId,
                             String organizationNumber,
                             String personId,
                             String phoneNumber) {

}
