package se.inera.intyg.cts.infrastructure.integration.Intygstjanst;

import java.util.Arrays;
import java.util.UUID;
import se.inera.intyg.cts.domain.model.Certificate;
import se.inera.intyg.cts.domain.model.CertificateBatch;
import se.inera.intyg.cts.domain.model.CertificateId;
import se.inera.intyg.cts.domain.model.CertificateSummary;
import se.inera.intyg.cts.domain.model.CertificateXML;
import se.inera.intyg.cts.infrastructure.integration.GetCertificateBatch;

public class GetCertificateBatchFromIntygstjanst implements GetCertificateBatch {

  @Override
  public CertificateBatch get(String careProvider, int limit, int offset) {
    return new CertificateBatch(
        new CertificateSummary(10, 2),
        Arrays.asList(
            new Certificate(
                new CertificateId(UUID.randomUUID().toString()),
                false,
                new CertificateXML("<xml></xml>")
            ),
            new Certificate(
                new CertificateId(UUID.randomUUID().toString()),
                true,
                new CertificateXML("<xml></xml>")
            ),
            new Certificate(
                new CertificateId(UUID.randomUUID().toString()),
                false,
                new CertificateXML("<xml></xml>")
            ),
            new Certificate(
                new CertificateId(UUID.randomUUID().toString()),
                false,
                new CertificateXML("<xml></xml>")
            ),
            new Certificate(
                new CertificateId(UUID.randomUUID().toString()),
                false,
                new CertificateXML("<xml></xml>")
            ),
            new Certificate(
                new CertificateId(UUID.randomUUID().toString()),
                false,
                new CertificateXML("<xml></xml>")
            ),
            new Certificate(
                new CertificateId(UUID.randomUUID().toString()),
                false,
                new CertificateXML("<xml></xml>")
            ),
            new Certificate(
                new CertificateId(UUID.randomUUID().toString()),
                true,
                new CertificateXML("<xml></xml>")
            ),
            new Certificate(
                new CertificateId(UUID.randomUUID().toString()),
                false,
                new CertificateXML("<xml></xml>")
            ),
            new Certificate(
                new CertificateId(UUID.randomUUID().toString()),
                false,
                new CertificateXML("<xml></xml>")
            )
        )
    );
  }
}
