package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.Set;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

public interface PrefillProcessor {

  Set<ElementData> prefill(CertificateModel certificateModel, Xml prefillXml, CertificateId id);


}