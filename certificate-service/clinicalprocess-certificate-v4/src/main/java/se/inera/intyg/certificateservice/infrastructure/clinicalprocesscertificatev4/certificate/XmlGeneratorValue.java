package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import jakarta.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.common.Mapping;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;

@Component
public class XmlGeneratorValue {

  private final Map<Class<? extends ElementValue>, XmlGeneratorElementData> converters;

  public XmlGeneratorValue(List<XmlGeneratorElementData> converters) {
    this.converters = converters.stream()
        .collect(Collectors.toMap(XmlGeneratorElementData::supports, Function.identity()));
  }

  public List<Svar> generate(Certificate certificate) {
    final var answerList = new ArrayList<Svar>();

    mappings(certificate.certificateModel(), certificate.elementData())
        .forEach(mapping -> {
              if (noCustomMapping(mapping)) {
                answerList.addAll(mapping.getAnswers());
                return;
              }

              final var answerToMapTo = answerToMapTo(mapping, answerList);
              if (answerToMapTo.isEmpty()) {
                throw new IllegalStateException(
                    "Cannot resolve custom mapping '%s'".formatted(mapping)
                );
              }

              answerToMapTo.get().getDelsvar().addAll(
                  mapping.getAnswers().stream()
                      .map(answer -> answer.getDelsvar().stream().toList())
                      .flatMap(List::stream)
                      .toList()
              );
            }
        );

    return answerList;
  }

  private List<Mapping> mappings(CertificateModel certificateModel,
      List<ElementData> elementData) {
    return elementData.stream()
        .filter(data -> !(data.value() instanceof ElementValueUnitContactInformation))
        .filter(data -> certificateModel.elementSpecification(data.id()).includeInXml())
        .map(data -> {
              final var converter = converters.get(data.value().getClass());
              if (converter == null) {
                throw new IllegalStateException(
                    "Converter for '%s' not found".formatted(data.value().getClass())
                );
              }
              return Mapping.builder()
                  .mapping(
                      certificateModel
                          .elementSpecification(data.id())
                          .mapping())
                  .answers(
                      converter.generate(data, certificateModel.elementSpecification(data.id()))
                  )
                  .build();
            }
        )
        .toList();
  }

  private static boolean noCustomMapping(Mapping mapping) {
    return mapping.getMapping() == null;
  }

  private static Optional<Svar> answerToMapTo(Mapping mapping, ArrayList<Svar> answerList) {
    return answerList.stream()
        .filter(svar ->
            svar.getId().equalsIgnoreCase(mapping.getMapping().elementId().id())
        )
        .filter(svar ->
            mapping.getMapping().code() == null || svar.getDelsvar().stream()
                .anyMatch(delsvar ->
                    delsvar.getContent().stream()
                        .filter(JAXBElement.class::isInstance)
                        .map(JAXBElement.class::cast)
                        .map(JAXBElement::getValue)
                        .filter(CVType.class::isInstance)
                        .map(CVType.class::cast)
                        .anyMatch(cvType ->
                            cvType.getCode().equalsIgnoreCase(mapping.getMapping().code().code())
                        )
                )
        )
        .findAny();
  }
}
