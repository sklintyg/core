package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import static se.inera.intyg.certificateservice.domain.common.model.HsaId.OID;

import jakarta.xml.bind.JAXBContext;
import java.io.StringWriter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.WorkplaceCode;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.RegisterCertificateType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ArbetsplatsKod;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Befattning;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.HsaId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.IntygId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.PersonId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Specialistkompetens;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.TypAvIntyg;
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

  private final XmlGeneratorValue xmlGeneratorValue;

  @Override
  public Xml generate(Certificate certificate) {
    return marshall(
        registerCertificateType(
            intyg(
                intygsId(certificate),
                version(certificate),
                typAvIntyg(certificate),
                patient(certificate),
                skapadAv(certificate),
                svar(certificate)
            )
        )
    );
  }

  private static RegisterCertificateType registerCertificateType(Intyg intyg) {
    final var registerCertificateType = new RegisterCertificateType();
    registerCertificateType.setIntyg(intyg);
    return registerCertificateType;
  }

  private static Intyg intyg(IntygId intygId, String version, TypAvIntyg typAvIntyg,
      Patient patient, HosPersonal skapadAv, List<Svar> answers) {
    final var intyg = new Intyg();
    intyg.setIntygsId(intygId);
    intyg.setVersion(version);
    intyg.setTyp(typAvIntyg);
    intyg.setPatient(patient);
    intyg.setSkapadAv(skapadAv);
    intyg.getSvar().addAll(answers);
    return intyg;
  }

  private List<Svar> svar(Certificate certificate) {
    return xmlGeneratorValue.generate(certificate.elementData());
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

  private static Xml marshall(RegisterCertificateType registerCertificateType) {
    final var factory = new ObjectFactory();
    final var element = factory.createRegisterCertificate(registerCertificateType);
    try {
      final var context = JAXBContext.newInstance(RegisterCertificateType.class);
      final var writer = new StringWriter();
      context.createMarshaller().marshal(element, writer);
      return new Xml(writer.toString());
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalStateException(e);
    }
  }
}

