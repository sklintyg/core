package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.common.XmlGeneratorHosPersonal.hosPersonal;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.RequiredArgsConstructor;
import org.w3._2000._09.xmldsig_.SignatureType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.Signature;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlValidationService;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.RegisterCertificateType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.IntygId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.PersonId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.TypAvIntyg;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.TypAvRelation;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.UnderskriftType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Intyg;
import se.riv.clinicalprocess.healthcond.certificate.v3.Patient;

@RequiredArgsConstructor
public class XmlGeneratorCertificateV4 implements XmlGenerator {

  private static final String EMPTY = "";
  private static final String KV_RELATION_CODE_SYSTEM = "c2362fcd-eda0-4f9a-bd13-b3bbaf7f2146";
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd'T'HH:mm:ss");

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
    final var intyg = new Intyg();
    intyg.setIntygsId(
        intygsId(certificate)
    );
    intyg.setVersion(
        version(certificate)
    );
    intyg.setTyp(
        typAvIntyg(certificate)
    );
    intyg.setPatient(
        patient(certificate)
    );
    intyg.setSkapadAv(
        hosPersonal(certificate)
    );
    intyg.getSvar().addAll(
        xmlGeneratorValue.generate(certificate.elementData())
    );

    relation(intyg, certificate.parent());

    final var signeringsTidpunkt = signeringsTidpunkt(certificate);
    if (signeringsTidpunkt != null) {
      intyg.setSigneringstidpunkt(signeringsTidpunkt);
      intyg.setSkickatTidpunkt(signeringsTidpunkt);
    }

    final var underskriftType = underskriftType(signature);
    if (underskriftType != null) {
      intyg.setUnderskrift(underskriftType);
    }

    return intyg;
  }

  private static void relation(Intyg intyg, Relation parent) {
    if (parent == null) {
      return;
    }
    final var relation = new se.riv.clinicalprocess.healthcond.certificate.v3.Relation();

    final var intygId = new IntygId();
    intygId.setRoot(parent.certificate().certificateMetaData().issuingUnit().hsaId().id());
    intygId.setExtension(parent.certificate().id().id());

    final var typAvRelation = new TypAvRelation();
    typAvRelation.setCodeSystem(KV_RELATION_CODE_SYSTEM);
    typAvRelation.setCode(parent.type().toRelationKod());
    typAvRelation.setDisplayName(parent.type().toRelationKodText());

    relation.setIntygsId(intygId);
    relation.setTyp(typAvRelation);

    intyg.getRelation().add(relation);
  }

  private static IntygId intygsId(Certificate certificate) {
    final var intygId = new IntygId();
    intygId.setRoot(certificate.certificateMetaData().issuingUnit().hsaId().id());
    intygId.setExtension(certificate.id().id());
    return intygId;
  }

  private static TypAvIntyg typAvIntyg(Certificate certificate) {
    final var typAvIntyg = new TypAvIntyg();
    typAvIntyg.setCode(certificate.certificateModel().type().code());
    typAvIntyg.setCodeSystem(certificate.certificateModel().type().codeSystem());
    typAvIntyg.setDisplayName(certificate.certificateModel().type().displayName());
    return typAvIntyg;
  }

  private static String version(Certificate certificate) {
    return certificate.certificateModel().id().version().version();
  }

  private static Patient patient(Certificate certificate) {
    final var patient = new Patient();
    final var personId = new PersonId();
    personId.setRoot(certificate.certificateMetaData().patient().id().type().oid());
    personId.setExtension(certificate.certificateMetaData().patient().id().idWithoutDash());
    patient.setPersonId(personId);
    patient.setFornamn(EMPTY);
    patient.setEfternamn(EMPTY);
    patient.setPostadress(EMPTY);
    patient.setPostnummer(EMPTY);
    patient.setPostort(EMPTY);
    return patient;
  }

  private XMLGregorianCalendar signeringsTidpunkt(Certificate certificate) {
    if (certificate.signed() == null) {
      return null;
    }

    try {
      return DatatypeFactory.newInstance()
          .newXMLGregorianCalendar(
              DATE_TIME_FORMATTER.format(certificate.signed())
          );
    } catch (Exception ex) {
      throw new IllegalStateException("Could not convert signed", ex);
    }
  }

  private UnderskriftType underskriftType(Signature signature) {
    if (signature == null) {
      return null;
    }
    final var signatureType = unmarshal(signature);
    final var underskriftType = new UnderskriftType();
    underskriftType.setSignature(signatureType);
    return underskriftType;
  }

  private SignatureType unmarshal(Signature response) {
    try {
      final var context = JAXBContext.newInstance(SignatureType.class);
      final var unmarshaller = context.createUnmarshaller();
      final var stringReader = new StringReader(response.value());
      final var jaxbElement = (JAXBElement<SignatureType>) unmarshaller.unmarshal(stringReader);
      return jaxbElement.getValue();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
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

