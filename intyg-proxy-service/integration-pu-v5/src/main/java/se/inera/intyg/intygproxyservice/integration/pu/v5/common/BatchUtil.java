package se.inera.intyg.intygproxyservice.integration.pu.v5.common;

import java.util.List;
import java.util.stream.Collectors;

public class BatchUtil {

  private BatchUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static <T> List<List<T>> split(List<T> list, int batchSize) {
    return list.stream()
        .collect(Collectors.groupingBy(object -> (list.indexOf(object)) / batchSize))
        .values()
        .stream()
        .toList();
  }

}
