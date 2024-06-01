package se.inera.intyg.certificateservice.infrastructure.configuration;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.AnswerComplementDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.ComplementCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.CreateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.DeleteCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificatePdfDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateXmlDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;
import se.inera.intyg.certificateservice.domain.certificate.service.RenewCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.ReplaceCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.RevokeCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.SendCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.SignCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.SignCertificateWithoutSignatureDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.UpdateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.ValidateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.citizen.service.GetCitizenCertificateDomainService;
import se.inera.intyg.certificateservice.domain.citizen.service.PrintCitizenCertificateDomainService;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventSubscriber;
import se.inera.intyg.certificateservice.domain.event.service.MessageEventDomainService;
import se.inera.intyg.certificateservice.domain.event.service.MessageEventSubscriber;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.message.service.HandleMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.ReceiveComplementMessageDomainService;
import se.inera.intyg.certificateservice.domain.message.service.SetMessagesToHandleDomainService;
import se.inera.intyg.certificateservice.domain.message.service.XmlGeneratorMessage;
import se.inera.intyg.certificateservice.domain.patient.service.GetPatientCertificatesDomainService;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitCertificatesDomainService;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitCertificatesInfoDomainService;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCertificateV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorValue;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.message.XmlGeneratorMessageV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchemaValidatorV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.SchematronValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlSchemaValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlSchematronValidator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlValidationService;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificatePdfFillService;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificatePdfGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificateTypePdfFillService;

@Configuration
public class AppConfig {

  @Bean
  public CreateCertificateDomainService createCertificateDomainService(
      CertificateModelRepository certificateModelRepository,
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new CreateCertificateDomainService(certificateModelRepository, certificateRepository,
        certificateEventDomainService);
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
  public XmlGeneratorValue xmlGeneratorValue() {
    return new XmlGeneratorValue();
  }

  @Bean
  public XmlGenerator xmlGenerator(XmlGeneratorValue xmlGeneratorValue,
      XmlValidationService xmlValidationService) {
    return new XmlGeneratorCertificateV4(xmlGeneratorValue, xmlValidationService);
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
      SetMessagesToHandleDomainService setMessagesToHandleDomainService) {
    return new RevokeCertificateDomainService(certificateRepository,
        certificateEventDomainService, setMessagesToHandleDomainService);
  }

  @Bean
  public GetCertificatePdfDomainService getCertificatePdfDomainService(
      CertificateRepository certificateRepository, PdfGenerator pdfGenerator,
      CertificateEventDomainService certificateEventDomainService) {
    return new GetCertificatePdfDomainService(certificateRepository, pdfGenerator,
        certificateEventDomainService);
  }

  @Bean
  public PdfGenerator pdfGenerator(
      List<CertificateTypePdfFillService> certificateTypePdfFillServices,
      CertificatePdfFillService certificatePdfFillService) {
    return new CertificatePdfGenerator(certificateTypePdfFillServices, certificatePdfFillService);
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
  public ComplementCertificateDomainService complementCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new ComplementCertificateDomainService(certificateRepository,
        certificateEventDomainService);
  }

  @Bean
  public PrintCitizenCertificateDomainService printCitizenCertificateDomainService(
      CertificateRepository certificateRepository, PdfGenerator pdfGenerator) {
    return new PrintCitizenCertificateDomainService(certificateRepository, pdfGenerator);
  }

  @Bean
  public ReceiveComplementMessageDomainService receiveMessageDomainService(
      CertificateRepository certificateRepository, MessageRepository messageRepository) {
    return new ReceiveComplementMessageDomainService(certificateRepository, messageRepository);
  }

  @Bean
  public SetMessagesToHandleDomainService setMessagesToHandleDomainService(
      MessageRepository messageRepository) {
    return new SetMessagesToHandleDomainService(messageRepository);
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
}
