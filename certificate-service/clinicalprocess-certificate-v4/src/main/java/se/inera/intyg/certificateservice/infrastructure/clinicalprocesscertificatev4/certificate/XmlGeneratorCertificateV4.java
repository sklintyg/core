package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import jakarta.xml.bind.JAXBContext;
import java.io.StringWriter;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Signature;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.common.XmlGeneratorIntyg;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlValidationService;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.RegisterCertificateType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Intyg;

@RequiredArgsConstructor
public class XmlGeneratorCertificateV4 implements XmlGenerator {

  private final XmlGeneratorValue xmlGeneratorValue;
  private final XmlValidationService xmlValidationService;


  @Override
  public Xml generate(Certificate certificate, boolean validate) {
    return generate(certificate, null, validate);
  }

  @Override
  public Xml generate(Certificate certificate, Signature signature) {
    return generate(certificate, signature, true);
  }

  private Xml generate(Certificate certificate, Signature signature, boolean validate) {
    final var xml = marshall(
        registerCertificateType(
            intyg(
                certificate,
                signature
            )
        )
    );

    if (validate) {
      xmlValidationService.validate(
          xml,
          certificate.certificateModel().schematronPath(),
          certificate.id()
      );
    }

    return xml;
  }

  private static RegisterCertificateType registerCertificateType(Intyg intyg) {
    final var registerCertificateType = new RegisterCertificateType();
    registerCertificateType.setIntyg(intyg);
    return registerCertificateType;
  }

  private Intyg intyg(Certificate certificate, Signature signature) {
    return XmlGeneratorIntyg.intyg(certificate, signature, xmlGeneratorValue);
  }

  private static Xml marshall(RegisterCertificateType registerCertificateType) {
    final var factory = new ObjectFactory();
    final var element = factory.createRegisterCertificate(registerCertificateType);
    try {
      final var context = JAXBContext.newInstance(
          RegisterCertificateType.class,
          DatePeriodType.class
      );
      final var writer = new StringWriter();
      context.createMarshaller().marshal(element, writer);
      return new Xml(writer.toString());
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}

