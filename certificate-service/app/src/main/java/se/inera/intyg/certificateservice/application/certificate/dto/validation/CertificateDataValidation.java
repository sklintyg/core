package se.inera.intyg.certificateservice.application.certificate.dto.validation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = CertificateDataValidationMandatory.class, name = "MANDATORY_VALIDATION"),
    @Type(value = CertificateDataValidationCategoryMandatory.class, name = "CATEGORY_MANDATORY_VALIDATION"),
    @Type(value = CertificateDataValidationText.class, name = "TEXT_VALIDATION"),
    @Type(value = CertificateDataValidationShow.class, name = "SHOW_VALIDATION"),
    @Type(value = CertificateDataValidationDisable.class, name = "DISABLE_VALIDATION"),
    @Type(value = CertificateDataValidationDisableSubElement.class, name = "DISABLE_SUB_ELEMENT_VALIDATION"),
    @Type(value = CertificateDataValidationHide.class, name = "HIDE_VALIDATION"),
    @Type(value = CertificateDataValidationAutoFill.class, name = "AUTO_FILL_VALIDATION"),
})
public interface CertificateDataValidation {

  CertificateDataValidationType getType();
}