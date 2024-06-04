package se.inera.intyg.certificateservice.application.certificate.dto.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = CertificateDataConfigCategory.class, name = "CATEGORY"),
    @Type(value = CertificateDataConfigDate.class, name = "UE_DATE"),
    @Type(value = CertificateDataConfigTextArea.class, name = "UE_TEXTAREA"),
    @Type(value = CertificateDataConfigCheckboxDateRangeList.class, name = "UE_CHECKBOX_DATE_RANGE_LIST"),
    @Type(value = CertificateDataConfigCheckboxMultipleDate.class, name = "UE_CHECKBOX_MULTIPLE_DATE")
})
public interface CertificateDataConfig {

  CertificateDataConfigType getType();

  String getHeader();

  String getLabel();

  String getIcon();

  String getText();

  String getDescription();

  Accordion getAccordion();

}
