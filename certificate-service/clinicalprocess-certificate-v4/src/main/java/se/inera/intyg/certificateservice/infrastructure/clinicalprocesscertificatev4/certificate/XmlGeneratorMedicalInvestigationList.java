package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class XmlGeneratorMedicalInvestigationList implements XmlGeneratorElementValue {

  @Override
  public Class<? extends ElementValue> supports() {
    return ElementValueMedicalInvestigationList.class;
  }

  public List<Svar> generate(ElementData data, ElementSpecification specification) {
    if (!(data.value() instanceof ElementValueMedicalInvestigationList value)) {
      return Collections.emptyList();
    }

    if (value.list() == null || value.list().isEmpty()) {
      return Collections.emptyList();
    }

    if (!(specification.configuration() instanceof ElementConfigurationMedicalInvestigationList configuration)) {
      throw new IllegalArgumentException(
          "Cannot generate xml for configuration of type '%s'"
              .formatted(specification.configuration().getClass())
      );
    }

    final var objectFactory = new ObjectFactory();

    return value.list().stream()
        .filter(row -> isTextDefined(row) && isDateDefined(row) && isTypeDefined(row))
        .map(row -> {
              final var answer = new Svar();
              answer.setId(data.id().id());
              answer.setInstans(value.list().indexOf(row) + 1);

              final var subAnswerDate = new Delsvar();
              final var subAnswerText = new Delsvar();
              final var subAnswerCode = new Delsvar();
              final var cvType = new CVType();

              subAnswerDate.setId(getDateId(data.id().id()));
              subAnswerDate.getContent().add(row.date().date().toString());

              subAnswerText.setId(getTextId(data.id().id()));
              subAnswerText.getContent().add(row.informationSource().text());

              final var code = configuration.code(row.investigationType());
              subAnswerCode.setId(getCvId(data.id().id()));
              cvType.setCode(code.code());
              cvType.setCodeSystem(code.codeSystem());
              cvType.setDisplayName(code.displayName());

              final var convertedCvType = objectFactory.createCv(cvType);
              subAnswerCode.getContent().add(convertedCvType);

              answer.getDelsvar().add(subAnswerCode);
              answer.getDelsvar().add(subAnswerDate);
              answer.getDelsvar().add(subAnswerText);

              return answer;
            }
        )
        .toList();
  }

  private static boolean isTextDefined(MedicalInvestigation row) {
    return row.informationSource().text() != null && !row.informationSource().text()
        .isEmpty();
  }

  private static boolean isDateDefined(MedicalInvestigation row) {
    return row.date() != null && row.date().date() != null;
  }

  private static boolean isTypeDefined(MedicalInvestigation row) {
    return row.investigationType() != null && row.investigationType().code() != null
        && !row.investigationType().code().isEmpty();
  }

  private static String getCvId(String id) {
    return id + ".1";
  }

  private static String getDateId(String id) {
    return id + ".2";
  }

  private static String getTextId(String id) {
    return id + ".3";
  }

}