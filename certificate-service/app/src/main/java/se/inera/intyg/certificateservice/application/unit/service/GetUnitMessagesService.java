package se.inera.intyg.certificateservice.application.unit.service;

import java.util.Collections;
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
                .map(question -> questionConverter.convert(question, Collections.emptyList()))
                //TODO: fix actions
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
                        .toList()
                )
            )
            .toList()
        )
        .build();
  }
}
