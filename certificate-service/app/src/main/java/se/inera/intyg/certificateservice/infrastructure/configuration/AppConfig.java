package se.inera.intyg.certificateservice.infrastructure.configuration;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.repository.StatisticsRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.AnswerComplementDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.ComplementCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.CreateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.CreateDraftFromCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.DeleteCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.DisposeObsoleteDraftsDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.ForwardCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.ForwardCertificateMessagesDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateCandidateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateEventsDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateEventsOfTypeDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificatePdfDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateXmlDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetSickLeaveCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetSickLeaveCertificatesDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.LockCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorProvider;
import se.inera.intyg.certificateservice.domain.certificate.service.PrefillProcessor;
import se.inera.intyg.certificateservice.domain.certificate.service.RenewCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.RenewExternalCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.ReplaceCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.RevokeCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.SendCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.SetCertificateReadyForSignDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.SignCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.SignCertificateWithoutSignatureDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.UpdateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.UpdateWithCertificateCandidateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.ValidateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGeneratorCertificatesForCareWithQA;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.service.ListAvailableCertificateModelsDomainService;
import se.inera.intyg.certificateservice.domain.citizen.service.GetCitizenCertificateDomainService;
import se.inera.intyg.certificateservice.domain.citizen.service.PrintCitizenCertificateDomainService;
import se.inera.intyg.certificateservice.domain.citizen.service.SendCitizenCertificateDomainService;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventSubscriber;
import se.inera.intyg.certificateservice.domain.event.service.MessageEventDomainService;
import se.inera.intyg.certificateservice.domain.event.service.MessageEventSubscriber;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.message.service.CreateMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.DeleteAnswerDomainService;
import se.inera.intyg.certificateservice.domain.message.service.DeleteMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.HandleMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.ReceiveAnswerMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.ReceiveComplementMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.ReceiveQuestionMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.ReceiveReminderMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.SaveAnswerDomainService;
import se.inera.intyg.certificateservice.domain.message.service.SaveMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.SendAnswerDomainService;
import se.inera.intyg.certificateservice.domain.message.service.SendMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.SetMessagesToHandleDomainService;
import se.inera.intyg.certificateservice.domain.message.service.SetMessagesToUnhandledDomainService;
import se.inera.intyg.certificateservice.domain.message.service.XmlGeneratorMessage;
import se.inera.intyg.certificateservice.domain.patient.service.GetCertificatesWithQAInternalDomainService;
import se.inera.intyg.certificateservice.domain.patient.service.GetPatientCertificatesDomainService;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitCertificatesDomainService;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitCertificatesInfoDomainService;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitMessagesDomainService;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitStatisticsDomainService;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCertificateV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCertificateWithQAV3;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorValue;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.message.XmlGeneratorMessageV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillHandler;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillService;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchemaValidatorV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchematronValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlSchemaValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlSchematronValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlValidationService;

@Configuration
public class AppConfig {

  @Value("${integration.intygproxyservice.address}")
  private String intygProxyServiceAddress;

  @Bean
  public CreateCertificateDomainService createCertificateDomainService(
      CertificateModelRepository certificateModelRepository,
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService,
      CertificateActionConfigurationRepository certificateActionConfigurationRepository,
      PrefillProcessor prefillProcessor) {
    return new CreateCertificateDomainService(certificateModelRepository, certificateRepository,
        certificateEventDomainService, certificateActionConfigurationRepository, prefillProcessor);
  }

  @Bean
  public GetCertificateDomainService getCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new GetCertificateDomainService(certificateRepository, certificateEventDomainService);
  }

  @Bean
  public UpdateCertificateDomainService updateCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new UpdateCertificateDomainService(certificateRepository, certificateEventDomainService);
  }

  @Bean
  public DeleteCertificateDomainService deleteCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new DeleteCertificateDomainService(certificateRepository, certificateEventDomainService);
  }

  @Bean
  public ValidateCertificateDomainService validateCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new ValidateCertificateDomainService(certificateRepository,
        certificateEventDomainService);
  }

  @Bean
  public GetPatientCertificatesDomainService getPatientCertificatesDomainService(
      CertificateRepository certificateRepository) {
    return new GetPatientCertificatesDomainService(certificateRepository);
  }

  @Bean
  public GetUnitCertificatesDomainService getUnitCertificatesDomainService(
      CertificateRepository certificateRepository) {
    return new GetUnitCertificatesDomainService(certificateRepository);
  }

  @Bean
  public GetUnitCertificatesInfoDomainService getUnitCertificatesInfoDomainService(
      CertificateRepository certificateRepository) {
    return new GetUnitCertificatesInfoDomainService(certificateRepository);
  }

  @Bean
  public CertificateEventDomainService certificateEventDomainService(
      List<CertificateEventSubscriber> subscribers) {
    return new CertificateEventDomainService(subscribers);
  }

  @Bean
  public GetCertificateXmlDomainService getCertificateXmlDomainService(
      CertificateRepository certificateRepository, XmlGenerator xmlGenerator) {
    return new GetCertificateXmlDomainService(certificateRepository, xmlGenerator);
  }

  @Bean
  public XmlGenerator xmlGenerator(XmlGeneratorValue xmlGeneratorValue,
      XmlValidationService xmlValidationService) {
    return new XmlGeneratorCertificateV4(xmlGeneratorValue, xmlValidationService);
  }

  @Bean
  public PrefillProcessor prefillProcessor(
      PrefillHandler prefillHandler) {
    return new PrefillService(prefillHandler);
  }

  @Bean
  public XmlSchematronValidator schematronValidator() {
    return new SchematronValidator();
  }

  @Bean
  public XmlSchemaValidator schemaValidator() {
    return new SchemaValidatorV4();
  }

  @Bean
  public SignCertificateDomainService signCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService, XmlGenerator xmlGenerator,
      SetMessagesToHandleDomainService setMessagesToHandleDomainService) {
    return new SignCertificateDomainService(certificateRepository, certificateEventDomainService,
        xmlGenerator, setMessagesToHandleDomainService);
  }

  @Bean
  public SignCertificateWithoutSignatureDomainService signCertificateWithoutSignatureDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService, XmlGenerator xmlGenerator,
      SetMessagesToHandleDomainService setMessagesToHandleDomainService) {
    return new SignCertificateWithoutSignatureDomainService(certificateRepository,
        certificateEventDomainService, xmlGenerator, setMessagesToHandleDomainService);
  }

  @Bean
  public SendCertificateDomainService sendCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new SendCertificateDomainService(certificateRepository,
        certificateEventDomainService);
  }

  @Bean
  public RevokeCertificateDomainService revokeCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService,
      SetMessagesToHandleDomainService setMessagesToHandleDomainService,
      SetMessagesToUnhandledDomainService setMessagesToUnhandledDomainService) {
    return new RevokeCertificateDomainService(certificateRepository,
        certificateEventDomainService, setMessagesToHandleDomainService,
        setMessagesToUnhandledDomainService);
  }

  @Bean
  public GetCertificatePdfDomainService getCertificatePdfDomainService(
      CertificateRepository certificateRepository, PdfGeneratorProvider pdfGeneratorProvider,
      CertificateEventDomainService certificateEventDomainService) {
    return new GetCertificatePdfDomainService(certificateRepository, pdfGeneratorProvider,
        certificateEventDomainService);
  }

  @Bean
  public XmlValidationService xmlValidationService(XmlSchemaValidator xmlSchemaValidator,
      XmlSchematronValidator xmlSchematronValidator) {
    return new XmlValidationService(xmlSchematronValidator, xmlSchemaValidator);
  }

  @Bean
  public ReplaceCertificateDomainService replaceCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new ReplaceCertificateDomainService(certificateRepository,
        certificateEventDomainService);
  }

  @Bean
  public GetCitizenCertificateDomainService getCitizenCertificateDomainService(
      CertificateRepository certificateRepository) {
    return new GetCitizenCertificateDomainService(certificateRepository);
  }

  @Bean
  public RenewCertificateDomainService renewCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new RenewCertificateDomainService(certificateRepository, certificateEventDomainService);
  }

  @Bean
  public RenewExternalCertificateDomainService renewExternalCertificateDomainService(
      CertificateModelRepository certificateModelRepository,
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService,
      PrefillProcessor prefillProcessor) {
    return new RenewExternalCertificateDomainService(certificateModelRepository,
        certificateRepository, certificateEventDomainService, prefillProcessor);
  }

  @Bean
  public ComplementCertificateDomainService complementCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new ComplementCertificateDomainService(certificateRepository,
        certificateEventDomainService);
  }

  @Bean
  public PrintCitizenCertificateDomainService printCitizenCertificateDomainService(
      CertificateRepository certificateRepository, PdfGeneratorProvider pdfGeneratorProvider) {
    return new PrintCitizenCertificateDomainService(certificateRepository, pdfGeneratorProvider);
  }

  @Bean
  public SendCitizenCertificateDomainService sendCitizenCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new SendCitizenCertificateDomainService(certificateRepository,
        certificateEventDomainService);
  }

  @Bean
  public ReceiveComplementMessageDomainService receiveComplementMessageDomainService(
      CertificateRepository certificateRepository, MessageRepository messageRepository) {
    return new ReceiveComplementMessageDomainService(certificateRepository, messageRepository);
  }

  @Bean
  public ReceiveReminderMessageDomainService receiveReminderMessageDomainService(
      CertificateRepository certificateRepository, MessageRepository messageRepository) {
    return new ReceiveReminderMessageDomainService(certificateRepository, messageRepository);
  }

  @Bean
  public ReceiveQuestionMessageDomainService receiveAdministrativeMessageDomainService(
      CertificateRepository certificateRepository, MessageRepository messageRepository) {
    return new ReceiveQuestionMessageDomainService(certificateRepository, messageRepository);
  }

  @Bean
  public ReceiveAnswerMessageDomainService receiveAnswerMessageDomainService(
      CertificateRepository certificateRepository, MessageRepository messageRepository) {
    return new ReceiveAnswerMessageDomainService(certificateRepository, messageRepository);
  }

  @Bean
  public SetMessagesToHandleDomainService setMessagesToHandleDomainService(
      MessageRepository messageRepository) {
    return new SetMessagesToHandleDomainService(messageRepository);
  }

  @Bean
  public SetMessagesToUnhandledDomainService setMessagesToUnhandledDomainService(
      MessageRepository messageRepository) {
    return new SetMessagesToUnhandledDomainService(messageRepository);
  }

  @Bean
  public HandleMessageDomainService handleMessageDomainService(
      MessageRepository messageRepository) {
    return new HandleMessageDomainService(messageRepository);
  }

  @Bean
  public XmlGeneratorMessage xmlGeneratorMessage() {
    return new XmlGeneratorMessageV4();
  }

  @Bean
  public AnswerComplementDomainService answerComplementDomainService(
      CertificateRepository certificateRepository,
      SetMessagesToHandleDomainService setMessagesToHandleDomainService,
      MessageEventDomainService messageEventDomainService) {
    return new AnswerComplementDomainService(certificateRepository,
        setMessagesToHandleDomainService, messageEventDomainService);
  }

  @Bean
  public MessageEventDomainService messageEventDomainService(
      List<MessageEventSubscriber> messageEventSubscribers) {
    return new MessageEventDomainService(messageEventSubscribers);
  }

  @Bean
  public CreateMessageDomainService createMessageDomainService(
      MessageRepository messageRepository) {
    return new CreateMessageDomainService(messageRepository);
  }

  @Bean
  public SaveMessageDomainService saveMessageDomainService(
      MessageRepository messageRepository) {
    return new SaveMessageDomainService(messageRepository);
  }

  @Bean
  public DeleteMessageDomainService deleteMessageDomainService(
      MessageRepository messageRepository, CertificateRepository certificateRepository) {
    return new DeleteMessageDomainService(messageRepository, certificateRepository);
  }

  @Bean
  public SendMessageDomainService sendMessageDomainService(
      MessageRepository messageRepository,
      MessageEventDomainService messageEventDomainService) {
    return new SendMessageDomainService(messageRepository,
        messageEventDomainService);
  }

  @Bean
  public SaveAnswerDomainService saveAnswerDomainService(
      MessageRepository messageRepository) {
    return new SaveAnswerDomainService(messageRepository);
  }

  @Bean
  public DeleteAnswerDomainService deleteAnswerDomainService(
      MessageRepository messageRepository) {
    return new DeleteAnswerDomainService(messageRepository);
  }

  @Bean
  public SendAnswerDomainService sendAnswerDomainService(
      MessageRepository messageRepository, MessageEventDomainService messageEventDomainService) {
    return new SendAnswerDomainService(messageRepository, messageEventDomainService);
  }

  @Bean
  public GetUnitMessagesDomainService unitMessagesDomainService(
      MessageRepository messageRepository, CertificateRepository certificateRepository) {
    return new GetUnitMessagesDomainService(messageRepository, certificateRepository);
  }

  @Bean
  public ForwardCertificateDomainService forwardCertificateDomainService(
      CertificateRepository certificateRepository) {
    return new ForwardCertificateDomainService(certificateRepository);
  }

  @Bean
  public ForwardCertificateMessagesDomainService forwardMessagesDomainService(
      MessageRepository messageRepository) {
    return new ForwardCertificateMessagesDomainService(messageRepository);
  }

  @Bean
  public XmlGeneratorCertificateWithQAV3 xmlGeneratorCertificateWithQAV3(
      XmlGeneratorValue xmlGeneratorValue) {
    return new XmlGeneratorCertificateWithQAV3(xmlGeneratorValue);
  }

  @Bean
  public GetCertificatesWithQAInternalDomainService getPatientCertificatesWithQADomainService(
      CertificateRepository certificateRepository,
      XmlGeneratorCertificatesForCareWithQA xmlGeneratorCertificatesForCareWithQA) {
    return new GetCertificatesWithQAInternalDomainService(certificateRepository,
        xmlGeneratorCertificatesForCareWithQA);
  }

  @Bean
  public LockCertificateDomainService setCertificatesToLockedDomainService(
      CertificateRepository certificateRepository) {
    return new LockCertificateDomainService(certificateRepository);
  }

  @Bean
  public GetCertificateEventsOfTypeDomainService getCertificateEventsOfTypeDomainService() {
    return new GetCertificateEventsOfTypeDomainService();
  }

  @Bean
  public GetCertificateEventsDomainService getCertificateEventsDomainService(
      CertificateRepository certificateRepository,
      GetCertificateEventsOfTypeDomainService getCertificateEventsOfTypeDomainService) {
    return new GetCertificateEventsDomainService(certificateRepository,
        getCertificateEventsOfTypeDomainService);
  }

  @Bean
  public GetUnitStatisticsDomainService getUnitStatisticsDomainService(
      StatisticsRepository statisticsRepository,
      CertificateModelRepository certificateModelRepository) {
    return new GetUnitStatisticsDomainService(statisticsRepository, certificateModelRepository);
  }

  @Bean
  public SetCertificateReadyForSignDomainService setCertificateReadyForSignDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new SetCertificateReadyForSignDomainService(certificateRepository,
        certificateEventDomainService);
  }

  @Bean
  public ListAvailableCertificateModelsDomainService listAvailableCertificateModelsDomainService(
      CertificateModelRepository certificateModelRepository,
      CertificateActionConfigurationRepository certificateActionConfigurationRepository) {
    return new ListAvailableCertificateModelsDomainService(certificateModelRepository,
        certificateActionConfigurationRepository);
  }

  @Bean
  public CertificateActionFactory certificateActionFactory(
      CertificateActionConfigurationRepository certificateActionConfigurationRepository) {
    return new CertificateActionFactory(certificateActionConfigurationRepository);
  }

  @Bean
  public PdfGeneratorProvider pdfGeneratorProvider(
      @Qualifier("certificatePdfGenerator") PdfGenerator certificatePdfGenerator,
      @Qualifier("generalPdfGenerator") PdfGenerator generalPdfGenerator) {
    return new PdfGeneratorProvider(certificatePdfGenerator, generalPdfGenerator);
  }

  @Bean
  public RestClient cpsRestClient() {
    return RestClient.create();
  }

  @Bean
  public RestClient ipsRestClient() {
    return RestClient.create(intygProxyServiceAddress);
  }

  @Bean
  public GetSickLeaveCertificateDomainService getSickLeaveCertificateDomainService(
      CertificateRepository certificateRepository) {
    return new GetSickLeaveCertificateDomainService(certificateRepository);
  }

  @Bean
  public CreateDraftFromCertificateDomainService createDraftFromCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateModelRepository certificateModelRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new CreateDraftFromCertificateDomainService(certificateRepository,
        certificateModelRepository, certificateEventDomainService);
  }

  @Bean
  public GetCertificateCandidateDomainService getCertificateCandidateDomainService(
      CertificateRepository certificateRepository) {
    return new GetCertificateCandidateDomainService(certificateRepository);
  }

  @Bean
  public UpdateWithCertificateCandidateDomainService updateWithCertificateCandidateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new UpdateWithCertificateCandidateDomainService(certificateRepository,
        certificateEventDomainService);
  }

  @Bean
  public GetSickLeaveCertificatesDomainService getSickLeaveCertificatesDomainService(
      CertificateRepository certificateRepository) {
    return new GetSickLeaveCertificatesDomainService(certificateRepository);
  }

  @Bean
  public DisposeObsoleteDraftsDomainService deleteDraftsDomainService(
      CertificateRepository certificateRepository) {
    return new DisposeObsoleteDraftsDomainService(certificateRepository);
  }
}