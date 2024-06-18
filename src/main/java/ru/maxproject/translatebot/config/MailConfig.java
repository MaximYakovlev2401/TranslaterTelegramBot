package ru.maxproject.translatebot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender javaMailSender (ApplicationProperties properties) {
        var  mailSender = new JavaMailSenderImpl();
        mailSender.setHost(properties.getHost());
        mailSender.setPort(Integer.parseInt(properties.getPort()));
        mailSender.setUsername(properties.getUsername());
        mailSender.setPassword(properties.getSenderEmailPassword());
        var props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol","smtp" );
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }
}
