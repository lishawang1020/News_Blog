package NowNewsBlog.service;

import NowNewsBlog.exceptions.NowNewsBlogException;
import NowNewsBlog.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

  private final JavaMailSender mailSender;
  private final MailContentBuilder mailContentBuilder;

  @Async
  public void sendMail(NotificationEmail notificationEmail) {
    MimeMessagePreparator messagePreparator = mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
      messageHelper.setFrom("nownewsblog@gmail.com");
      messageHelper.setTo(notificationEmail.getRecipient());
      messageHelper.setSubject(notificationEmail.getSubject());
      messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
    };
    try {
      mailSender.send(messagePreparator);
      log.info("Account activation email sent to user");
    } catch (MailException e) {
      log.error("Exception occurred when sending mail", e);
      throw new NowNewsBlogException("Activation email can't be sent due to Exception" + notificationEmail.getRecipient(), e);
    }
  }
}
