package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;

public interface XmlGeneratorCertificatesForCareWithQA {


  Xml generate(List<Certificate> certificate);
}
