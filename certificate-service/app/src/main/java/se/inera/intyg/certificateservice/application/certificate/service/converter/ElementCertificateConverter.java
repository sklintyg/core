package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;

@Component
@RequiredArgsConstructor
public class ElementCertificateConverter {

  private final ElementDataConverter elementDataConverter;
  private final ElementMetaDataConverter elementMetaDataConverter;

  public List<ElementData> convert(CertificateDTO certificateDTO) {
    return Stream.concat(
            certificateDTO.getData()
                .entrySet()
                .stream()
                .filter(removeElementsMissingValue())
                .map(entry ->
                    elementDataConverter.convert(entry.getKey(), entry.getValue())
                )
            ,
            elementMetaDataConverter.convert(certificateDTO.getMetadata()).stream()
        )
        .toList();
  }

  private static Predicate<Entry<String, CertificateDataElement>> removeElementsMissingValue() {
    return entry -> entry.getValue().getValue() != null;
  }
}
