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
    @Type(value = CertificateDataConfigTextField.class, name = "UE_TEXTFIELD"),
    @Type(value = CertificateDataConfigCheckboxDateRangeList.class, name = "UE_CHECKBOX_DATE_RANGE_LIST"),
    @Type(value = CertificateDataConfigCheckboxMultipleDate.class, name = "UE_CHECKBOX_MULTIPLE_DATE"),
    @Type(value = CertificateDataConfigRadioMultipleCode.class, name = "UE_RADIO_MULTIPLE_CODE"),
    @Type(value = CertificateDataConfigRadioBoolean.class, name = "UE_RADIO_BOOLEAN"),
    @Type(value = CertificateDataConfigMessage.class, name = "UE_MESSAGE"),
    @Type(value = CertificateDataConfigMedicalInvestigation.class, name = "UE_MEDICAL_INVESTIGATION"),
    @Type(value = CertificateDataConfigDiagnoses.class, name = "UE_DIAGNOSES"),
    @Type(value = CertificateDataConfigCheckboxMultipleCode.class, name = "UE_CHECKBOX_MULTIPLE_CODE"),
    @Type(value = CertificateDataConfigVisualAcuity.class, name = "UE_VISUAL_ACUITY"),
    @Type(value = CertificateDataConfigDateRange.class, name = "UE_DATE_RANGE"),
    @Type(value = CertificateDataConfigDropdown.class, name = "UE_DROPDOWN"),
    @Type(value = CertificateDataConfigInteger.class, name = "UE_INTEGER"),
    @Type(value = CertificateDataConfigCheckboxBoolean.class, name = "UE_CHECKBOX_BOOLEAN"),
    @Type(value = CertificateDataConfigIcf.class, name = "UE_ICF")
})

public interface CertificateDataConfig {

  CertificateDataConfigType getType();

  String getHeader();

  String getLabel();

  String getIcon();

  String getText();

  String getDescription();

  Accordion getAccordion();

  Message getMessage();
}
