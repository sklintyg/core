package se.inera.intyg.cts.infrastructure.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import se.inera.intyg.cts.domain.model.Password;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.service.CreatePackage;
import se.inera.intyg.cts.infrastructure.persistence.entity.CertificateEntity;
import se.inera.intyg.cts.infrastructure.persistence.entity.CertificateTextEntity;
import se.inera.intyg.cts.infrastructure.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.CertificateTextEntityRepository;
import se.inera.intyg.cts.infrastructure.persistence.repository.TerminationEntityRepository;

public class CreateEncryptedZipPackage implements CreatePackage {

  public static final String ROOT_FOLDER = "./";
  public static final String CERTIFICATES_FOLDER = "/intyg";
  private static final String CERTIFICATE_TEXT_FOLDER = "/texter";
  public static final String REVOKED_FILENAME = " MAKULERAT";
  public static final String XML_EXTENSION = ".xml";
  public static final String ZIP_EXTENSION = ".zip";

  private final TerminationEntityRepository terminationEntityRepository;
  private final CertificateEntityRepository certificateEntityRepository;
  private final CertificateTextEntityRepository certificateTextEntityRepository;

  public CreateEncryptedZipPackage(TerminationEntityRepository terminationEntityRepository,
      CertificateEntityRepository certificateEntityRepository,
      CertificateTextEntityRepository certificateTextEntityRepository) {
    this.terminationEntityRepository = terminationEntityRepository;
    this.certificateEntityRepository = certificateEntityRepository;
    this.certificateTextEntityRepository = certificateTextEntityRepository;
  }

  @Override
  public File create(Termination termination, Password password) {
    final var terminationEntity = terminationEntityRepository.findByTerminationId(
        termination.terminationId().id()).orElseThrow();
    final var certificates = certificateEntityRepository.findAllByTermination(terminationEntity);
    final var certificateTexts = certificateTextEntityRepository.findAllByTermination(
        terminationEntity);

    createDirIfNotExists(rootDir(termination));
    createDirIfNotExists(certificatesDir(termination));
    createDirIfNotExists(certificateTextsDir(termination));

    certificates.forEach(
        certificateEntity -> writeCertificateFile(termination, certificateEntity)
    );

    certificateTexts.forEach(
        certificateTextEntity -> writeCertificateTextFile(termination, certificateTextEntity)
    );

    final var zipFile = writeZipFile(termination, password).getFile();

    removeDirIfExists(rootDir(termination));

    return zipFile;
  }

  private void createDirIfNotExists(String directory) {
    final var directoryPath = Path.of(directory);
    if (Files.exists(directoryPath)) {
      return;
    }

    try {
      Files.createDirectory(directoryPath);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Could not create directory: %s", directoryPath), e);
    }
  }

  private String rootDir(Termination termination) {
    return ROOT_FOLDER + termination.careProvider().hsaId().id();
  }

  private String certificatesDir(Termination termination) {
    return rootDir(termination) + CERTIFICATES_FOLDER;
  }

  private String certificateTextsDir(Termination termination) {
    return rootDir(termination) + CERTIFICATE_TEXT_FOLDER;
  }

  private void writeCertificateFile(Termination termination, CertificateEntity certificate) {
    final var file = Paths.get(
        certificatesDir(termination) + "/" + certificateFileName(certificate)
    );

    try {
      Files.write(file, Collections.singleton(certificate.getXml()), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Could not write file: %s", file), e);
    }
  }

  private String certificateFileName(CertificateEntity certificate) {
    return certificate.getCertificateId() + revokedFileName(certificate) + XML_EXTENSION;
  }

  private String revokedFileName(CertificateEntity certificate) {
    return certificate.isRevoked() ? REVOKED_FILENAME : "";
  }

  private void writeCertificateTextFile(Termination termination,
      CertificateTextEntity certificateTextEntity) {
    final var file = Paths.get(
        certificateTextsDir(termination) + "/" + certificateTextFileName(certificateTextEntity)
    );

    try {
      final var xml = new String(Base64.getDecoder().decode(certificateTextEntity.getXml()));
      Files.write(file, Collections.singleton(xml), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Could not write file: %s", file), e);
    }
  }

  private String certificateTextFileName(CertificateTextEntity certificateTextEntity) {
    return certificateTextEntity.getCertificateType() + "-"
        + certificateTextEntity.getCertificateTypeVersion() + XML_EXTENSION;
  }

  private ZipFile writeZipFile(Termination termination, Password password) {
    final ZipFile zipFile = createZipFile(termination, password);
    try {
      zipFile.addFolder(new File(rootDir(termination)), zipParameters());
    } catch (ZipException e) {
      throw new RuntimeException("Could not create zip file", e);
    }
    return zipFile;
  }

  private ZipFile createZipFile(Termination termination, Password password) {
    return new ZipFile(
        termination.careProvider().hsaId().id() + ZIP_EXTENSION,
        password.password().toCharArray()
    );
  }

  private ZipParameters zipParameters() {
    final var zipParameters = new ZipParameters();
    zipParameters.setEncryptFiles(true);
    zipParameters.setCompressionLevel(CompressionLevel.HIGHER);
    zipParameters.setEncryptionMethod(EncryptionMethod.AES);
    zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
    return zipParameters;
  }

  private void removeDirIfExists(String directory) {
    final var directoryPath = Path.of(directory);
    if (Files.notExists(directoryPath)) {
      return;
    }

    try {
      Files.walk(directoryPath)
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .forEach(File::delete);
    } catch (IOException e) {
      throw new RuntimeException(String.format("Could not remove directory: %s", directoryPath), e);
    }
  }
}
