package se.inera.intyg.certificateservice.model;

import java.util.List;

public record CertificateTypeInfo(
    String id,
    String label,
    String issuerTypeId,
    String description,
    String detailedDescription,
    List<ResourceLink> links,
    String message) {

}
