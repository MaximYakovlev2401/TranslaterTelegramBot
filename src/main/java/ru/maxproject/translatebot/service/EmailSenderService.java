package ru.maxproject.translatebot.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import ru.maxproject.translatebot.model.Mail;

import java.nio.charset.StandardCharsets;

@Service
public class EmailSenderService {

    private JavaMailSender emailSender;
    private SpringTemplateEngine templateEngine;

    public void sendEmail(Mail mail, String template) {
        emailSender.send(prepareMessage(mail, template));
    }

    private MimeMessage prepareMessage(Mail mail, String template) {
        var message = emailSender.createMimeMessage();

        try {
            var helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            var image = new ClassPathResource("static/welcome.png");
            var context = new Context();
            context.setVariables(mail.getVariables());
            var html = templateEngine.process(template, context);
            helper.setTo(mail.getTo());
            helper.setFrom(mail.getFrom());
            helper.setSubject(mail.getSubject());
            helper.setText(html, true);
            helper.addInline("welcome", image);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }
}
