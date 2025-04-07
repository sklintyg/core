package se.inera.intyg.certificateservice.prefill.service;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import model.AIPrefillValueCodeList;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import model.AIPrefillValue;
import model.AIPrefillValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Service
@RequiredArgsConstructor
public class PrefillService {

  private static final String SYSTEM_PROMPT = "Generate a map placing the question ids as the key. The value you need to extract from the prompt. In the prompt you will recieve a certificatemodel which has different questions defined with an id and a question name. Using the question name you will extract data from the text, which is electronic health records for the patient, and set as the value in the map. The question id is the key.";
  private static final String ONLY_TEXT_PROMPT = "The value will be strings so if you in the model find something that is not a string, skip that value and dont add it to the map.";

  private final ChatClient chatClient;

  public Certificate prefill(Certificate certificate, String ehrData) {
    final var data = generateMap(certificate, ehrData);

    final var elementData = data.entrySet().stream()
        .map(entry -> ElementData.builder()
            .id(new ElementId(entry.getKey()))
            .value(convertToElementValue(certificate, entry.getValue(), entry.getKey()))
            .build()
        )
        .toList();
    certificate.elementData(elementData);

    return certificate;
  }

  private Map<String, AIPrefillValue> generateMap(Certificate certificate, String ehrData) {
    return chatClient.prompt()
        .system(SYSTEM_PROMPT + ONLY_TEXT_PROMPT)
        .user(String.format("CertificateModel: %s, \n Text: %s", certificate.certificateModel(),
            ehrData))
        .call()
        .entity(new ParameterizedTypeReference<>() {
        });
  }

  private ElementValue convertToElementValue(Certificate certificate, AIPrefillValue aiPrefillValue, String id) {
    return switch (aiPrefillValue.getType()) {
      case TEXT -> convertElementValueText(certificate, (AIPrefillValueText) aiPrefillValue, id);
      case CODE_LIST -> convertElementValueCodeList(certificate, (AIPrefillValueCodeList) aiPrefillValue, id);
    };
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
