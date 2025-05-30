package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import jakarta.xml.bind.JAXBContext;
import java.io.StringWriter;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Signature;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.common.XmlGeneratorIntyg;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.common.XmlNamespaceTrimmer;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlValidationService;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.RegisterCertificateType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Intyg;
import se.riv.clinicalprocess.healthcond.certificate.v3.MeddelandeReferens;

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
            svarPa(certificate),
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

  private static RegisterCertificateType registerCertificateType(MeddelandeReferens svarPa,
      Intyg intyg) {
    final var registerCertificateType = new RegisterCertificateType();
    registerCertificateType.setSvarPa(svarPa);
    registerCertificateType.setIntyg(intyg);
    return registerCertificateType;
  }

  private Intyg intyg(Certificate certificate, Signature signature) {
    return XmlGeneratorIntyg.intyg(certificate, signature, xmlGeneratorValue);
  }

  private MeddelandeReferens svarPa(Certificate certificate) {
    if (certificate.hasParent(RelationType.COMPLEMENT)) {
      final var svarPa = new MeddelandeReferens();
      final var unhandledComplements = certificate.parent().certificate()
          .messages(MessageType.COMPLEMENT)
          .stream()
          .filter(message -> !message.isHandled())
          .sorted(Comparator.comparing(Message::sent))
          .toList();

      if (unhandledComplements.isEmpty()) {
        return null;
      }

      final var complement = unhandledComplements.getLast();
      svarPa.setMeddelandeId(complement.id().id());
      svarPa.setReferensId(complement.reference().reference());
      return svarPa;
    }

    return null;
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
      return new Xml(XmlNamespaceTrimmer.trim(writer.toString()));
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}