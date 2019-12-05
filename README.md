# Kotlin Discord Bot
This Discord bot is based on how Roboragi detects content searches. 
It uses AniList to search for media, characters, staffs,  and studios.

## Getting Started (without Docker)
1. Set DISCORD_KEY=<your_key_here> as a enviroment variable
2. Build project `gradle build` or `./gradlew build`
3. Run project `gradle run`

## Docker
1. Build project `gradle build` or `./gradlew build`
2. Build image `docker build -t kotlin-anilist-bot .`
3. Run image `docker run --env DISCORD_KEY=<your_key_here> kotlin-anilist-bot`

## Behavior
When a message is sent the bot looks if the message starts with (<, {) and ends with (>, }).
If the message does it will first set if it is a ANIME or MANGA query, then it will remove (<, {) and (>, }) from the message.
After that it will start the search by making a post call to AniList:s GraphQL API. 
When it receive the response it is parsed and then a message will be created to reply to the user.

## Commands
| Name      | Syntax            | Description                   | Example           |
| :---      | :---------------: | :---------------------------: | :---              |
| Anime     | {*text here*}     | Used to search for media      | {*one piece*}     |
| Manga     | <*text here*>     |                               | <*one piece*>     |
| Character | (*text here*)     | Used to search for characters | (luffy)           |
| Staff     | \[*text here*\]   | Used to search for staff      | (eiichiro oda)    |
| Studio    | >*text here*<     | Used to search for studios    | >toei animation<  |

## TODO
- [x] Add Media search
- [x] Add Staff search
- [X] Add Character search
- [x] Add Studio search
- [X] Use Design Pattern
- [X] Docker