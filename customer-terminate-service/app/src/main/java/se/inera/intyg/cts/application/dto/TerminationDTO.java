package se.inera.intyg.cts.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public record TerminationDTO(UUID terminationId,
                             @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime created, //JSON formating for JAVA 8 types
                             String creatorHSAId,
                             String creatorName,
                             String status,
                             String hsaId,
                             String organizationNumber,
                             String personId,
                             String phoneNumber) {

}
