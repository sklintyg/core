package se.inera.intyg.certificateservice.infrastructure.certificatemodel;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class JsonTreeTestUtil {

  private JsonTreeTestUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static StringBuilder compareTrees(String path,
      JsonNode expected,
      JsonNode actual) {
    final var differences = new StringBuilder();

    if (expected.equals(actual)) {
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

  // Generated using AI support with model: Claude Sonnet 4.5
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

  // Generated using AI support with model: Claude Sonnet 4.5
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

  // Generated using AI support with model: Claude Sonnet 4.5
  private static String getElementId(JsonNode node) {
    if (!node.isObject() || !node.has("id")) {
      return null;
    }

    final var idNode = node.get("id");
    return Optional.ofNullable(idNode.has("id") ? idNode.get("id").asText() : null)
        .or(() -> Optional.ofNullable(idNode.has("value") ? idNode.get("value").asText() : null))
        .or(() -> idNode.isTextual() ? Optional.of(idNode.asText()) : Optional.empty())
        .orElse(null);
  }

  private static String formatValue(JsonNode node) {
    return node.isTextual() ? node.asText() : node.toString();
  }


  private static String buildPath(String currentPath, String fieldName) {
    if (currentPath.isEmpty()) {
      return fieldName;
    }
    return currentPath + "." + fieldName;
  }
}