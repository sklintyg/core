package se.inera.intyg.certificateservice.testability.certificate.service.fillservice;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;

public interface TestabilityCertificateFillService {

  List<CertificateModelId> certificateModelIds();

  List<ElementData> fill(CertificateModel certificateModel, TestabilityFillTypeDTO fillType);
}
