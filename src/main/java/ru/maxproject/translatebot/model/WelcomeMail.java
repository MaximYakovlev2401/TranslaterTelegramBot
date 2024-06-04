package ru.maxproject.translatebot.model;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor

public class WelcomeMail extends Mail{
    private final static String TEMPLATE_WELCOME = "welcome_mail.html";

    public WelcomeMail(String from, String to, String subject, Map<String, Object> variables) {
        super(from, to, subject, variables, WelcomeMail.TEMPLATE_WELCOME);
    }
}