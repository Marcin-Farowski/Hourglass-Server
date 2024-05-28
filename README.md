# Hourglass

Hourglass is a Spring Boot-based backend application that allows users to create routines and reminds them to perform these routines at user-defined intervals. The backend uses PostgreSQL for data storage.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Useful commands](#useful-commands)
- [License](#license)

## Features

- User authentication and authorization
- Create, read, update, and delete routines
- Set reminders for routines at user-defined intervals
- Send notifications to remind users of their routines

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