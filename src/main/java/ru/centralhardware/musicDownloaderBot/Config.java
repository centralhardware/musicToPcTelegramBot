package ru.centralhardware.musicDownloaderBot;

import java.util.ResourceBundle;

public class Config {

    public static final ResourceBundle config = ResourceBundle.getBundle("config");

    public static final String username = config.getString("username");
    public static final String token = config.getString("token");
    public static final boolean isTesting = Boolean.parseBoolean(config.getString("isTesting"));

}
