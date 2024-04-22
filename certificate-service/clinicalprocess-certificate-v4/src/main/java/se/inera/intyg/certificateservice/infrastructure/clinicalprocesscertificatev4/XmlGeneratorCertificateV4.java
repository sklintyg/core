package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import static se.inera.intyg.certificateservice.domain.common.model.HsaId.OID;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.RequiredArgsConstructor;
import org.w3._2000._09.xmldsig_.SignatureType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Signature;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.WorkplaceCode;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.RegisterCertificateType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ArbetsplatsKod;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Befattning;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.HsaId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.IntygId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.PersonId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Specialistkompetens;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.TypAvIntyg;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.UnderskriftType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Enhet;
import se.riv.clinicalprocess.healthcond.certificate.v3.HosPersonal;
import se.riv.clinicalprocess.healthcond.certificate.v3.Intyg;
import se.riv.clinicalprocess.healthcond.certificate.v3.Patient;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Vardgivare;

@RequiredArgsConstructor
public class XmlGeneratorCertificateV4 implements XmlGenerator {

  private static final String EMPTY = "";
  private static final String NOT_APPLICABLE = "N/A";
  private static final String PRESCRIPTION_CODE_MASKED = "0000000";
  private static final String CERTIFICATE_TYPE_IGRAV = "IGRAV";

  private final XmlGeneratorValue xmlGeneratorValue;
  private final XmlGeneratorIntygsgivare xmlGeneratorIntygsgivare;
  private final XmlValidationService xmlValidationService;

  @Override
  public Xml generate(Certificate certificate) {
    return generate(certificate, null);
  }

  @Override
  public Xml generate(Certificate certificate, Signature signature) {
    final var xml = marshall(
        registerCertificateType(
            intyg(
                certificate,
                signature
            )
        )
    );

    xmlValidationService.validate(
        xml,
        certificate.certificateModel().schematronPath(),
        certificate.id()
    );

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
        skapadAv(certificate)
    );
    intyg.getSvar().addAll(
        svar(certificate)
    );

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

  private List<Svar> svar(Certificate certificate) {
    final var svar = xmlGeneratorValue.generate(certificate.elementData());

    if (!svar.isEmpty() && certificate.certificateModel().type().code()
        .equals(CERTIFICATE_TYPE_IGRAV)) {
      final var role = certificate.certificateMetaData().issuer().role();
      final var intygsgivare = xmlGeneratorIntygsgivare.generate(role);
      svar.get(0).getDelsvar().add(intygsgivare);
    }

    return svar;
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

  private static HosPersonal skapadAv(Certificate certificate) {
    final var hosPersonal = new HosPersonal();
    final var hsaId = new HsaId();
    hsaId.setRoot(OID);
    hsaId.setExtension(certificate.certificateMetaData().issuer().hsaId().id());
    hosPersonal.setPersonalId(hsaId);
    hosPersonal.setForskrivarkod(PRESCRIPTION_CODE_MASKED);
    hosPersonal.setFullstandigtNamn(certificate.certificateMetaData().issuer().name().fullName());

    certificate.certificateMetaData().issuer().paTitles().stream()
        .map(paTitle -> {
              final var befattning = new Befattning();
              befattning.setCode(paTitle.code());
              befattning.setCodeSystem(PaTitle.OID);
              befattning.setDisplayName(paTitle.description());
              return befattning;
            }
        )
        .forEach(befattning -> hosPersonal.getBefattning().add(befattning));

    certificate.certificateMetaData().issuer().specialities().stream()
        .map(speciality -> {
              final var specialistkompetens = new Specialistkompetens();
              specialistkompetens.setCode(NOT_APPLICABLE);
              specialistkompetens.setDisplayName(speciality.value());
              return specialistkompetens;
            }
        )
        .forEach(
            specialistkompetens -> hosPersonal.getSpecialistkompetens().add(specialistkompetens)
        );

    hosPersonal.setEnhet(
        enhet(certificate.certificateMetaData())
    );

    return hosPersonal;
  }

  private static Enhet enhet(CertificateMetaData certificateMetaData) {
    final var enhet = new Enhet();
    final var hsaId = new HsaId();
    hsaId.setRoot(OID);
    hsaId.setExtension(certificateMetaData.issuingUnit().hsaId().id());
    enhet.setEnhetsId(hsaId);
    enhet.setEnhetsnamn(certificateMetaData.issuingUnit().name().name());
    enhet.setPostadress(certificateMetaData.issuingUnit().address().address());
    enhet.setPostnummer(certificateMetaData.issuingUnit().address().zipCode());
    enhet.setPostort(certificateMetaData.issuingUnit().address().city());
    enhet.setTelefonnummer(certificateMetaData.issuingUnit().contactInfo().phoneNumber());
    enhet.setEpost(certificateMetaData.issuingUnit().contactInfo().email());

    final var arbetsplatsKod = new ArbetsplatsKod();
    arbetsplatsKod.setRoot(WorkplaceCode.OID);
    arbetsplatsKod.setExtension(certificateMetaData.issuingUnit().workplaceCode().code());
    enhet.setArbetsplatskod(arbetsplatsKod);

    enhet.setVardgivare(
        vardgivare(certificateMetaData.careProvider())
    );

    return enhet;
  }

  private static Vardgivare vardgivare(CareProvider careProvider) {
    final var vardgivare = new Vardgivare();
    final var hsaId = new HsaId();
    hsaId.setRoot(OID);
    hsaId.setExtension(careProvider.hsaId().id());
    vardgivare.setVardgivareId(hsaId);
    vardgivare.setVardgivarnamn(careProvider.name().name());
    return vardgivare;
  }

  private XMLGregorianCalendar signeringsTidpunkt(Certificate certificate) {
    if (certificate.signed() == null) {
      return null;
    }

    try {
      return DatatypeFactory.newInstance()
          .newXMLGregorianCalendar(
              certificate.signed().truncatedTo(ChronoUnit.SECONDS).toString()
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

