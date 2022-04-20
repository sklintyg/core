package se.inera.intyg.cts.infrastructure.persistence.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import se.inera.intyg.cts.infrastructure.persistence.entity.CertificateTextEntity;
import se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntity;

public class InMemoryCertificateTextsEntityRepository implements CertificateTextEntityRepository {

  private Map<Long, CertificateTextEntity> repository = new HashMap<>();

  @Override
  public <S extends CertificateTextEntity> S save(S entity) {
    if (entity.getId() == null || entity.getId() < 1L) {
      entity.setId(RandomGenerator.getDefault().nextLong());
    }

    repository.put(entity.getId(), entity);

    return entity;
  }

  @Override
  public <S extends CertificateTextEntity> Iterable<S> saveAll(Iterable<S> entities) {
    final var saved = new ArrayList<CertificateTextEntity>();
    entities.forEach(entity -> saved.add(save(entity)));
    return (Iterable<S>) saved;
  }

  @Override
  public Optional<CertificateTextEntity> findById(Long aLong) {
    if (repository.containsKey(aLong)) {
      return Optional.of(repository.get(aLong));
    }
    return Optional.empty();
  }

  @Override
  public boolean existsById(Long aLong) {
    return repository.containsKey(aLong);
  }

  @Override
  public Iterable<CertificateTextEntity> findAll() {
    return repository.values();
  }

  @Override
  public Iterable<CertificateTextEntity> findAllById(Iterable<Long> longs) {
    final var saved = new ArrayList<CertificateTextEntity>();
    longs.forEach(id -> {
      if (repository.containsKey(id)) {
        saved.add(repository.get(id));
      }
    });
    return saved;
  }

  @Override
  public long count() {
    return repository.size();
  }

  @Override
  public void deleteById(Long aLong) {
    repository.remove(aLong);
  }

  @Override
  public void delete(CertificateTextEntity entity) {
    repository.remove(entity.getId());
  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {
    longs.forEach(this::deleteById);
  }

  @Override
  public void deleteAll(Iterable<? extends CertificateTextEntity> entities) {
    entities.forEach(this::delete);
  }

  @Override
  public void deleteAll() {
    repository.clear();
  }

  @Override
  public List<CertificateTextEntity> findAllByTermination(TerminationEntity terminationEntity) {
    return repository.values().stream()
        .filter(certificateTextEntity -> certificateTextEntity.getTermination().getTerminationId()
            == terminationEntity.getTerminationId())
        .collect(Collectors.toList());
  }
}
