package se.inera.intyg.certificateservice.application.message.service;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.message.dto.SaveMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.SaveMessageResponse;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.SaveMessageRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.service.SaveMessageDomainService;

@Service
@RequiredArgsConstructor
public class SaveMessageService {

  private final SaveMessageRequestValidator saveMessageRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final SaveMessageDomainService saveMessageDomainService;
  private final QuestionConverter questionConverter;

  public SaveMessageResponse save(SaveMessageRequest request, String messageId) {
    saveMessageRequestValidator.validate(request, messageId);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var question = request.getQuestion();
    final var messageType = MessageType.valueOf(question.getType().name());

    final var updatedMessage = saveMessageDomainService.save(
        new CertificateId(question.getCertificateId()),
        new MessageId(messageId),
        new Content(question.getMessage()),
        actionEvaluation,
        messageType
    );

    return SaveMessageResponse.builder()
        .question(questionConverter.convert(updatedMessage, Collections.emptyList()))
        .build();
  }
}
