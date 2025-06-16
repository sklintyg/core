package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import java.io.StringReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.service.PrefillProcessor;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@Slf4j
@RequiredArgsConstructor
public class PrefillProcessorV4 implements PrefillProcessor {

  private final ConfigurationConverterMapper configurationConverterMapper;

  @Override
  public List<ElementData> prefill(CertificateModel certificateModel, Xml prefillXml) {
    final var unmarshalledPrefill = unmarshallPrefill(prefillXml);
    if (unmarshalledPrefill == null) {
      return List.of();
    }

    final var prefillResult = PrefillResult.prepare(certificateModel, unmarshalledPrefill,
        configurationConverterMapper);
    log.info("Begin prefilling answers for certificate");
    prefillResult.prefill();
    log.info("Prefill result log: {}", prefillResult.toJsonReport());
    return prefillResult.prefilledElements();
  }

  private Forifyllnad unmarshallPrefill(Xml prefillXml) {
    if (prefillXml == null) {
      return null;
    }

    final var xml = prefillXml.decode();

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
