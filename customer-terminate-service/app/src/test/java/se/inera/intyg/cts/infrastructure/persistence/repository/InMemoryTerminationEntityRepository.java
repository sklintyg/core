package se.inera.intyg.cts.infrastructure.persistence.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import se.inera.intyg.cts.infrastructure.persistence.entity.TerminationEntity;

public class InMemoryTerminationEntityRepository implements TerminationEntityRepository {

  private Map<Long, TerminationEntity> repository = new HashMap<>();

  @Override
  public <S extends TerminationEntity> S save(S entity) {
    if (entity.getId() == null) {
      entity.setId(RandomGenerator.getDefault().nextLong());
    }

    repository.put(entity.getId(), entity);

    return entity;
  }

  @Override
  public <S extends TerminationEntity> Iterable<S> saveAll(Iterable<S> entities) {
    final var saved = new ArrayList<TerminationEntity>();
    entities.forEach(entity -> saved.add(save(entity)));
    return (Iterable<S>) saved;
  }

  @Override
  public Optional<TerminationEntity> findById(Long aLong) {
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
  public Iterable<TerminationEntity> findAll() {
    return repository.values();
  }

  @Override
  public Iterable<TerminationEntity> findAllById(Iterable<Long> longs) {
    final var saved = new ArrayList<TerminationEntity>();
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
  public void delete(TerminationEntity entity) {
    repository.remove(entity.getId());
  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {
    longs.forEach(this::deleteById);
  }

  @Override
  public void deleteAll(Iterable<? extends TerminationEntity> entities) {
    entities.forEach(this::delete);
  }

  @Override
  public void deleteAll() {
    repository.clear();
  }

  @Override
  public Optional<TerminationEntity> findByTerminationId(UUID terminationId) {
    return repository.values().stream()
        .filter(terminationEntity -> terminationEntity.getTerminationId().equals(terminationId))
        .findAny();
  }

  @Override
  public List<TerminationEntity> findAllByStatusIsIn(List<String> statuses) {
    return repository.values().stream()
        .filter(terminationEntity -> statuses.contains(terminationEntity.getStatus()))
        .collect(Collectors.toList());
  }
}
