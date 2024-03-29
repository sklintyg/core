package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
    {
        @JsonSubTypes.Type(value = MappedElementValueDate.class, name = "DATE"),
        @JsonSubTypes.Type(value = MappedElementValueIssuingUnit.class, name = "ISSUING_UNIT"),
    }
)
public interface MappedElementValue {

}
