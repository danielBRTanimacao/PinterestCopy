package pinstack_api.utils.components;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailSender {
        
    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String email, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("noreply@baeldung.com");
        message.setTo(email); 
        message.setSubject("Verify your email"); 
        message.setText("Copie esse codigo e cole: " + verificationCode);
        mailSender.send(message);
    }
}
