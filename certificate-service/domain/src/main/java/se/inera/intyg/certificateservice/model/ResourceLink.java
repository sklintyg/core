package se.inera.intyg.certificateservice.model;

public record ResourceLink(
    ResourceLinkType type,
    String name,
    String description,
    String body,
    boolean enabled,
    String title
) {

}
