package se.inera.intyg.certificateservice.prefill.service;

import java.util.Map;
import java.util.Objects;
import model.AIPrefillResponse;
import model.AIPrefillValueCodeList;
import model.AIPrefillValueDate;
import model.AIPrefillValueDateList;
import model.AIPrefillValueDiagnosisList;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import model.AIPrefillValue;
import model.AIPrefillValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Service
public class PrefillService {

  private static final String SYSTEM_PROMPT = "Generate a map placing the question ids as the key. The value you need to extract from the prompt. In the prompt you will recieve a certificatemodel which has different questions defined with an id and a question name. Using the question name you will extract data from the text, which is electronic health records for the patient, and set as the value in the map. The question id is the key. Do not use the interface AIPrefillValue, that is not an object with a value. If you don't find fitting value type - skip the question.";
  private static final String ONLY_TEXT_PROMPT = "The value will be strings so if you in the model find something that is not a string, skip that value and dont add it to the map.";
  private static final String MORE_VALUES_PROMPT = "The value will be strings, date, date list (you will use the id and the date to create a list), checkboxes (codes that you will take from the config), and diagnosis so if you in the model find something that is not these values, skip that value and dont add it to the map. When you've generated the whole map.";
  private static final String INFO_PROMPT = "Then add a entry in the map with key INFO where you add a text that describes what information you have used and how you have used it.";

  @Value("${ai.prefill.system.prompt:}")
  private String systemPrompt;

  private final ChatClient chatClient;

  public PrefillService(ChatClient.Builder chatClientBuilder) {
    this.chatClient = chatClientBuilder.build();
  }

  public AIPrefillResponse prefill(Certificate certificate, String ehrData) {
    final var data = generateMap(certificate, ehrData);

    final var elementData = data.entrySet().stream()
        .filter(entry -> certificate.certificateModel().elementSpecificationExists(new ElementId(entry.getKey())))
        .filter(entry -> certificate.certificateModel().elementSpecification(new ElementId(entry.getKey())).configuration() instanceof ElementConfigurationTextField
            || certificate.certificateModel().elementSpecification(new ElementId(entry.getKey())).configuration() instanceof ElementConfigurationTextArea)
        .map(entry -> ElementData.builder()
            .id(new ElementId(entry.getKey()))
            //.value(convertToElementValue(certificate, entry.getValue(), entry.getKey()))
            .value(ElementValueText.builder()
                .text(entry.getValue())
                .textId(new FieldId(entry.getKey()))
                .build()
            )
            .build()
        )
        .filter(Objects::nonNull)
        .toList();
    certificate.elementData(elementData);

    return AIPrefillResponse.builder()
        .certificate(certificate)
        //.informationAboutAIGeneration((((AIPrefillValueText)data.get("INFO")).getText()))
        .build();
  }

  private Map<String, String> generateMap(Certificate certificate, String ehrData) {
    return chatClient.prompt()
        .system(SYSTEM_PROMPT + ONLY_TEXT_PROMPT)
        .user(String.format("CertificateModel: %s, \n Text: %s", certificateModelToString(certificate.certificateModel()), ehrData))
        .call()
        .entity(new ParameterizedTypeReference<>() {
        });
  }

  private String certificateModelToString(CertificateModel model) {
    return model.toString().replaceAll("[\\[\\]{}]", "");
  }

  private ElementValue convertToElementValue(Certificate certificate, AIPrefillValue aiPrefillValue, String id) {
    return switch (aiPrefillValue.getType()) {
      case TEXT -> convertElementValueText(certificate, (AIPrefillValueText) aiPrefillValue, id);
      case CODE_LIST -> convertElementValueCodeList(certificate, (AIPrefillValueCodeList) aiPrefillValue, id);
      case DIAGNOSIS_LIST -> convertElementValueDiagnosisList(certificate, (AIPrefillValueDiagnosisList) aiPrefillValue, id);
      case DATE -> convertElementValueDate(certificate, (AIPrefillValueDate) aiPrefillValue, id);
      case DATE_LIST -> convertElementValueDateList(certificate, (AIPrefillValueDateList) aiPrefillValue, id);
    };
  }

  private static ElementValueDateList convertElementValueDateList(Certificate certificate,
      AIPrefillValueDateList aiPrefillValue, String id) {
    final var elementSpecification = certificate.certificateModel()
        .elementSpecification(new ElementId(id));
    final var emptyValue = elementSpecification.configuration().emptyValue();
    return ((ElementValueDateList) emptyValue).withDateList(
        aiPrefillValue.getDates().stream()
            .map(date ->
                ElementValueDate.builder()
                    .dateId(new FieldId(date.getId()))
                    .date(date.getDate())
                    .build())
            .toList());
  }

  private static ElementValueDate convertElementValueDate(Certificate certificate,
      AIPrefillValueDate aiPrefillValue, String id) {
    final var elementSpecification = certificate.certificateModel()
        .elementSpecification(new ElementId(id));
    final var emptyValue = elementSpecification.configuration().emptyValue();
    return ((ElementValueDate) emptyValue).withDate(aiPrefillValue.getDate());
  }

  private static ElementValueDiagnosisList convertElementValueDiagnosisList(Certificate certificate, AIPrefillValueDiagnosisList aiPrefillValue, String id) {
    final var elementSpecification = certificate.certificateModel()
        .elementSpecification(new ElementId(id));
    final var emptyValue = elementSpecification.configuration().emptyValue();
    return ((ElementValueDiagnosisList) emptyValue).withDiagnoses(
        aiPrefillValue.getDiagnoses().stream()
            .map(diagnosis ->
                ElementValueDiagnosis.builder()
                    .code(diagnosis.getCode())
                    .id(new FieldId(diagnosis.getCode()))
                    .description(diagnosis.getDescription())
                    .build())
            .toList());
  }

  private static ElementValueCodeList convertElementValueCodeList(Certificate certificate,
      AIPrefillValueCodeList aiPrefillValue, String id) {
    final var elementSpecification = certificate.certificateModel()
        .elementSpecification(new ElementId(id));
    final var emptyValue = elementSpecification.configuration().emptyValue();
    return ((ElementValueCodeList) emptyValue).withList(
        aiPrefillValue.getCodes().stream()
            .map(code ->
                ElementValueCode.builder()
                    .code(code)
                    .codeId(new FieldId(code))
                    .build())
            .toList());
  }

  private static ElementValueText convertElementValueText(Certificate certificate,
      AIPrefillValueText aiPrefillValue, String id) {
    final var elementSpecification = certificate.certificateModel()
        .elementSpecification(new ElementId(id));
    final var emptyValue = elementSpecification.configuration().emptyValue();
    return ((ElementValueText) emptyValue).withText(
        aiPrefillValue.getText());
  }
}
