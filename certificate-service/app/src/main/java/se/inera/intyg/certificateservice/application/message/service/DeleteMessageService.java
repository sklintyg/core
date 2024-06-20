package se.inera.intyg.certificateservice.application.message.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.message.dto.DeleteMessageRequest;
import se.inera.intyg.certificateservice.application.message.service.validator.DeleteMessageRequestValidator;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.service.DeleteMessageDomainService;

@Service
@RequiredArgsConstructor
public class DeleteMessageService {

  private final DeleteMessageRequestValidator deleteMessageRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final DeleteMessageDomainService deleteMessageDomainService;

  @Transactional
  public void delete(DeleteMessageRequest request, String messageId) {
    deleteMessageRequestValidator.validate(request, messageId);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    deleteMessageDomainService.delete(
        new MessageId(messageId),
        actionEvaluation
    );
  }
}
