package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Map;
import java.util.Optional;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.PrefillAICertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.CreateCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.service.CreateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;

@Service
public class CreateCertificateAIService {

  private final CreateCertificateRequestValidator createCertificateRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final CreateCertificateDomainService createCertificateDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;
  private final ChatClient chatClient;

  public CreateCertificateAIService(
      CreateCertificateRequestValidator createCertificateRequestValidator,
      ActionEvaluationFactory actionEvaluationFactory,
      CreateCertificateDomainService createCertificateDomainService,
      CertificateConverter certificateConverter, ResourceLinkConverter resourceLinkConverter,
      ChatClient.Builder builder) {
    this.createCertificateRequestValidator = createCertificateRequestValidator;
    this.actionEvaluationFactory = actionEvaluationFactory;
    this.createCertificateDomainService = createCertificateDomainService;
    this.certificateConverter = certificateConverter;
    this.resourceLinkConverter = resourceLinkConverter;
    this.chatClient = builder.build();
  }

  @Transactional
  public CreateCertificateResponse create(PrefillAICertificateRequest request) {
    //createCertificateRequestValidator.validate(request);
    final var actionEvaluation = actionEvaluationFactory.create(
        request.getPatient(),
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var certificate = createCertificateDomainService.create(
        certificateModelId(request),
        actionEvaluation,
        request.getExternalReference() != null
            ? new ExternalReference(request.getExternalReference())
            : null
    );

    final var data = generateData(certificate.certificateModel(), request.getText());
    final var elementData = data.entrySet().stream()
        .map(entry -> ElementData.builder()
            .id(new ElementId(entry.getKey()))
            //.value(ElementValueDate.builder().date(entry.getValue()).build())
            .value(ElementValueText.builder().text(entry.getValue()).build())
            .build()
        )
        .toList();
    certificate.elementData(elementData);

    return CreateCertificateResponse.builder()
        .certificate(certificateConverter.convert(
            certificate,
            certificate.actionsInclude(Optional.of(actionEvaluation)).stream()
                .map(certificateAction ->
                    resourceLinkConverter.convert(
                        certificateAction,
                        Optional.of(certificate),
                        actionEvaluation
                    )
                )
                .toList(),
            actionEvaluation)
        )
        .build();
  }

  private Map<String, String> generateData(CertificateModel certificateModel,
      String text) {
    //final var systemPrompt = String.format(
    //  "Generate a map placing the question ids as the key. The value you need to extract from the prompt. In the prompt you will recieve a certificatemodel which has different questions defined with an id and a question name. Using the question name you will extract data from the text and set as the value in the map. The value will be a date. The question id is the key. Todays date is %s",
    //LocalDate.now());
    final var systemPrompt = "Generate a map placing the question ids as the key. The value you need to extract from the prompt. In the prompt you will recieve a certificatemodel which has different questions defined with an id and a question name. Using the question name you will extract data from the text and set as the value in the map. The value will be strings so if you in the model find something that is not a string, skip that value and dont add it to the map. The question id is the key.";
    return chatClient.prompt()
        .system(systemPrompt)
        .user(String.format("CertificateModel: %s, \n Text: %s", certificateModel, text))
        .call()
        .entity(new ParameterizedTypeReference<>() {
        });
  }

  private static CertificateModelId certificateModelId(PrefillAICertificateRequest request) {
    return CertificateModelId.builder()
        .type(
            new CertificateType(
                request.getCertificateModelId().getType()
            )
        )
        .version(
            new CertificateVersion(
                request.getCertificateModelId().getVersion()
            )
        ).build();
  }
}
