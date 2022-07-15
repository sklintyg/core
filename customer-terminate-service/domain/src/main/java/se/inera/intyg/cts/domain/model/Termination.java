package se.inera.intyg.cts.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Termination {

  private final TerminationId terminationId;
  private final LocalDateTime created;
  private final Staff creator;
  private final CareProvider careProvider;
  private TerminationStatus status;
  private final Export export;
  private Erase erase;

  Termination(TerminationId terminationId, LocalDateTime created, Staff creator,
      CareProvider careProvider, TerminationStatus status, Export export, Erase erase) {
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
    if (erase == null) {
      throw new IllegalArgumentException("Missing Erase");
    }
    this.terminationId = terminationId;
    this.created = created;
    this.creator = creator;
    this.careProvider = careProvider;
    this.status = status;
    this.export = export;
    this.erase = erase;
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

  public Erase erase() {
    return erase;
  }

  public void initiateErase() {
    status = TerminationStatus.START_ERASE;
  }

  public void startErase(List<EraseService> eraseServices) {
    erase = new Erase(eraseServices);
    status = TerminationStatus.ERASE_IN_PROGRESS;
  }

  public void eraseCancelled() {
    status = TerminationStatus.ERASE_CANCELLED;
  }

  public void erased(ServiceId serviceId) {
    erase = new Erase(
        erase.eraseServices().stream()
            .map(service -> {
              if (service.serviceId().equals(serviceId)) {
                return new EraseService(serviceId, true);
              }
              return service;
            })
            .collect(Collectors.toList())
    );

    if (erase.eraseServices().stream().allMatch(eraseService -> eraseService.erased())) {
      status = TerminationStatus.ERASE_COMPLETED;
    }
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
        ", erase=" + erase +
        '}';
  }
}
