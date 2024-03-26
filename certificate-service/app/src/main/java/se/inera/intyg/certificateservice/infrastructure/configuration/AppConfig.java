package se.inera.intyg.certificateservice.infrastructure.configuration;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.CreateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.DeleteCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificatePdfDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateXmlDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;
import se.inera.intyg.certificateservice.domain.certificate.service.SendCertificateDomainService;
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
import se.inera.intyg.certificateservice.pdfboxgenerator.CertificatePdfGenerator;

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
  public SendCertificateDomainService sendCertificateDomainService(
      CertificateRepository certificateRepository,
      CertificateEventDomainService certificateEventDomainService) {
    return new SendCertificateDomainService(certificateRepository,
        certificateEventDomainService);
  }

  @Bean
  public GetCertificatePdfDomainService getCertificatePdfDomainService(
      CertificateRepository certificateRepository, PdfGenerator pdfGenerator,
      CertificateEventDomainService certificateEventDomainService) {
    return new GetCertificatePdfDomainService(certificateRepository, pdfGenerator,
        certificateEventDomainService);

  }

  @Bean
  public PdfGenerator pdfGenerator() {
    return new CertificatePdfGenerator();
  }

}
