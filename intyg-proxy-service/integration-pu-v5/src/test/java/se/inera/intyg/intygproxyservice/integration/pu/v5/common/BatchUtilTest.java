package se.inera.intyg.intygproxyservice.integration.pu.v5.common;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class BatchUtilTest {

  @Test
  void shouldSplitUpIntoBatchWithOneItemEachIfSizeIs1() {
    final var response = BatchUtil.split(List.of("s1", "s2", "s3"), 1);
    assertAll(
        () -> assertEquals(3, response.size()),
        () -> assertEquals("s1", response.getFirst().getFirst()),
        () -> assertEquals("s2", response.get(1).getFirst()),
        () -> assertEquals("s3", response.get(2).getFirst())
    );
  }

  @Test
  void shouldSplitUpIntoBatchWithAllItemsIfBatchSizeIsBiggerThanListSize() {
    final var response = BatchUtil.split(List.of("s1", "s2", "s3"), 5);
    assertAll(
        () -> assertEquals(1, response.size()),
        () -> assertEquals("s1", response.getFirst().getFirst()),
        () -> assertEquals("s2", response.getFirst().get(1)),
        () -> assertEquals("s3", response.getFirst().get(2))
    );
  }

}