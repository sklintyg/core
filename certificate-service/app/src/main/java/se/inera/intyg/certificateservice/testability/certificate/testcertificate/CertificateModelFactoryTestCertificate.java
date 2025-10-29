package se.inera.intyg.certificateservice.testability.certificate.testcertificate;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.CategoryTestQuestions.categoryTestQuestions;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionCheckboxDateRangeList.questionCheckboxDateRangeList;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionCheckboxMultipleCodeColumns.questionCheckboxMultipleCodeColumns;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionCheckboxMultipleDate.questionCheckboxMultipleDate;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionDate.questionDate;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionDateRange.questionDateRange;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionDiagnosis.questionDiagnosis;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionDropdown.questionDropdown;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionMedicalInvestigationList.questionMedicalInvestigationList;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionMessage.questionMessage;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionRadioBoolean.questionRadioBoolean;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionRadioMultipleCode.questionRadioMultipleCode;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionTextArea.questionTextArea;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionTextField.questionTextField;
import static se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements.QuestionVisualAcuities.questionVisualAcuities;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateTypeName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryTestCertificate implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;
  @Value("${certificate.model.testcertificate.v1_0.active.from:4000-01-01T00:00:00}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetofake.logicaladdress:FAKE}")
  private String fakeLogicalAddress;

  private static final String CERTIFICATE_MODEL_ID = "CS";
  private static final String VERSION = "1.0";
  private static final String NAME = "Komponenter som stöds av Certificate Service";
  private static final String DESCRIPTION = """
      Intyg för att demonstrera vilka komponenter som stöds av Certificate Service
      """;

  private static final String DETAILED_DESCRIPTION = """
      <ul>
        <li>CHECKBOX_DATE_RANGE_LIST</li>
        <li>CHECKBOX_MULTIPLE_CODE</li>
        <li>CHECKBOX_MULTIPLE_DATE</li>
        <li>DATE</li>
        <li>DATE_RANGE</li>
        <li>DIAGNOSIS</li>
        <li>DROPDOWN</li>
        <li>MEDICAL_INVESTIGATION_LIST</li>
        <li>MESSAGE</li>
        <li>RADIO_BOOLEAN</li>
        <li>RADIO_MULTIPLE_CODE</li>
        <li>TEXT_AREA</li>
        <li>TEXT_FIELD</li>
        <li>VISUAL_ACUITIES</li>
        <li>ISSUING_UNIT</li>
      </ul>
      """;

  public static final CertificateModelId TEST_CERTIFICATE_V1 = CertificateModelId.builder()
      .type(new CertificateType(CERTIFICATE_MODEL_ID))
      .version(new CertificateVersion(VERSION))
      .build();

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(TEST_CERTIFICATE_V1)
        .type(
            new Code(
                "testCertificate",
                "test",
                NAME
            )
        )
        .typeName(new CertificateTypeName(CERTIFICATE_MODEL_ID))
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(false)
        .certificateActionSpecifications(TestCertificateCertificateActionSpecification.create())
        .messageActionSpecifications(Collections.emptyList())
        .elementSpecifications(List.of(
            categoryTestQuestions(
                "KAT_1",
                "Följande komponenter finns i Certificate Service",
                questionCheckboxDateRangeList()
            ),
            categoryTestQuestions("KAT_2", "", questionCheckboxMultipleCodeColumns()),
            categoryTestQuestions("KAT_3", "", questionCheckboxMultipleDate()),
            categoryTestQuestions("KAT_4", "", questionDate()),
            categoryTestQuestions("KAT_5", "", questionDateRange()),
            categoryTestQuestions("KAT_6", "", questionDiagnosis()),
            categoryTestQuestions("KAT_7", "", questionDropdown()),
            categoryTestQuestions("KAT_8", "", questionMedicalInvestigationList()),
            categoryTestQuestions("KAT_9", "", questionMessage()),
            categoryTestQuestions("KAT_10", "", questionRadioBoolean()),
            categoryTestQuestions("KAT_11", "", questionRadioMultipleCode()),
            categoryTestQuestions("KAT_12", "", questionTextArea()),
            categoryTestQuestions("KAT_13", "", questionTextField()),
            categoryTestQuestions("KAT_14", "", questionVisualAcuities()),
            issuingUnitContactInfo()
        ))
        .recipient(CertificateRecipientFactory.transp(fakeLogicalAddress))
        .certificateActionFactory(certificateActionFactory)
        .build();
  }
}