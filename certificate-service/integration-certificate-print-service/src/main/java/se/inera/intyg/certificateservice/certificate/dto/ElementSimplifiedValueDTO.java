package se.inera.intyg.certificateservice.certificate.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = ElementSimplifiedValueTextDTO.class, name = "TEXT"),
    @Type(value = ElementSimplifiedValueListDTO.class, name = "LIST"),
    @Type(value = ElementSimplifiedValueTableDTO.class, name = "TABLE")
})

public interface ElementSimplifiedValueDTO {

}
