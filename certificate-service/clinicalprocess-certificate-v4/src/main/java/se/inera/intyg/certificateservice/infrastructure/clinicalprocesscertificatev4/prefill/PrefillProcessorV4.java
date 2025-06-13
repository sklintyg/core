package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.PrefillXml;
import se.inera.intyg.certificateservice.domain.certificate.service.PrefillProcessor;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Slf4j
@RequiredArgsConstructor
public class PrefillProcessorV4 implements PrefillProcessor {

  private final ConfigurationConvertertMapper configurationConvertertMapper;

  @Override
  public List<ElementData> prefill(CertificateModel certificateModel, PrefillXml prefillXml) {
    final var unmarshalledPrefill = unmarshallPrefill(prefillXml);
    if (unmarshalledPrefill == null) {
      return List.of();
    }
    final var results = prefillAnswers(certificateModel, unmarshalledPrefill);

    results.stream()
        .map(PrefillResult::getErrorDetails)
        .forEach(result -> result.ifPresent(
            errorDetails -> log.warn("Error pre-filling answer: {}", errorDetails)));
    log.info("Finished prefilling answers for certificate");

    return results.stream()
        .map(PrefillResult::getElementData)
        .filter(Objects::nonNull)
        .toList();
  }

  private List<PrefillResult> prefillAnswers(CertificateModel certificateModel,
      Forifyllnad unmarshalledPreFill) {
    log.info("Begin prefilling answers for certificate");
    final var results = new ArrayList<PrefillResult>();
    results.addAll(prefillAnswers(unmarshalledPreFill.getSvar(), certificateModel));
    results.addAll(prefillSubAnswers(unmarshalledPreFill.getSvar(), certificateModel));
    return results;
  }

  private Collection<? extends PrefillResult> prefillSubAnswers(List<Svar> svar,
      CertificateModel certificateModel) {
    return svar.stream()
        .flatMap(s -> s.getDelsvar().stream())
        .collect(Collectors.groupingBy(Delsvar::getId))
        .entrySet().stream()
        .map(entry -> certificateModel.elementSpecificationOptional(new ElementId(entry.getKey()))
            .map(spec -> configurationConvertertMapper.prefillSubAnswer(entry.getValue(), spec))
            .orElse(null))
        .filter(Objects::nonNull)
        .toList();
  }

  private Collection<PrefillResult> prefillAnswers(List<Svar> svar,
      CertificateModel certificateModel) {
    return svar
        .stream()
        .collect(Collectors.groupingBy(Svar::getId))
        .entrySet().stream()
        .map(entry -> certificateModel.elementSpecificationOptional(new ElementId(entry.getKey()))
            .map(spec -> configurationConvertertMapper.prefillAnswer(entry.getValue(), spec))
            .orElse(null))
        .filter(Objects::nonNull)
        .toList();
  }

  private Forifyllnad unmarshallPrefill(PrefillXml prefillXml) {
    if (prefillXml == null) {
      return null;
    }

    final var xml = prefillXml.decode();
    if (xml == null) {
      return null;
    }

    try {
      final var context = JAXBContext.newInstance(Forifyllnad.class);
      final var unmarshaller = context.createUnmarshaller();
      final var stringReader = new StringReader(xml);
      final var jaxbElement = (JAXBElement<Forifyllnad>) unmarshaller.unmarshal(
          stringReader);

      return jaxbElement.getValue();
    } catch (Exception ex) {
      log.error("Failed to unmarshal prefillXml", ex);
      return null;
    }
  }

}
