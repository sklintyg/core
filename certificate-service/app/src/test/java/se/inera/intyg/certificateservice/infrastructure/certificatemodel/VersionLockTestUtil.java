package se.inera.intyg.certificateservice.infrastructure.certificatemodel;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class VersionLockTestUtil {

  // TODO: Add AI-label

  private static final String SNAPSHOT_RESOURCE_DIR = "certificate-snapshots";

  private VersionLockTestUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static StringBuilder compareTrees(String path,
      JsonNode expected,
      JsonNode actual) {
    final var differences = new StringBuilder();

    if (expected.equals(actual)) {
      return differences;
    }

    if (expected.getNodeType() != actual.getNodeType()) {
      differences.append(String.format("  %s: type changed from %s to %s%n",
          path.isEmpty() ? "root" : path,
          expected.getNodeType(),
          actual.getNodeType()));
      return differences;
    }

    if (expected.isObject()) {
      compareObjectFields(path, expected, actual, differences);
      return differences;
    }

    if (expected.isArray()) {
      compareArrayElements(path, expected, actual, differences);
      return differences;
    }

    differences.append(String.format("  %s: value changed from '%s' to '%s'%n",
        path.isEmpty() ? "root" : path,
        formatValue(expected),
        formatValue(actual)));

    return differences;
  }

  private static void compareObjectFields(String path, JsonNode expected, JsonNode actual,
      StringBuilder differences) {
    StreamSupport.stream(((Iterable<String>) expected::fieldNames).spliterator(), false)
        .forEach(fieldName -> {
          final var fieldPath = buildPath(path, fieldName);
          if (actual.has(fieldName)) {
            differences.append(
                compareTrees(fieldPath, expected.get(fieldName), actual.get(fieldName)));
          } else {
            differences.append(String.format("  %s: field removed (was: %s)%n",
                fieldPath, formatValue(expected.get(fieldName))));
          }
        });

    StreamSupport.stream(((Iterable<String>) actual::fieldNames).spliterator(), false)
        .filter(fieldName -> !expected.has(fieldName))
        .forEach(fieldName -> {
          final var fieldPath = buildPath(path, fieldName);
          differences.append(String.format("  %s: field added (value: %s)%n",
              fieldPath, formatValue(actual.get(fieldName))));
        });
  }

  private static void compareArrayElements(String path, JsonNode expected, JsonNode actual,
      StringBuilder differences) {
    if (expected.size() != actual.size()) {
      differences.append(String.format("  %s: array size changed from %d to %d%n",
          path.isEmpty() ? "root" : path, expected.size(), actual.size()));
    }

    final var minSize = Math.min(expected.size(), actual.size());
    IntStream.range(0, minSize)
        .forEach(i -> {
          final var elementId = getElementId(expected.get(i));
          final var indexPath = elementId != null
              ? path + "[" + i + " (id: " + elementId + ")]"
              : path + "[" + i + "]";
          differences.append(compareTrees(indexPath, expected.get(i), actual.get(i)));
        });
  }

  public static String buildPath(String currentPath, String fieldName) {
    if (currentPath.isEmpty()) {
      return fieldName;
    }
    return currentPath + "." + fieldName;
  }

  public static String getElementId(JsonNode node) {
    if (!node.isObject() || !node.has("id")) {
      return null;
    }

    final var idNode = node.get("id");
    return Optional.ofNullable(idNode.has("id") ? idNode.get("id").asText() : null)
        .or(() -> Optional.ofNullable(idNode.has("value") ? idNode.get("value").asText() : null))
        .or(() -> idNode.isTextual() ? Optional.of(idNode.asText()) : Optional.empty())
        .orElse(null);
  }

  public static String formatValue(JsonNode node) {
    return node.isTextual() ? node.asText() : node.toString();
  }

  public static Path getSnapshotPath(String snapshotFileName) {
    final var resourceUrl = VersionLockTestUtil.class.getClassLoader()
        .getResource(SNAPSHOT_RESOURCE_DIR + "/" + snapshotFileName);

    if (resourceUrl != null) {
      return Paths.get(resourceUrl.getPath().substring(1));
    }

    return Paths.get("src/test/resources", SNAPSHOT_RESOURCE_DIR, snapshotFileName);
  }

  public static void generateSnapshot(String json, String snapshotFileName) throws IOException {
    final var resourceDir = Paths.get("src/test/resources", SNAPSHOT_RESOURCE_DIR);
    Files.createDirectories(resourceDir);

    final var snapshotPath = resourceDir.resolve(snapshotFileName);
    Files.writeString(snapshotPath, json);
  }

}
