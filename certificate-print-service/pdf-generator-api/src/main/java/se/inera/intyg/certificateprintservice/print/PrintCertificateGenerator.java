package se.inera.intyg.certificateprintservice.print;

import se.inera.intyg.certificateprintservice.print.api.Certificate;

public interface PrintCertificateGenerator {

  byte[] generate(Certificate certificate);

}