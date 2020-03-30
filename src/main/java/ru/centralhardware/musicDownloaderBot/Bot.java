package ru.centralhardware.musicDownloaderBot;

import lombok.extern.log4j.Log4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * telegram bot class
 * @author centralhardware
 */
@Log4j
public class Bot extends TelegramLongPollingBot {

    private final Youtube youtube;



    /**
     * run bot
     */
    public static void run(){
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            if (Config.isUseProxy){
                log.info("use proxy socks5://"+Config.proxyHost+":"+Config.proxyPort);
                DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
                botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
                botOptions.setProxyPort(Config.proxyPort);
                botOptions.setProxyHost(Config.proxyHost);
                telegramBotsApi.registerBot(new Bot(botOptions));
            } else {
                telegramBotsApi.registerBot(new Bot());
            }
            log.info("bot register");
        } catch (TelegramApiRequestException e) {
            log.fatal("bot register fail", e);
        }
    }

    /**
     * init youtube-dl
     */
    public Bot() {
        this.youtube = new Youtube(this);
    }

    public Bot(DefaultBotOptions defaultBotOptions){
        super(defaultBotOptions);
        this.youtube = new Youtube(this);
    }

    /**
     * processing receiving message
     * @param update incoming update
     */
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            if (Url.validate(update.getMessage().getText())){
                Thread thread = new Thread(() -> {
                    log.info("start processing " + update.getMessage().getText());
                    sendReplyMessage("начато скачивание", update.getMessage().getMessageId(), update.getMessage().getChatId());
                    youtube.download(Url.trim(update.getMessage().getText()), update.getMessage().getMessageId(), update.getMessage().getChatId());
                });
                thread.start();
            }
        }
    }

    /**
     * send a reply to giving message
     * @param message message text
     * @param messageId  message id for reply
     * @param chatId  chat id
     */
    public synchronized void sendReplyMessage(String message, Integer messageId, long chatId){
        SendMessage sendMessage = new SendMessage().setText(message).setReplyToMessageId(messageId).setChatId(chatId);
        try{
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.warn("fail to send message", e);
        }
    }


    /**
     * @return bot username
     */
    public String getBotUsername() {
        return Config.username;
    }

    /**
     * @return bot token
     */
    public String getBotToken() {
        return Config.token;
    }
}
