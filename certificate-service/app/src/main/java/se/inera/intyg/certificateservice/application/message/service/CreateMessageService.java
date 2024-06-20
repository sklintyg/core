package se.inera.intyg.certificateservice.application.message.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageResponse;
import se.inera.intyg.certificateservice.application.message.service.converter.QuestionConverter;
import se.inera.intyg.certificateservice.application.message.service.validator.CreateMessageRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.service.CreateMessageDomainService;

@Service
@RequiredArgsConstructor
public class CreateMessageService {

  private final CreateMessageRequestValidator createMessageRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final CreateMessageDomainService createMessageDomainService;
  private final QuestionConverter questionConverter;
  private final CertificateRepository certificateRepository;

  @Transactional
  public CreateMessageResponse create(CreateMessageRequest request, String certificateId) {
    createMessageRequestValidator.validate(request, certificateId);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getPatient(),
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var certificate = certificateRepository.getById(
        new CertificateId(certificateId)
    );

    final var createdQuestion = createMessageDomainService.create(
        certificate,
        actionEvaluation,
        MessageType.valueOf(request.getQuestionType().name()),
        new Content(request.getMessage())
    );

    return CreateMessageResponse.builder()
        .question(questionConverter.convert(createdQuestion,
            createdQuestion.actions(actionEvaluation, certificate)))
        .build();
  }
}
