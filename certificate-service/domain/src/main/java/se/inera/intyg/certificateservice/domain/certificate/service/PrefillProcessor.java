package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.PrefillXml;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

public interface PrefillProcessor {

  List<ElementData> prefill(CertificateModel certificateModel, PrefillXml prefillXml);


}
