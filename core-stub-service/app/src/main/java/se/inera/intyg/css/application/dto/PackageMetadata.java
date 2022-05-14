package se.inera.intyg.css.application.dto;

public record PackageMetadata(String organizationName,
                              String organizationNumber,
                              String delegatePnr,
                              String sourceSystem,
                              String receiptUrl) {

}
