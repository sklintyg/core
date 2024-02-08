package se.inera.intyg.certificateservice.application.certificate.dto.validation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = CertificateDataValidationMandatory.class, name = "MANDATORY_VALIDATION"),
})
public interface CertificateDataValidation {

  CertificateDataValidationType getType();
}