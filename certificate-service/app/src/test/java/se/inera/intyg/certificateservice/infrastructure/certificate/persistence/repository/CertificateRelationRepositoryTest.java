package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateRelationEntity;

@ExtendWith(MockitoExtension.class)
class CertificateRelationRepositoryTest {

  private static final String ID = "id";
  @Mock
  private CertificateEntityRepository certificateEntityRepository;
  @Mock
  public CertificateRelationEntityRepository relationEntityRepository;
  @InjectMocks
  private CertificateRelationRepository certificateRelationRepository;

  @Nested
  class SaveTest {

    @Test
    void shallNotContinueIfParentIsNull() {
      final var certificate = Certificate.builder().build();
      certificateRelationRepository.save(certificate, null);
      verifyNoInteractions(relationEntityRepository);
    }

    @Test
    void shallNotSaveIfCertificateAlreadyHasChildRelation() {
      final var certificate = Certificate.builder()
          .parent(Relation.builder().build())
          .build();

      final var certificateEntity = CertificateEntity.builder().build();

      doReturn(List.of(CertificateEntity.builder().build())).when(relationEntityRepository)
          .findByChildCertificate(certificateEntity);

      certificateRelationRepository.save(certificate, certificateEntity);

      verify(relationEntityRepository, times(0)).save(any());
    }

    @Test
    void shallSaveIfCertificateDoesNotHaveChildRelation() {
      final var certificate = Certificate.builder()
          .parent(
              Relation.builder()
                  .certificateId(new CertificateId(ID))
                  .type(RelationType.REPLACE)
                  .build())
          .build();

      final var parentEntity = CertificateEntity.builder().build();

      final var certificateEntity = CertificateEntity.builder().build();

      doReturn(Collections.emptyList()).when(relationEntityRepository)
          .findByChildCertificate(certificateEntity);
      doReturn(Optional.of(parentEntity)).when(certificateEntityRepository)
          .findByCertificateId(ID);

      certificateRelationRepository.save(certificate, certificateEntity);

      verify(relationEntityRepository, times(1)).save(any());
    }
  }

  @Nested
  class RelationsTests {

    @Test
    void shallReturnListOfRelations() {
      final var expectedResult = List.of(CertificateRelationEntity.builder().build());
      final var certificateEntity = CertificateEntity.builder().build();
      doReturn(Optional.of(expectedResult)).when(relationEntityRepository)
          .findByParentCertificateOrChildCertificate(
              certificateEntity, certificateEntity
          );

      final var actualResult = certificateRelationRepository.relations(certificateEntity);
      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shallReturnEmptyListOfRelations() {
      final var certificateEntity = CertificateEntity.builder().build();
      doReturn(Optional.empty()).when(relationEntityRepository)
          .findByParentCertificateOrChildCertificate(
              certificateEntity, certificateEntity
          );

      final var actualResult = certificateRelationRepository.relations(certificateEntity);
      assertEquals(Collections.emptyList(), actualResult);
    }
  }
}
