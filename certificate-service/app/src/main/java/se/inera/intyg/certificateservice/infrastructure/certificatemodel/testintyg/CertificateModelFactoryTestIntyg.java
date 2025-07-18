package se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.CategoryTestQuestions.categoryTestQuestions;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionCheckboxDateRangeList.questionCheckboxDateRangeList;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionCheckboxMultipleCodeColumns.questionCheckboxMultipleCodeColumns;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionCheckboxMultipleCodeRows.questionCheckboxMultipleCodeRows;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionCheckboxMultipleDate.questionCheckboxMultipleDate;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionDateRange.questionDateRange;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionDiagnosis.questionDiagnosis;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionDropdown.questionDropdown;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionMedicalInvestigationList.questionMedicalInvestigationList;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionMessage.questionMessage;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionRadioBoolean.questionRadioBoolean;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionRadioMultipleCode.questionRadioMultipleCode;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionTextArea.questionTextArea;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionTextField.questionTextField;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements.QuestionVisualAcuities.questionVisualAcuities;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryTestIntyg implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;
  @Value("${certificate.model.testintyg.v1_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetofake.logicaladdress:FAKE}")
  private String fakeLogicalAddress;

  private static final String TESTINTYG = "testintyg";
  private static final String VERSION = "1.0";
  private static final String NAME = "Demo Intyg för Certificate Service";
  private static final String DESCRIPTION = """
      Testintyg för att demonstrera vilka komponenter vi har i Certificate Service
      
      Uppdaterad 2025-07-17
      
      Bör uppdateras med varje ny komponent som läggs till i Certificate Service.
      """;

  private static final String DETAILED_DESCRIPTION = """
      <b className="iu-fw-heading">Demo Intyg för Certificate Service</b>
      <br>
      </p><b className="iu-fw-heading">Följande komponenter finns här:</b>
      <ul>
        <li>CATEGORY</li>
        <li>DATE</li>
        <li>ISSUING_UNIT</li>
        <li>TEXT_AREA</li>
        <li>CHECKBOX_DATE_RANGE_LIST</li>
        <li>CHECKBOX_MULTIPLE_DATE</li>
        <li>RADIO_MULTIPLE_CODE</li>
        <li>RADIO_BOOLEAN</li>
        <li>MESSAGE</li>
        <li>DIAGNOSIS</li>
        <li>TEXT_FIELD</li>
        <li>MEDICAL_INVESTIGATION_LIST</li>
        <li>CHECKBOX_MULTIPLE_CODE</li>
        <li>VISUAL_ACUITIES</li>
        <li>DROPDOWN</li>
        <li>DATE_RANGE</li>
      </ul>
      """;

  private static final String PREAMBLE_TEXT =
      "Det här är ditt intyg. Intyget innehåller all information som någon fyllt i. Du kan inte ändra något i ditt intyg. "
          + "Har du frågor kontaktar du INGEN för detta är ett demointyg.";

  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "testintyg/schematron/luas.v1.sch");

  public static final CertificateModelId TestIntyg_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(TESTINTYG))
      .version(new CertificateVersion(VERSION))
      .build();

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(TestIntyg_V1_0)
        .type(
            new Code(
                "Testintyg",
                "test",
                NAME
            )
        )
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION.replace("\n", ""))
        .activeFrom(activeFrom)
        .availableForCitizen(true)
        .texts(
            List.of(
                CertificateText.builder()
                    .text(PREAMBLE_TEXT)
                    .type(CertificateTextType.PREAMBLE_TEXT)
                    .links(Collections.emptyList())
                    .build()
            )
        )
        .messageTypes(List.of(
            CertificateMessageType.builder()
                .type(MessageType.MISSING)
                .subject(new Subject(MessageType.MISSING.displayName()))
                .build(),
            CertificateMessageType.builder()
                .type(MessageType.CONTACT)
                .subject(new Subject(MessageType.CONTACT.displayName()))
                .build(),
            CertificateMessageType.builder()
                .type(MessageType.OTHER)
                .subject(new Subject(MessageType.OTHER.displayName()))
                .build()
        ))
        .certificateActionSpecifications(TestIntygCertificateActionSpecification.create())
        .messageActionSpecifications(TestIntygMessageActionSpecification.create())
        .elementSpecifications(List.of(
            categoryTestQuestions(
                questionCheckboxDateRangeList(),
                questionCheckboxMultipleCodeRows(),
                questionCheckboxMultipleCodeColumns(),
                questionCheckboxMultipleDate(),
                questionDateRange(),
                questionDiagnosis(),
                questionDropdown(),
                questionMedicalInvestigationList(),
                questionMessage(),
                questionRadioBoolean(),
                questionRadioMultipleCode(),
                questionTextArea(),
                questionTextField(),
                questionVisualAcuities()
            ),

            issuingUnitContactInfo()
        ))
        .recipient(CertificateRecipientFactory.transp(fakeLogicalAddress))
        .certificateActionFactory(certificateActionFactory)
        .build();
  }
}
