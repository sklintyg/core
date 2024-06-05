package se.inera.intyg.certificateservice.application.certificate.dto.value;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = CertificateDataValueDate.class, name = "DATE"),
    @Type(value = CertificateDataValueText.class, name = "TEXT"),
    @Type(value = CertificateDataValueDateRange.class, name = "DATE_RANGE"),
    @Type(value = CertificateDataValueDateRangeList.class, name = "DATE_RANGE_LIST"),
    @Type(value = CertificateDataValueDateList.class, name = "DATE_LIST"),
    @Type(value = CertificateDataValueCode.class, name = "CODE")
})
public interface CertificateDataValue {

  CertificateDataValueType getType();
}
