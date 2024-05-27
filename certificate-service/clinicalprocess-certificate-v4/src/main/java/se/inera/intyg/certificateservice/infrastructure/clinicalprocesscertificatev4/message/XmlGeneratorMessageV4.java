package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.message;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.service.XmlGenerator;

@RequiredArgsConstructor
public class XmlGeneratorMessageV4 implements XmlGenerator {

  @Override
  public Xml generate(Answer answer) {
    return null;
  }
}

