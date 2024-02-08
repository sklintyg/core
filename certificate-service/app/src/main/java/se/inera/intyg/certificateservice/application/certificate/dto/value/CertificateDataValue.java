package se.inera.intyg.certificateservice.application.certificate.dto.value;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = CertificateDataValueDate.class, name = "DATE"),
})
public interface CertificateDataValue {

  CertificateDataValueType getType();
}
