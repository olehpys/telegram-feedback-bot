# telegram-feedback-bot
[![telegram-feedback-bot](https://github.com/pisarenko/telegram-feedback-bot/actions/workflows/any_branch.yml/badge.svg)](https://github.com/pisarenko/telegram-feedback-bot/actions/workflows/any_branch.yml)

This bot allows to send feedback messages to the channel owner and reply to them.

## AWS Lambda deploy steps:

1. Create Lambda function on AWS
2. Edit runtime settings
   1. Set up handler to `com.pysarenko.feedbackbot.EventHandler::handleRequest`
3. Set up AWS Lambda environment variables from `model/Environment.java` class
4. Create AWS Lambda **public** function URL
   1. [Set up Telegram Webhook URL for bot](#set-up-telegram-webhook-url)
5. Upload .jar file to AWS Lambda

## Build Lambda code
````
mvn clean package
````

## Set up Telegram Webhook URL
````
https://api.telegram.org/bot{bot-key}/setWebhook?url={webhook-url}
````

## GitFlow release procedure
````
mvn -B gitflow:release-start gitflow:release-finish
````