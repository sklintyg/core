package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.message;

import jakarta.xml.bind.JAXBContext;
import java.io.StringWriter;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.service.XmlGenerator;
import se.riv.clinicalprocess.healthcond.certificate.sendMessageToRecipient.v2.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.sendMessageToRecipient.v2.SendMessageToRecipientType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;

@RequiredArgsConstructor
public class XmlGeneratorMessageV4 implements XmlGenerator {

  @Override
  public Xml generate(Answer answer) {
    return null;
  }

  private static Xml marshall(SendMessageToRecipientType sendMessageToRecipientType) {
    final var factory = new ObjectFactory();
    final var element = factory.createSendMessageToRecipient(sendMessageToRecipientType);
    try {
      final var context = JAXBContext.newInstance(
          SendMessageToRecipientType.class,
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

