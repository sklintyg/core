package se.inera.intyg.cts.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Termination {

  private final TerminationId terminationId;
  private final LocalDateTime created;
  private final Staff creator;
  private final CareProvider careProvider;
  private TerminationStatus status;
  private final Export export;

  Termination(TerminationId terminationId, LocalDateTime created, Staff creator,
      CareProvider careProvider, TerminationStatus status, Export export) {
    if (terminationId == null) {
      throw new IllegalArgumentException("Missing TerminationId");
    }
    if (created == null) {
      throw new IllegalArgumentException("Missing Created");
    }
    if (creator == null) {
      throw new IllegalArgumentException("Missing Creator");
    }
    if (careProvider == null) {
      throw new IllegalArgumentException("Missing CareProvider");
    }
    if (status == null) {
      throw new IllegalArgumentException("Missing Status");
    }
    if (export == null) {
      throw new IllegalArgumentException("Missing Export");
    }
    this.terminationId = terminationId;
    this.created = created;
    this.creator = creator;
    this.careProvider = careProvider;
    this.status = status;
    this.export = export;
  }

  public void collect(CertificateBatch certificateBatch) {
    if (status == TerminationStatus.CREATED) {
      status = TerminationStatus.COLLECTING_CERTIFICATES;
    }

    export().processBatch(certificateBatch);

    if (export().certificateSummary().equals(certificateBatch.certificateSummary())) {
      status = TerminationStatus.COLLECTING_CERTIFICATES_COMPLETED;
    }
  }

  public void collect(List<CertificateText> certificateTexts) {
    status = TerminationStatus.COLLECTING_CERTIFICATE_TEXTS_COMPLETED;
  }

  public void exported(Password password) {
    export().packagePassword(password);
    status = TerminationStatus.EXPORTED;
  }

  public void receiptReceived(LocalDateTime receiptTime) {
    export().receiptTime(receiptTime);
    status = TerminationStatus.RECEIPT_RECEIVED;
  }

  public void passwordSent() {
    status = TerminationStatus.PASSWORD_SENT;
  }

  public TerminationId terminationId() {
    return terminationId;
  }

  public LocalDateTime created() {
    return created;
  }

  public Staff creator() {
    return creator;
  }

  public CareProvider careProvider() {
    return careProvider;
  }

  public TerminationStatus status() {
    return status;
  }

  public Export export() {
    return export;
  }

  @Override
  public String toString() {
    return "Termination{" +
        "terminationId=" + terminationId +
        ", created=" + created +
        ", creator=" + creator +
        ", careProvider=" + careProvider +
        ", status=" + status +
        ", export=" + export +
        '}';
  }
}
