package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import jakarta.xml.bind.JAXBContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class CheckboxMultipleDatesConverter implements PrefillConverter {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationCheckboxMultipleDate.class;
  }

  @Override
  public PrefillAnswer prefillSubAnswer(Collection<Delsvar> subAnswers,
      ElementSpecification specification) {
    return null;
  }

  public PrefillAnswer prefillAnswer(Collection<Svar> answers, ElementSpecification specification) {
    var elementData = ElementData.builder()
        .id(specification.id())
        .value(ElementValueDateList.builder()
            .dateListId(new FieldId(null))
            .dateList(answers.stream()
                .map(s -> {
                  //TODO: Do we want to create error for each unmarshalling failure ?

                  CVType cv = unmarshalCVType(s.getDelsvar().stream()
                      .filter(d -> d.getId().equals(specification.id().id() + ".1"))
                      .findFirst().get().getContent());

                  var date = LocalDate.parse(((String) s.getDelsvar().stream()
                      .filter(d -> d.getId().equals(specification.id().id() + ".2"))
                      .findFirst().get().getContent().getFirst()));

                  var dateBox = ((ElementConfigurationCheckboxMultipleDate) specification.configuration()).dates()
                      .stream()
                      .filter(d -> d.code().matches(
                          new Code(cv.getCode(), cv.getCodeSystem(),
                              cv.getDisplayName())))
                      .findFirst();
                  if (dateBox.isEmpty()) {
                    throw new IllegalArgumentException(
                        "No matching date box found for code: " + cv.getCode());
                  }
                  return ElementValueDate.builder()
                      .dateId(dateBox.get().id())
                      .date(date)
                      .build();
                })
                .toList())
            .build())
        .build();

    PrefillAnswer.builder().elementData(elementData);
    return PrefillAnswer.builder()
        .elementData(elementData)
        .build();
  }

  @Override
  public Collection<PrefillAnswer> unknownIds(Svar answer, CertificateModel model) {
    if (!model.elementSpecificationExists(new ElementId(answer.getId()))) {
      return List.of(PrefillAnswer.answerNotFound(answer.getId()));
    }

    var result = new ArrayList<PrefillAnswer>();
    answer.getDelsvar().stream().filter(
            subAnswer -> !model.elementSpecificationExists(new ElementId(subAnswer.getId())))
        .forEach(subAnswer -> {
          if (!List.of("1.1", "1.2").contains(subAnswer.getId())) {
            result.add(PrefillAnswer.subAnswerNotFound(answer.getId(), subAnswer.getId()));
          }
        });

    return result;
  }


  private CVType unmarshalCVType(List<Object> content) {
    final var contentObj = content.stream()
        .filter(obj -> obj instanceof org.w3c.dom.Element)
        .map(obj -> (org.w3c.dom.Element) obj)
        .findFirst();

    if (contentObj.isPresent()) {
      try {
        final var context = JAXBContext.newInstance(CVType.class);
        final var jaxbElement = context.createUnmarshaller()
            .unmarshal(contentObj.get(), CVType.class);
        return jaxbElement.getValue();
      } catch (Exception e) {
//        throw new PrefillException("Error when unmarshalling CVType", e);
      }
    }
//    throw new PrefillException("Content does not contain a valid CVType element");
    return null;
  }
}
