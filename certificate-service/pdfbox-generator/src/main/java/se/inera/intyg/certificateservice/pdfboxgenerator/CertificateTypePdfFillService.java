package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

public interface CertificateTypePdfFillService {

  String getPatientIdFieldId();

  List<PdfField> getFields(Certificate certificate);

  CertificateType getType();

}
