package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;

public interface CertificateTypePdfFillService {

  String getPatientIdFieldId();

  List<PdfField> getFields(Certificate certificate);

  CertificateType getType();

  int getAvailableMcid();

  int getSignatureTagIndex(boolean includeAddress);
}
