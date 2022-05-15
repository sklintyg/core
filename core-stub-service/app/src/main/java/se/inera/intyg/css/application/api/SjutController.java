package se.inera.intyg.css.application.api;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import se.inera.intyg.css.application.dto.PackageMetadata;
import se.inera.intyg.css.application.service.SjutService;

@RestController
@RequestMapping("/api/v1")
public class SjutController {

  private final static Logger LOG = LoggerFactory.getLogger(SjutController.class);

  private final SjutService sjutService;

  public SjutController(SjutService sjutService) {
    this.sjutService = sjutService;
  }

  @PostMapping("/upload")
  public void upload(
      @RequestPart("metadata") PackageMetadata packageMetadata,
      @RequestPart("file") MultipartFile packageFile) throws IOException {
    LOG.info(String.format("Upload package with metadata '%s' and size '%s'.",
        packageMetadata, packageFile.getSize()));
    sjutService.upload(packageMetadata, packageFile.getBytes());
  }
}
