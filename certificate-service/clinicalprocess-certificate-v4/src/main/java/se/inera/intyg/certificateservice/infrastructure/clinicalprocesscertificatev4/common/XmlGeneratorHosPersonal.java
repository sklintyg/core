package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.common;

import static se.inera.intyg.certificateservice.domain.common.model.HsaId.OID;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.common.model.HealthCareProfessionalLicence;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.WorkplaceCode;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ArbetsplatsKod;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Befattning;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.HsaId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.LegitimeratYrkeType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Specialistkompetens;
import se.riv.clinicalprocess.healthcond.certificate.v3.Enhet;
import se.riv.clinicalprocess.healthcond.certificate.v3.HosPersonal;
import se.riv.clinicalprocess.healthcond.certificate.v3.Vardgivare;

public class XmlGeneratorHosPersonal {

  private static final String PRESCRIPTION_CODE_MASKED = "0000000";
  private static final String NOT_APPLICABLE = "N/A";

  private XmlGeneratorHosPersonal() {
    throw new IllegalStateException("Utility class");
  }

  public static HosPersonal hosPersonal(Certificate certificate) {
    final var hosPersonal = hosPersonalWithoutEnhet(
        certificate.certificateMetaData().issuer()
    );

    hosPersonal.setEnhet(
        enhet(certificate.certificateMetaData(), certificate.unitContactInformation())
    );

    return hosPersonal;
  }

  public static HosPersonal hosPersonalWithoutEnhet(Staff staff) {
    final var hosPersonal = new HosPersonal();
    final var hsaId = new HsaId();
    hsaId.setRoot(OID);
    hsaId.setExtension(staff.hsaId().id());
    hosPersonal.setPersonalId(hsaId);
    hosPersonal.setForskrivarkod(PRESCRIPTION_CODE_MASKED);
    hosPersonal.setFullstandigtNamn(staff.name().fullName());

    staff.paTitles().stream()
        .map(paTitle -> {
              final var befattning = new Befattning();
              befattning.setCode(paTitle.code());
              befattning.setCodeSystem(PaTitle.OID);
              befattning.setDisplayName(paTitle.description());
              return befattning;
            }
        )
        .forEach(befattning -> hosPersonal.getBefattning().add(befattning));

    staff.specialities().stream()
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

    staff.healthCareProfessionalLicence().stream()
        .map(HealthCareProfessionalLicence::code)
        .map(code -> {
              final var legitimeratYrkeType = new LegitimeratYrkeType();
              legitimeratYrkeType.setCode(code.code());
              legitimeratYrkeType.setDisplayName(code.displayName());
              legitimeratYrkeType.setCodeSystem(code.codeSystem());
              return legitimeratYrkeType;
            }
        )
        .forEach(legitimeratYrke -> hosPersonal.getLegitimeratYrke().add(legitimeratYrke));

    return hosPersonal;
  }

  public static Enhet enhet(CertificateMetaData certificateMetaData,
      Optional<ElementValueUnitContactInformation> unitContactInformation) {
    final var enhet = new Enhet();
    final var hsaId = new HsaId();
    hsaId.setRoot(OID);
    hsaId.setExtension(certificateMetaData.issuingUnit().hsaId().id());
    enhet.setEnhetsId(hsaId);
    enhet.setEnhetsnamn(certificateMetaData.issuingUnit().name().name());
    enhet.setPostadress(
        unitContactInformation.map(ElementValueUnitContactInformation::address).orElse(""));
    enhet.setPostnummer(
        unitContactInformation.map(ElementValueUnitContactInformation::zipCode).orElse(""));
    enhet.setPostort(
        unitContactInformation.map(ElementValueUnitContactInformation::city).orElse(""));
    enhet.setTelefonnummer(
        unitContactInformation.map(ElementValueUnitContactInformation::phoneNumber).orElse(""));
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
}