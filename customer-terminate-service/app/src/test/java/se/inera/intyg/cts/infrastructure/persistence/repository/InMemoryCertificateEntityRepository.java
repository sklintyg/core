package se.inera.intyg.cts.infrastructure.persistence.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.random.RandomGenerator;
import se.inera.intyg.cts.infrastructure.persistence.entity.CertificateEntity;

public class InMemoryCertificateEntityRepository implements CertificateEntityRepository {

  private Map<Long, CertificateEntity> repository = new HashMap<>();

  @Override
  public <S extends CertificateEntity> S save(S entity) {
    if (entity.getId() == null) {
      entity.setId(RandomGenerator.getDefault().nextLong());
    }

    repository.put(entity.getId(), entity);

    return entity;
  }

  @Override
  public <S extends CertificateEntity> Iterable<S> saveAll(Iterable<S> entities) {
    final var saved = new ArrayList<CertificateEntity>();
    entities.forEach(entity -> saved.add(save(entity)));
    return (Iterable<S>) saved;
  }

  @Override
  public Optional<CertificateEntity> findById(Long aLong) {
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
  public Iterable<CertificateEntity> findAll() {
    return repository.values();
  }

  @Override
  public Iterable<CertificateEntity> findAllById(Iterable<Long> longs) {
    final var saved = new ArrayList<CertificateEntity>();
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
  public void delete(CertificateEntity entity) {
    repository.remove(entity.getId());
  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {
    longs.forEach(this::deleteById);
  }

  @Override
  public void deleteAll(Iterable<? extends CertificateEntity> entities) {
    entities.forEach(this::delete);
  }

  @Override
  public void deleteAll() {
    repository.clear();
  }
}
