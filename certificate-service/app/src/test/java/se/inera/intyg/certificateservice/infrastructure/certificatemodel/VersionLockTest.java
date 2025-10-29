package se.inera.intyg.certificateservice.infrastructure.certificatemodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.CertificateModelFactoryTS8071;

/**
 * Version lock tests to ensure that older certificate versions remain unchanged when new versions
 * are released.
 * <p>
 * This test class uses snapshot testing to verify that the structure and content of certificate
 * models remain exactly as they were when the version was released. When a new major version is
 * created (e.g., TS8071 V2), this test ensures that V1 cannot be accidentally modified.
 * <p>
 * <b>How it works:</b>
 * <ul>
 *   <li>Each certificate version is serialized to JSON and compared against a stored snapshot</li>
 *   <li>If the snapshot doesn't exist, the test will fail and prompt you to create it</li>
 *   <li>If the model has changed, the test will show the differences and fail</li>
 *   <li>Snapshots are stored in src/test/resources/certificate-snapshots/</li>
 * </ul>
 * <p>
 * <b>Adding a new version lock:</b>
 * <ol>
 *   <li>Inject the CertificateModelFactory for the version you want to lock</li>
 *   <li>Add an entry to the {@code lockedVersions()} method with the factory and snapshot filename</li>
 *   <li>Run the test - it will generate a snapshot in src/test/resources/certificate-snapshots/</li>
 *   <li>Review the generated JSON file to ensure it looks correct</li>
 *   <li>Run the test again - it should now pass</li>
 * </ol>
 * <p>
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles({"test", "testability"})
class VersionLockTest {

  @Mock
  private static CertificateActionFactory certificateActionFactory;

  private ObjectMapper objectMapper;
  private static final CertificateModelFactory ts8071FactoryV1 = new CertificateModelFactoryTS8071(
      certificateActionFactory
  );

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        .findAndRegisterModules();
  }

  /**
   * Asserts that a certificate model matches its stored snapshot.
   *
   * <p>This method:
   * <ol>
   *   <li>Serializes the certificate model to JSON</li>
   *   <li>Compares it with the stored snapshot file</li>
   *   <li>If no snapshot exists, generates one in src/test/resources/certificate-snapshots/ and fails</li>
   *   <li>If snapshot exists but differs, shows the difference and fails</li>
   * </ol>
   *
   * @param certificateModel The certificate model to verify
   * @param snapshotFileName The snapshot file name (e.g., "ts8071-v1.0.json")
   */
  @ParameterizedTest(name = "{2} should remain unchanged")
  @MethodSource("lockedVersions")
  void certificateVersionShouldRemainUnchanged(
      CertificateModel certificateModel,
      String snapshotFileName,
      @SuppressWarnings("unused") String displayName
  ) {
    assertVersionLocked(certificateModel, snapshotFileName);
  }

  private static Stream<Arguments> lockedVersions() {
    return Stream.of(
        Arguments.of(ts8071FactoryV1.create(), "ts8071-v1.0.json", "TS8071 V1")
    );
  }

  private void assertVersionLocked(CertificateModel certificateModel, String snapshotFileName) {
    try {
      final var actualJson = objectMapper.writeValueAsString(certificateModel);
      final var snapshotPath = VersionLockTestUtil.getSnapshotPath(snapshotFileName);

      if (!Files.exists(snapshotPath)) {
        VersionLockTestUtil.generateSnapshot(actualJson, snapshotFileName);
        fail(String.format(
            "Snapshot file did not exist and has been created at: %s%n"
                + "Review the generated snapshot file to ensure it looks correct.%n"
                + "Then run the test again to verify the snapshot.",
            snapshotPath
        ));
      }

      final var expectedJson = Files.readString(snapshotPath);
      final var normalizedExpected = normalizeJson(expectedJson);
      final var normalizedActual = normalizeJson(actualJson);
      final var differences = findDifferences(expectedJson, actualJson);

      assertEquals(normalizedExpected, normalizedActual,
          String.format(
              "Certificate model %s has changed. This version should be locked.%n"
                  + "%n"
                  + "Differences found:%n%s%n"
                  + "%n"
                  + "If this change is intentional and you understand the impact:%n"
                  + "1. Review the differences carefully%n"
                  + "2. Update the snapshot file at: %s%n"
                  + "3. Document the reason for the change in your commit message",
              certificateModel.id(),
              differences,
              snapshotPath
          ));

    } catch (IOException e) {
      fail("Failed to process snapshot: " + e.getMessage(), e);
    }
  }

  private String normalizeJson(String json) throws IOException {
    final var tree = objectMapper.readTree(json);
    return objectMapper.writeValueAsString(tree);
  }

  private String findDifferences(String expectedJson, String actualJson) throws IOException {
    final var expectedTree = objectMapper.readTree(expectedJson);
    final var actualTree = objectMapper.readTree(actualJson);

    final var differences = VersionLockTestUtil.compareTrees("", expectedTree, actualTree);

    return !differences.isEmpty() ? differences.toString() : "No specific differences found";
  }
}

