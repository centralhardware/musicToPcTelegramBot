### music to pc telegram bot

this is simple bot for downloading audio track from youtube and etc (you can download from any place that supports youtube-dl)

## WARNING

youtube-dl repo now is deleted from github and I'm not sure if the project will work in the future as it downloads youtube-dl from https://yt-dl.org/

## before 

(create new bot)[https://core.telegram.org/bots#creating-a-new-bot]

## how to 

create and fil musicToPcTelegramBot/src/main/resources/config.properties (see example in config.properties.example) 

change mapping path in docker-compose.yml (by default ./music)

```bash
sudo docker-composy build
sudo docker-compose up -t
```

## usege 

send bot link to youtube (if you send link to playlist only the current video will be downloaded, not the entire playlist)
wait for finish message

be carefull number of proccessing videos is unlimited
