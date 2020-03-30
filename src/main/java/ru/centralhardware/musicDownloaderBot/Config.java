package ru.centralhardware.musicDownloaderBot;

import java.util.ResourceBundle;

public class Config {

    public static final ResourceBundle config = ResourceBundle.getBundle("config");

    public static final String username = config.getString("username");
    public static final String token = config.getString("token");
    public static final boolean isTesting = Boolean.parseBoolean(config.getString("isTesting"));
    public static final boolean isUseProxy = Boolean.parseBoolean(config.getString("isUseProxy"));
    public static final String proxyHost = config.getString("proxyHost");
    public static final int proxyPort = Integer.parseInt(config.getString("proxyPort"));

}
