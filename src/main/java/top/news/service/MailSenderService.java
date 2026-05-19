package top.news.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import top.news.dto.auth.MailMessageDTO;

import java.util.Random;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationCode(MailMessageDTO dto, Integer profileId) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(dto.getToAccount());
            helper.setSubject(dto.getSubject());
            helper.setText(dto.getBody(), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Email yuborishda xatolik: " + e.getMessage());
        }
    }

    public void verificationCode(String toAccount, Integer profileId) {
        MailMessageDTO mailMessage = new MailMessageDTO();
        mailMessage.setToAccount(toAccount);
        mailMessage.setSubject("Top News - Tasdiqlash kodi");

        String body = "<div style=\"font-family: Arial; max-width: 500px; margin: auto; padding: 20px; border: 1px solid #eee;\">" +
                "<h2 style=\"color: #2196F3; text-align: center;\">TOP NEWS</h2>" +
                "<p>Assalomu alaykum,</p>" +
                "<p>Sizning tasdiqlash kodingiz:</p>" +
                "<div style=\"font-size: 28px; font-weight: bold; text-align: center; padding: 20px; background: #f4f4f4; border-radius: 10px;\">" +
                generateCode() + "</div>" +
                "<p style=\"color: #666;\">Iltimos, ushbu kodni hech kimga bermang.</p>" +
                "</div>";
        mailMessage.setBody(body);
        sendVerificationCode(mailMessage, profileId);

    }

    private String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

}