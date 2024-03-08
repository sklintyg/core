package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.RegisterCertificateType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Intyg;

public class XmlGeneratorCertificateV4 implements XmlGenerator {

  @Override
  public String generate(Certificate certificate) {
    final var registerCertificateType = new RegisterCertificateType();
    registerCertificateType.setIntyg(new Intyg());
    final var factory = new ObjectFactory();
    final var element = factory.createRegisterCertificate(registerCertificateType);
    try {
      final var context = JAXBContext.newInstance(RegisterCertificateType.class);
      final var writer = new StringWriter();
      context.createMarshaller().marshal(element, writer);
      return writer.toString();
    } catch (JAXBException e) {
      throw new IllegalStateException(e);
    }
  }
}

