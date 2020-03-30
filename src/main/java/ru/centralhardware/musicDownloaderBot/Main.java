package ru.centralhardware.musicDownloaderBot;

import lombok.extern.log4j.Log4j;
import org.telegram.telegrambots.ApiContextInitializer;

@Log4j
public class Main {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        log.info("api context initialized");
        Bot.run();
        log.info("bot run");
    }

}
