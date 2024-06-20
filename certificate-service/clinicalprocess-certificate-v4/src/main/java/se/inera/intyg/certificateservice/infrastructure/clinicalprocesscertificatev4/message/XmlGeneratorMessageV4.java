package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.message;

import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.common.XmlGeneratorHosPersonal.enhet;
import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.common.XmlGeneratorHosPersonal.hosPersonalWithoutEnhet;

import jakarta.xml.bind.JAXBContext;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.service.XmlGeneratorMessage;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.riv.clinicalprocess.healthcond.certificate.sendMessageToRecipient.v2.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.sendMessageToRecipient.v2.SendMessageToRecipientType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Amneskod;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.IntygId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.PersonId;
import se.riv.clinicalprocess.healthcond.certificate.v3.HosPersonal;
import se.riv.clinicalprocess.healthcond.certificate.v3.MeddelandeReferens;

@RequiredArgsConstructor
public class XmlGeneratorMessageV4 implements XmlGeneratorMessage {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd'T'HH:mm:ss");

  @Override
  public Xml generate(Message message, Certificate certificate) {
    return marshall(
        sendMessageToRecipientType(
            message,
            certificate
        )
    );
  }

  @Override
  public Xml generateAnswer(Answer answer, Message message, Certificate certificate) {
    return marshall(
        sendMessageToRecipientType(
            answer,
            message,
            certificate
        )
    );
  }

  private static SendMessageToRecipientType sendMessageToRecipientType(Message message,
      Certificate certificate) {
    final var sendMessageToRecipientType = new SendMessageToRecipientType();
    sendMessageToRecipientType.setMeddelandeId(
        message.id().id()
    );
    if (message.reference() != null) {
      sendMessageToRecipientType.setReferensId(message.reference().reference());
    }
    sendMessageToRecipientType.setSkickatTidpunkt(
        skickatTidpunkt(
            message.sent()
        )
    );
    sendMessageToRecipientType.setIntygsId(
        intygsId(certificate)
    );
    sendMessageToRecipientType.setPatientPersonId(
        patientPersonId(certificate)
    );
    sendMessageToRecipientType.setLogiskAdressMottagare(
        logiskAdressMottagare(certificate)
    );
    sendMessageToRecipientType.setAmne(
        amneskod(message.type())
    );
    sendMessageToRecipientType.setRubrik(
        message.subject().subject()
    );
    sendMessageToRecipientType.setMeddelande(
        message.content().content()
    );
    sendMessageToRecipientType.setSkickatAv(
        skickatAv(
            message.authoredStaff(),
            certificate
        )
    );
    return sendMessageToRecipientType;
  }

  private static SendMessageToRecipientType sendMessageToRecipientType(Answer answer,
      Message message, Certificate certificate) {
    final var sendMessageToRecipientType = new SendMessageToRecipientType();
    sendMessageToRecipientType.setMeddelandeId(
        answer.id().id()
    );
    if (answer.reference() != null) {
      sendMessageToRecipientType.setReferensId(answer.reference().reference());
    }
    sendMessageToRecipientType.setSkickatTidpunkt(
        skickatTidpunkt(
            answer.sent()
        )
    );
    sendMessageToRecipientType.setIntygsId(
        intygsId(certificate)
    );
    sendMessageToRecipientType.setPatientPersonId(
        patientPersonId(certificate)
    );
    sendMessageToRecipientType.setLogiskAdressMottagare(
        logiskAdressMottagare(certificate)
    );
    sendMessageToRecipientType.setAmne(
        amneskod(message.type())
    );
    sendMessageToRecipientType.setRubrik(
        answer.subject().subject()
    );
    sendMessageToRecipientType.setMeddelande(
        answer.content().content()
    );
    sendMessageToRecipientType.setSvarPa(
        svarPa(message)
    );
    sendMessageToRecipientType.setSkickatAv(
        skickatAv(
            answer.authoredStaff(),
            certificate
        )
    );
    return sendMessageToRecipientType;
  }

  private static XMLGregorianCalendar skickatTidpunkt(LocalDateTime sent) {
    try {
      return DatatypeFactory.newInstance()
          .newXMLGregorianCalendar(
              DATE_TIME_FORMATTER.format(sent)
          );
    } catch (Exception ex) {
      throw new IllegalStateException("Could not convert signed", ex);
    }
  }

  private static IntygId intygsId(Certificate certificate) {
    final var intygId = new IntygId();
    intygId.setRoot(certificate.certificateMetaData().issuingUnit().hsaId().id());
    intygId.setExtension(certificate.id().id());
    return intygId;
  }

  private static PersonId patientPersonId(Certificate certificate) {
    final var personId = new PersonId();
    personId.setRoot(certificate.certificateMetaData().patient().id().type().oid());
    personId.setExtension(certificate.certificateMetaData().patient().id().idWithoutDash());
    return personId;
  }

  private static String logiskAdressMottagare(Certificate certificate) {
    return certificate.certificateModel().recipient().id().id();
  }

  private static Amneskod amneskod(MessageType messageType) {
    final var amneskod = new Amneskod();
    amneskod.setCode(messageType.code());
    amneskod.setDisplayName(messageType.displayName());
    amneskod.setCodeSystem(MessageType.OID);
    return amneskod;
  }

  private static MeddelandeReferens svarPa(Message message) {
    final var meddelandeReferens = new MeddelandeReferens();
    meddelandeReferens.setMeddelandeId(message.id().id());
    if (message.reference() != null) {
      meddelandeReferens.setReferensId(message.reference().reference());
    }
    return meddelandeReferens;
  }

  private static HosPersonal skickatAv(Staff authoredStaff, Certificate certificate) {
    final var hosPersonal = hosPersonalWithoutEnhet(authoredStaff);
    hosPersonal.setEnhet(
        enhet(certificate.certificateMetaData())
    );
    return hosPersonal;
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

  private static <T, U> String getStringValue(T answer, Function<T, String> answerFunction,
      U message,
      Function<U, String> messageFunction) {
    if (answer != null) {
      return answerFunction.apply(answer);
    }

    return messageFunction.apply(message);
  }

  private static <T, U> LocalDateTime getDateValue(T answer,
      Function<T, LocalDateTime> answerFunction, U message,
      Function<U, LocalDateTime> messageFunction) {
    if (answer != null) {
      return answerFunction.apply(answer);
    }

    return messageFunction.apply(message);
  }

  private static <T, U> Staff getStaffValue(T answer,
      Function<T, Staff> answerFunction, U message, Function<U, Staff> messageFunction) {
    if (answer != null) {
      return answerFunction.apply(answer);
    }

    return messageFunction.apply(message);
  }
}

