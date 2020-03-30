package ru.centralhardware.musicDownloaderBot;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Url {

    /**
     * validate url
     * @param checkingUrl  incoming string from telegram
     * @return true if string is valid url
     */
    public static boolean validate(String checkingUrl){
        try {
            new URL(checkingUrl).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return true;
    }

    /**
     * delete youtube playlist from link
     * @param url
     * @return
     */
    public static String trim(String url){
        if (url.contains("&list")){
            return url.substring(0, url.indexOf("&"));
        } else
            return url;
    }

}
