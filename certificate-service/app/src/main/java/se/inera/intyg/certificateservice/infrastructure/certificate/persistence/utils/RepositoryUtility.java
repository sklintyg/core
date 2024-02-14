package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.utils;

import java.util.function.Function;
import org.springframework.data.repository.CrudRepository;

public class RepositoryUtility {

  public static <E, O> E saveIfNotExists(E entity, O object, Function<O, E> mapper,
      CrudRepository<E, Long> repo) {
    if (entity == null) {
      return repo.save(mapper.apply(object));
    }

    return entity;
  }

  public static <T, R> T getEntity(T entity, R object, Function<R, T> mapper) {
    return entity != null ? entity : mapper.apply(object);
  }

}
