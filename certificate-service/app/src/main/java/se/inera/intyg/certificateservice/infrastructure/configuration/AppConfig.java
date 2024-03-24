package se.inera.intyg.certificateservice.infrastructure.configuration;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Queue;
import java.util.List;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.CreateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.DeleteCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateXmlDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.SignCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.SignCertificateWithoutSignatureDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.UpdateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.ValidateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventSubscriber;
import se.inera.intyg.certificateservice.domain.patient.service.GetPatientCertificatesDomainService;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitCertificatesDomainService;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitCertificatesInfoDomainService;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.XmlGeneratorCertificateV4;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.XmlGeneratorValue;

@Configuration
public class AppConfig {

  @Value("${certificate.service.amq.name}")
  private String queueName;

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
  public XmlGenerator xmlGenerator(XmlGeneratorValue xmlGeneratorValue) {
    return new XmlGeneratorCertificateV4(xmlGeneratorValue);
  }

  @Bean
  public SignCertificateDomainService signCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService, XmlGenerator xmlGenerator) {
    return new SignCertificateDomainService(certificateRepository, certificateEventDomainService,
        xmlGenerator);
  }

  @Bean
  public SignCertificateWithoutSignatureDomainService signCertificateWithoutSignatureDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService, XmlGenerator xmlGenerator) {
    return new SignCertificateWithoutSignatureDomainService(certificateRepository,
        certificateEventDomainService, xmlGenerator);
  }

  @Bean
  public Queue queue() {
    return new ActiveMQQueue(queueName);
  }

  @Bean
  public ConnectionFactory jmsConnectionFactory() {
    return new ActiveMQConnectionFactory();
  }

  @Bean
  public JmsTemplate certificateEventJmsTemplate() {
    return jmsTemplate(jmsConnectionFactory(), queue());
  }

  public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, Queue queue) {
    final var jmsTemplate = new JmsTemplate(connectionFactory);
    jmsTemplate.setDefaultDestination(queue);
    jmsTemplate.setSessionTransacted(true);
    return jmsTemplate;
  }

}
