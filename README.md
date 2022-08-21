# telegram-feedback-bot

## Build lambda 
````
mvn clean package
````

## Release procedure
````
mvn -B gitflow:release-start gitflow:release-finish
````

## Set up telegram webhook url
````
https://api.telegram.org/bot{bot-key}/setWebhook?url={webhook-url}
````