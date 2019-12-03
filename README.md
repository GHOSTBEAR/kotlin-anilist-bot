# Kotlin Discord Bot
This Discord bot is based on how Roboragi detects content searches. 
It uses AniList to search for media, characters, staffs,  and studios.

## Getting Started

### Set Enviroment Variable
DISCORD_KEY=<YOUR_KEY>

### Gradle
To build the project use `gradle build` and to run it use `gradle run`.

## Behavior
When a message is sent the bot looks if the message starts with (<, {) and ends with (>, }).
If the message does it will first set if it is a ANIME or MANGA query, then it will remove (<, {) and (>, }) from the message.
After that it will start the search by making a post call to AniList:s GraphQL API. 
When it receive the response it is parsed and then a message will be created to reply to the user.

## TODO
- [x] Add Media search
- [X] Add Staff search
- [ ] Add Character search
- [ ] Add Studio search
- [X] Use Design Pattern
- [ ] Docker