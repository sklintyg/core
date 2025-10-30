package se.inera.intyg.certificateservice.infrastructure.certificatemodel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SnapshotTestUtil {

  private static final String SNAPSHOT_RESOURCE_DIR = "certificate-model-snapshots";

  private SnapshotTestUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static Path getSnapshotPath(String snapshotFileName) {
    final var resourceUrl = SnapshotTestUtil.class.getClassLoader()
        .getResource(SNAPSHOT_RESOURCE_DIR + "/" + snapshotFileName);

    if (resourceUrl != null) {
      return Paths.get(resourceUrl.getPath().substring(1));
    }

    return Paths.get("src/test/resources", SNAPSHOT_RESOURCE_DIR, snapshotFileName);
  }

  public static void generateSnapshot(String json, String snapshotFileName) throws IOException {
    final var resourceDir = Paths.get("src/test/resources", SNAPSHOT_RESOURCE_DIR);
    final var snapshotPath = resourceDir.resolve(snapshotFileName);
    Files.writeString(snapshotPath, json);
  }
}
