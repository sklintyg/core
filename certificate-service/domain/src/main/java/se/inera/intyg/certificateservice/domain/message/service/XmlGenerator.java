package se.inera.intyg.certificateservice.domain.message.service;

import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.message.model.Answer;

public interface XmlGenerator {

  Xml generate(Answer answer);
}
