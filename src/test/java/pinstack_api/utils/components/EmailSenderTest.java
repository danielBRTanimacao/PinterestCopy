package pinstack_api.utils.components;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSenderTest {

    @Mock
    private JavaMailSender mailSender;
    @Mock
    private TemplateEngine templateEngine;
    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailSender emailSender;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(emailSender, "fromEmail", "noreply@pinstack.com");
    }

    @Test
    void sendVerificationEmail_success() throws MessagingException {
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("<html>hT146X</html>");
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        assertDoesNotThrow(() -> emailSender.sendVerificationEmail("user@email.com", "hT146X"));

        verify(templateEngine).process(eq("verification-email"), any(Context.class));
        verify(mailSender).createMimeMessage();
        verify(mailSender).send(mimeMessage);
    }

    @Test
    void sendVerificationEmail_setsCorrectVariableInContext() {
        when(templateEngine.process(anyString(), any(Context.class))).thenAnswer(invocation -> {
            Context ctx = invocation.getArgument(1);
            assertEquals("hT146X", ctx.getVariable("verificationCode"));
            return "<html>hT146X</html>";
        });
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailSender.sendVerificationEmail("user@email.com", "hT146X");

        verify(templateEngine).process(eq("verification-email"), any(Context.class));
    }

    @Test
    void sendVerificationEmail_throwsWhenMailFails() {
        when(templateEngine.process(anyString(), any(Context.class))).thenReturn("<html></html>");
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(new MailSendException("SMTP error")).when(mailSender).send(any(MimeMessage.class));

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> emailSender.sendVerificationEmail("user@email.com", "hT146X")
        );

        assertEquals("Failed to send verification email", ex.getMessage());
        verify(mailSender).send(mimeMessage);
    }

    @Test
    void sendVerificationEmail_throwsWhenTemplateProcessingFails() {
        when(templateEngine.process(anyString(), any(Context.class)))
            .thenThrow(new RuntimeException("Template not found"));

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> emailSender.sendVerificationEmail("user@email.com", "hT146X")
        );

        assertEquals("Failed to send verification email", ex.getMessage());
        verify(mailSender, never()).send(any(MimeMessage.class));
    }
}