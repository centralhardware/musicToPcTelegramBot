package ru.centralhardware.musicDownloaderBot;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import lombok.extern.log4j.Log4j;

import java.io.File;

@Log4j
public class Youtube {

    private final Bot bot;
    private final String directory ;

    /**
     * make music folder if not exist
     * @param bot
     */
    public Youtube(Bot bot) {
        this.bot = bot;
        if (Config.isTesting){
            directory = "music";
        } else {
            directory = "/music";
        }
        var dir = new File(directory);
        if (!(dir.exists() && dir.isDirectory())){
            dir.mkdir();
            log.info("create music directory");
        }
    }

    /**
     * download mp3 file to host
     * @param url link to video
     * @param messageId
     * @param chatId
     */
    public void download(String url, Integer messageId, long chatId){
        YoutubeDLRequest request = new YoutubeDLRequest(url, directory);
        request.setOption("ignore-errors");
//        request.setOption("output", "%(id)s");
        request.setOption("retries", 10);
        request.setOption("extract-audio");
        request.setOption("audio-format", "mp3");
        try {
            YoutubeDL.execute(request);
            bot.sendReplyMessage("file download successfully", messageId, chatId);
            log.info("finish processing " + url);
        } catch (YoutubeDLException e) {
            log.warn("download music fail", e);
            bot.sendReplyMessage(e.getMessage() , messageId, chatId);
        }
    }
}
