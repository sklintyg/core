package se.inera.intyg.certificateservice.domain.message.service;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Message;

public interface XmlGenerator {

  Xml generate(Answer answer, Message message, Certificate certificate);
}
