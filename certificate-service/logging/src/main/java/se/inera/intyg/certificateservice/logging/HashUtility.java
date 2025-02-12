package se.inera.intyg.certificateservice.logging;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public final class HashUtility {

  @Value("${hash.salt}")
  private String salt;

  public static final String EMPTY = "EMPTY";
  private static final HashFunction hf = Hashing.sha256();

  public String hash(final String payload) {
    if (!StringUtils.hasText(payload)) {
      return EMPTY;
    }

    final var saltedPayload = salt + payload;
    final var digest = hf.hashString(saltedPayload, StandardCharsets.UTF_8).asBytes();
    return BaseEncoding.base16().lowerCase().encode(digest);
  }
}
