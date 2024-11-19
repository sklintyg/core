package se.inera.intyg.certificateservice.application.unit.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.MessagesRequestFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesResponse;
import se.inera.intyg.certificateservice.application.unit.service.validator.GetUnitMessagesRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitMessagesDomainService;

@Service
@RequiredArgsConstructor
public class GetUnitMessagesService {

  private final GetUnitMessagesRequestValidator getUnitMessagesRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final MessagesRequestFactory messagesRequestFactory;
  private final GetUnitMessagesDomainService getUnitMessagesDomainService;
  private final CertificateConverter certificateConverter;
  private final QuestionConverter questionConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  public GetUnitMessagesResponse get(GetUnitMessagesRequest request) {
    getUnitMessagesRequestValidator.validate(request);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var messagesRequest = messagesRequestFactory.create(
        request.getMessagesQueryCriteria()
    );

    final var messagesResponse = getUnitMessagesDomainService.get(messagesRequest,
        actionEvaluation);

    return GetUnitMessagesResponse.builder()
        .questions(
            messagesResponse.messages().stream()
                .map(message ->
                    questionConverter.convert(
                        message,
                        message.actions(actionEvaluation, getCertificateForMessage(
                                message,
                                messagesResponse.certificates()
                            )
                        )
                    )
                )
                .toList()
        )
        .certificates(messagesResponse.certificates().stream()
            .map(certificate -> certificateConverter.convert(
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
                    actionEvaluation
                )
            )
            .toList()
        )
        .build();
  }

  private Certificate getCertificateForMessage(Message message, List<Certificate> certificateList) {
    return certificateList.stream()
        .filter(certificate -> certificate.id().id().equals(message.certificateId().id()))
        .findAny()
        .orElseThrow(
            () -> new IllegalStateException("Message does not have valid certificate in list"));
  }
}
