package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

public class XmlGeneratorIntygsgivare {

  private static final String CODE_SYSTEM = "KV_FKMU_0008";
  private static final String QUESTION_INTYGSGIVARE_FIELD_ID = "1.2";

  public Delsvar generate(Role role) {
    final var subAnswer = new Delsvar();
    subAnswer.setId(QUESTION_INTYGSGIVARE_FIELD_ID);

    final var objectFactory = new ObjectFactory();
    final var cvType = new CVType();
    final var displayName = getDisplayName(role);
    cvType.setCode(displayName.toUpperCase());
    cvType.setCodeSystem(CODE_SYSTEM);
    cvType.setDisplayName(displayName);
    subAnswer.getContent().add(objectFactory.createCv(cvType));
    return subAnswer;
  }

  private String getDisplayName(Role role) {
    switch (role) {
      case DOCTOR, PRIVATE_DOCTOR -> {
        return "Läkare";
      }
      case NURSE -> {
        return "Sjuksköterska";
      }
      case MIDWIFE -> {
        return "Barnmorska";
      }
      default -> throw new IllegalStateException("Illegal role %s".formatted(role.name()));
    }
  }
}
