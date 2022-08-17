package se.inera.intyg.cts.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_CREATOR_HSA_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_CREATOR_NAME;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_EMAIL_ADDRESS;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_HSA_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_ORGANIZATION_NUMBER;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_PERSON_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_PHONE_NUMBER;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_TERMINATION_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTerminationEntity;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import se.inera.intyg.cts.application.dto.CreateTerminationDTO;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.SendPackagePassword;
import se.inera.intyg.cts.infrastructure.persistence.JpaTerminationRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.InMemoryTerminationEntityRepository;

@ExtendWith(MockitoExtension.class)
class TerminationServiceImplTest {

  private TerminationServiceImpl terminationServiceImpl;
  private TerminationRepository terminationRepository;
  private InMemoryTerminationEntityRepository inMemoryTerminationEntityRepository;

  @Mock
  private SendPackagePassword sendPackagePasswordMock;
  @Mock
  private TerminationRepository terminationRepositoryMock;

  private Termination termination;

  @BeforeEach
  void setUp() {
    inMemoryTerminationEntityRepository = new InMemoryTerminationEntityRepository();
    terminationRepository = new JpaTerminationRepository(inMemoryTerminationEntityRepository);
    terminationServiceImpl = new TerminationServiceImpl(terminationRepository, sendPackagePasswordMock);

    termination = defaultTermination();
  }

  @Nested
  class CreateTermination {

    private CreateTerminationDTO createTerminationDTO;

    @BeforeEach
    void setUp() {
      createTerminationDTO = new CreateTerminationDTO(DEFAULT_CREATOR_HSA_ID, DEFAULT_CREATOR_NAME,
          DEFAULT_HSA_ID, DEFAULT_ORGANIZATION_NUMBER, DEFAULT_PERSON_ID, DEFAULT_PHONE_NUMBER,
          DEFAULT_EMAIL_ADDRESS);
    }

    @Test
    void shallCreateTermination() {
      assertNotNull(terminationServiceImpl.create(createTerminationDTO), "Termination is null");
    }

    @Test
    void shallCreateTerminationWithTerminationId() {
      assertNotNull(terminationServiceImpl.create(createTerminationDTO).terminationId(),
          "TerminationId is null");
    }

    @Test
    void shallCreateTerminationWithCreatorHSAId() {
      assertEquals(DEFAULT_CREATOR_HSA_ID,
          terminationServiceImpl.create(createTerminationDTO).creatorHSAId());
    }

    @Test
    void shallCreateTerminationWithCreatorName() {
      assertEquals(DEFAULT_CREATOR_NAME,
          terminationServiceImpl.create(createTerminationDTO).creatorName());
    }

    @Test
    void shallCreateTerminationWithHSAId() {
      assertEquals(DEFAULT_HSA_ID,
          terminationServiceImpl.create(createTerminationDTO).hsaId());
    }

    @Test
    void shallCreateTerminationWithOrganizationNumber() {
      assertEquals(DEFAULT_ORGANIZATION_NUMBER,
          terminationServiceImpl.create(createTerminationDTO).organizationNumber());
    }

    @Test
    void shallCreateTerminationWithPersonId() {
      assertEquals(DEFAULT_PERSON_ID,
          terminationServiceImpl.create(createTerminationDTO).personId());
    }

    @Test
    void shallCreateTerminationWithPhoneNumber() {
      assertEquals(DEFAULT_PHONE_NUMBER,
          terminationServiceImpl.create(createTerminationDTO).phoneNumber());
    }

    @Test
    void shallCreateTerminationWithEmailAddress() {
      assertEquals(DEFAULT_EMAIL_ADDRESS,
          terminationServiceImpl.create(createTerminationDTO).emailAddress());
    }
  }

  @Test
  void shallReturnExistingTermination() {
    inMemoryTerminationEntityRepository.save(defaultTerminationEntity());

    assertTrue(terminationServiceImpl.findById(DEFAULT_TERMINATION_ID).isPresent(),
        "Shall contain a termination");
  }

  @Test
  void shallReturnEmptyOptionalWhenTerminationIdDoesntExist() {
    assertTrue(terminationServiceImpl.findById(DEFAULT_TERMINATION_ID).isEmpty(),
        "Shall not contain any termination");
  }

  @Test
  void shallReturnAllExistingTerminations() {
    final var numberOfTerminations = 10;
    for (int i = 0; i < numberOfTerminations; i++) {
      inMemoryTerminationEntityRepository.save(
          defaultTerminationEntity(UUID.randomUUID())
      );
    }

    assertEquals(numberOfTerminations, terminationServiceImpl.findAll().size());
  }

  @Test
  void resendKey() throws NotFoundException {
    terminationServiceImpl = new TerminationServiceImpl(terminationRepositoryMock, sendPackagePasswordMock);
    termination.passwordSent();
    when(terminationRepositoryMock.findByTerminationId(termination.terminationId())).thenReturn(Optional.of(termination));

    assertNotNull(terminationServiceImpl.resendPassword(termination.terminationId().id()));

    verify(terminationRepositoryMock, times(1)).findByTerminationId(termination.terminationId());
    verify(sendPackagePasswordMock, times(1)).sendPassword(termination);
  }

  @Test
  void resendKeyNotFound() throws NotFoundException {
    terminationServiceImpl = new TerminationServiceImpl(terminationRepositoryMock, sendPackagePasswordMock);
    termination.passwordSent();
    when(terminationRepositoryMock.findByTerminationId(termination.terminationId())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> {
      terminationServiceImpl.resendPassword(termination.terminationId().id());
    });

    verify(terminationRepositoryMock, times(1)).findByTerminationId(termination.terminationId());
    verify(sendPackagePasswordMock, times(0)).sendPassword(termination);
  }

  @Test
  void resendKeyInvalidTerminationStatus() throws NotFoundException {
    terminationServiceImpl = new TerminationServiceImpl(terminationRepositoryMock, sendPackagePasswordMock);
    when(terminationRepositoryMock.findByTerminationId(termination.terminationId())).thenReturn(Optional.of(termination));

    assertThrows(IllegalArgumentException.class, () -> {
      terminationServiceImpl.resendPassword(termination.terminationId().id());
    });

    verify(terminationRepositoryMock, times(1)).findByTerminationId(termination.terminationId());
    verify(sendPackagePasswordMock, times(0)).sendPassword(termination);
  }
}