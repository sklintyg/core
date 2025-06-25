package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Slf4j
@Component
public class PrefillHandler {

  private static final List<ElementType> IGNORED_TYPES = List.of(
      ElementType.CATEGORY,
      ElementType.MESSAGE,
      ElementType.ISSUING_UNIT);

  private final Map<Class<? extends ElementConfiguration>, PrefillConverter> converters;

  public PrefillHandler(List<PrefillConverter> converters) {
    this.converters = converters.stream()
        .collect(Collectors.toMap(PrefillConverter::supports, Function.identity()));
  }

  public Collection<PrefillAnswer> unknownAnswerIds(CertificateModel model,
      Forifyllnad answer) {
    return answer.getSvar().stream()
        .filter(s -> !model.elementSpecificationExists(new ElementId(s.getId())))
        .map(sp -> PrefillAnswer.answerNotFound(sp.getId()))
        .toList();
  }

  public Collection<PrefillAnswer> handlePrefill(CertificateModel certificateModel,
      Forifyllnad prefill) {
    return certificateModel.elementSpecifications().stream()
        .flatMap(ElementSpecification::flatten)
        .filter(isQuestion())
        .map(elementSpecification -> {
          final var prefillConverter = converters.get(
              elementSpecification.configuration().getClass());
          try {
            return prefillConverter.prefillAnswer(elementSpecification, prefill);
          } catch (Exception ex) {
            log.error(
                "Error while pre-filling element with id '{}': {}",
                elementSpecification.id().id(), ex.getMessage(), ex);
            return PrefillAnswer.builder()
                .errors(List.of(PrefillError.technicalError(elementSpecification.id().id())))
                .build();
          }
        })
        .filter(Objects::nonNull)
        .toList();
  }


  private static Predicate<ElementSpecification> isQuestion() {
    return e -> !IGNORED_TYPES.contains(e.configuration().type());
  }
}