package se.inera.intyg.cts.application.dto;

public record CreateTerminationDTO(String creatorHSAId,
                                   String creatorName,
                                   String hsaId,
                                   String organizationalNumber,
                                   String personId,
                                   String phoneNumber) {

}
