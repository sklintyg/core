package se.inera.intyg.certificateprintservice.print;

import se.inera.intyg.certificateprintservice.print.api.PrintCertificateRequest;

public interface PrintCertificateGenerator {

  byte[] generate(PrintCertificateRequest printCertificateRequest);

}