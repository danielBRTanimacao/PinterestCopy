package pinstack_api.utils.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailSender {
    
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationEmail(String email, String verificationCode) {
        log.info("Sending verification email to {}", email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Verificando email");
        message.setText("Copie esse codigo e cole: " + verificationCode);
        try {
            mailSender.send(message);
            log.info("Verification email sent to {}", email);
        } catch (MailException e) {
            log.error("Failed to send verification email to {}", email, e);
            throw e;
        }
    }
}
