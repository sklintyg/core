package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SqlResultSetMapping(
    name = "CertificateMessageCountMapping",
    classes = @ConstructorResult(
        targetClass = CertificateMessageCountEntity.class,
        columns = {
            @ColumnResult(name = "certificateId", type = String.class),
            @ColumnResult(name = "nrOfMessages", type = Integer.class)
        }
    )
)
@NamedNativeQuery(
    name = "CertificateMessageCountEntity.getMessageCountForCertificates",
    query = """
        SELECT c.certificate_id AS certificateId, COUNT(m.message_id) AS nrOfMessages
        FROM certificate c
        JOIN message m ON m.certificate_key = c.key
        JOIN patient p ON c.patient_key = p.key
        WHERE p.patient_id IN :patientIds
        AND m.message_status_key = '2'
        AND m.created >= DATE_SUB(NOW(), INTERVAL :maxDaysOfUnansweredCommunication DAY)
        AND m.author = 'FK'
        GROUP BY c.certificate_id
        """,
    resultSetMapping = "CertificateMessageCountMapping"
)
public class CertificateMessageCountEntity implements Serializable {

  @Id
  String certificateId;
  Integer messageCount;
}
