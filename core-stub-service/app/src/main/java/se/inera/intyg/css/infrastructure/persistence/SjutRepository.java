package se.inera.intyg.css.infrastructure.persistence;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class SjutRepository {

  private final Map<String, FileUpload> uploadedFiles = new HashMap<>();

  public void store(FileUpload fileUpload) {
    uploadedFiles.put(fileUpload.packageMetadata().organizationNumber(), fileUpload);
  }

  public byte[] get(String organizationNumber) {
    if (uploadedFiles.containsKey(organizationNumber)) {
      return uploadedFiles.get(organizationNumber).file();
    }
    return new byte[0];
  }

  public void removeAll() {
    uploadedFiles.clear();
  }
}
