package se.inera.intyg.css.testability.configuration;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class MailServerConfig implements InitializingBean, DisposableBean {

  private static final Logger LOG = LoggerFactory.getLogger(MailServerConfig.class);

  private GreenMail greenMail;

  @Override
  public void afterPropertiesSet() {
    LOG.info("Starting development mail server");
    greenMail = new GreenMail(ServerSetupTest.SMTP);
    registerUser();
    greenMail.start();
  }

  @Override
  public void destroy() {
    greenMail.stop();
  }

  public void restartGreenMail() {
    LOG.info("Restarting development mail server");
    greenMail.reset();
    registerUser();
  }

  private void registerUser() {
    greenMail.setUser("email@address.se", "emailUser", "emailPassword");
  }
}
