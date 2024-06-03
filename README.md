# Hourglass

Hourglass is a Spring Boot-based backend application that allows users to create routines and reminds them to perform these routines at user-defined intervals. The backend uses PostgreSQL for data storage.

## Table of Contents

- [Features](#features)
- [User Stories](#user-stories)
- [Requirements](#requirements)
- [Useful commands](#useful-commands)
- [License](#license)

## Features

- User authentication and authorization
- Create, read, update, and delete routines
- Set reminders for routines at user-defined intervals
- Send notifications to remind users of their routines

## User Stories

- **User Story 1**: As a user, I want to add my routines to receive notifications at my specified time.
- **User Story 2**: As a user, I want to update or delete my routines to adjust them to my changing daily schedule.
- **User Story 3**: As a user, I want to add one-time tasks so that I don't need to use a separate application.
- **User Story 4**: As a user, I want to log in to access my routines across different devices.
- **User Story 5**: As a user, I want to mark completed routines to have a sense of satisfaction and not have to remember what is done and what still needs to be done.
- **User Story 6**: As a user, I want to view statistics of my routines to know which ones I'm doing well with and which ones I need to work on.
- **User Story 7**: As a user, I want to access account settings to edit my data.
- **User Story 8**: As a user, I want to explore the app's features before creating an account to see if it meets my needs.
- **User Story 9**: As a user, I want to create an account using my Google account to save time and avoid remembering another password.
- **User Story 10**: As a user, I want access to help resources to learn how to use the app.
- **User Story 11**: As a user, I want to report bugs and suggest improvements to help enhance the app.
- **User Story 12**: As a user, I want to delete my account to remove my data from this service.
- **User Story 13**: As a user, I want to search my routines to quickly find what I am looking for.

## Requirements

- Java 17
- Maven
- PostgreSQL

## Installation

1. Set up PostgreSQL:

```shell
docker-compose up -d
```

## Useful commands

Docker Compose command `docker-compose ps` lists all the running containers managed by Docker Compose along with their status information, such as container ID, name, command, creation time, and current status.

## License

All rights reserved.

This software is the proprietary information of Marcin Farowski.
You may use this software solely for your own personal, non-commercial use.
You may not modify, copy, distribute, transmit, display, perform, reproduce, publish, license, create derivative works from, transfer, or sell any information, software, products, or services obtained from this software without explicit written permission from the owner.

For permissions or inquiries, please contact: [m.farowski@gmail.com](mailto:m.farowski@gmail.com).