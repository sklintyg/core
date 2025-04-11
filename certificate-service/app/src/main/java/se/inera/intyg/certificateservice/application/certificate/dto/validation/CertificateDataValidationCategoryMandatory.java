package se.inera.intyg.certificateservice.application.certificate.dto.validation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationCategoryMandatory.CertificateDataValidationCategoryMandatoryBuilder;

@JsonDeserialize(builder = CertificateDataValidationCategoryMandatoryBuilder.class)
@Value
@Builder
public class CertificateDataValidationCategoryMandatory implements CertificateDataValidation {

    @Getter(onMethod = @__(@Override))
    CertificateDataValidationType type = CertificateDataValidationType.CATEGORY_MANDATORY_VALIDATION;
    ExpressionTypeEnum expressionType;
    List<CertificateDataValidationMandatory> questions;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CertificateDataValidationCategoryMandatoryBuilder {

    }
}