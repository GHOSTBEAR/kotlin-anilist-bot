# Kotlin Discord Bot
This Discord bot is based on how Roboragi detects content searches. 
The bot always look for { } and < > when it does detect either of those it makes an query to AniList.
After getting the response back from AniList it makes a message in the guild where the comment was created. 

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