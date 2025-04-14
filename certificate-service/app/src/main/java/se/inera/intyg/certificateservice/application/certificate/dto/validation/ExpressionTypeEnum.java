package se.inera.intyg.certificateservice.application.certificate.dto.validation;

public enum ExpressionTypeEnum {
    OR;


    public static ExpressionTypeEnum fromValue(final String value) {
        return ExpressionTypeEnum.valueOf(value.toUpperCase());
    }
}