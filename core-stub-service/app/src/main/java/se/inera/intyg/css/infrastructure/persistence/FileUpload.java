package se.inera.intyg.css.infrastructure.persistence;

import se.inera.intyg.css.application.dto.PackageMetadata;

public record FileUpload(PackageMetadata packageMetadata, byte[] file) {

}
