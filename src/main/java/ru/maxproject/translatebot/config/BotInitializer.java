package ru.maxproject.translatebot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.maxproject.translatebot.service.TelegramBot;

@Component
public class BotInitializer {

    @Autowired
    public TelegramBot bot;


    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotApi.registerBot(bot);
        } catch (TelegramApiException e) {

        }
    }

}
