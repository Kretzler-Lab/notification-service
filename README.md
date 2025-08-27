# Notification Service

The email notification service for MiKTMC applications. 

## Environment Vars
The Notification Services depends on the following environment variables:
* ENV_SMTP_USERNAME=
* ENV_SMTP_PASSWORD=
* ENV_SMTP_RECIPIENT=[a comma-separated list of email addresses]  
* ENV_SMTP_HOST=
* ENV_SMTP_SERVER=

## Email Routing
1. Email addresses with "neptune" will get notifications with the word "Neptune" in the body
2. Email addresses with "curegn" will get notifications with the word "CureGN" in the body
3. Email addresses with "miktmc-devs" will get everything
