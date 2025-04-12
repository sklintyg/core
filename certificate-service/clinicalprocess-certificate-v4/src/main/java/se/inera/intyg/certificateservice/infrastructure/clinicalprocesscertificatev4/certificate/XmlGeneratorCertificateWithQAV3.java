package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import java.io.StringReader;
import java.io.StringWriter;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGeneratorCertificatesForCareWithQA;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.common.XmlGeneratorIntyg;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.common.XmlNamespaceTrimmer;
import se.riv.clinicalprocess.healthcond.certificate.listCertificatesForCareWithQA.v3.List;
import se.riv.clinicalprocess.healthcond.certificate.listCertificatesForCareWithQA.v3.ListCertificatesForCareWithQAResponseType;
import se.riv.clinicalprocess.healthcond.certificate.listCertificatesForCareWithQA.v3.ListItem;
import se.riv.clinicalprocess.healthcond.certificate.listCertificatesForCareWithQA.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.RegisterCertificateType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Arenden;

@RequiredArgsConstructor
public class XmlGeneratorCertificateWithQAV3 implements XmlGeneratorCertificatesForCareWithQA {

  private final XmlGeneratorValue xmlGeneratorValue;

  @Override
  public Xml generate(java.util.List<Certificate> certificates) {
    return marshall(
        listCertificatesForCareWithQAResponseType(
            list(certificates)
        )
    );
  }

  private List list(java.util.List<Certificate> certificates) {
    final var list = new List();
    certificates.forEach(certificate -> {
      final var listItem = new ListItem();
      listItem.setIntyg(
          certificate.xml() == null
              ? XmlGeneratorIntyg.intyg(certificate, null, xmlGeneratorValue)
              : unmarshall(certificate.xml().xml()).getIntyg()
      );
      listItem.setSkickadeFragor(
          toArenden(
              certificate.messages().stream()
                  .filter(Message::hasAuthoredStaff)
                  .toList()
          )
      );
      listItem.setMottagnaFragor(
          toArenden(
              certificate.messages().stream()
                  .filter(message -> !message.hasAuthoredStaff())
                  .toList()
          )
      );
      listItem.setRef(
          certificate.externalReference() != null ? certificate.externalReference().value() : null
      );
      list.getItem().add(listItem);
    });

    return list;
  }

  private Arenden toArenden(java.util.List<Message> messages) {
    final var arenden = new Arenden();
    arenden.setTotalt(messages.size());
    arenden.setBesvarade(
        (int) messages.stream()
            .filter(Message::hasAnswer)
            .count()
    );
    arenden.setEjBesvarade(
        (int) messages.stream()
            .filter(message -> !message.hasAnswer())
            .count()
    );
    arenden.setHanterade(
        (int) messages.stream()
            .filter(Message::isHandled)
            .count()
    );

    return arenden;
  }

  private RegisterCertificateType unmarshall(String xml) {
    try {
      final var context = JAXBContext.newInstance(RegisterCertificateType.class);
      final var unmarshaller = context.createUnmarshaller();
      final var stringReader = new StringReader(xml);
      final var jaxbElement = (JAXBElement<RegisterCertificateType>) unmarshaller.unmarshal(
          stringReader);
      return jaxbElement.getValue();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }

  private static ListCertificatesForCareWithQAResponseType listCertificatesForCareWithQAResponseType(
      List list) {
    final var listCertificatesForCareWithQAResponseType = new ListCertificatesForCareWithQAResponseType();
    listCertificatesForCareWithQAResponseType.setList(list);
    return listCertificatesForCareWithQAResponseType;
  }

  private static Xml marshall(
      ListCertificatesForCareWithQAResponseType listCertificatesForCareWithQAResponseType) {
    final var factory = new ObjectFactory();
    final var element = factory.createListCertificatesForCareWithQAResponse(
        listCertificatesForCareWithQAResponseType);
    try {
      final var context = JAXBContext.newInstance(
          ListCertificatesForCareWithQAResponseType.class,
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
