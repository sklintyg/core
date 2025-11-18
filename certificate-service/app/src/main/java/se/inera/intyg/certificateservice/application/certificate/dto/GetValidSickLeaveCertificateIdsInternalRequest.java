package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetValidSickLeaveCertificateIdsInternalRequest.GetValidSickLeaveCertificateIdsInternalRequestBuilder;

@JsonDeserialize(builder = GetValidSickLeaveCertificateIdsInternalRequestBuilder.class)
@Value
@Builder
public class GetValidSickLeaveCertificateIdsInternalRequest {

    List<String> certificateIds;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GetValidSickLeaveCertificateIdsInternalRequestBuilder {

    }

}