package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.TotalExportsInternalResponse.TotalExportsInternalResponseDTOBuilder;

@JsonDeserialize(builder = TotalExportsInternalResponseDTOBuilder.class)
@Value
@Builder
public class TotalExportsInternalResponse {

    long totalCertificates;
    long totalRevokedCertificates;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TotalExportsInternalResponseDTOBuilder {

    }
}