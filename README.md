# Inside Challenge

## Requirements

- Docker
- cURL

## Getting Started

Run:

```shell
docker-compose up
```

Create account:

```shell
curl --location --request POST 'http://localhost:8090/accounts' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "vova",
    "password": "hello"
}'
```

Generate auth token:

```shell
curl --location --request POST 'http://localhost:8090/token' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "vova",
    "password": "hello"
}'
```

Create message:

```shell
curl --location --request POST 'http://localhost:8090/messages' \
--header 'Authorization: Bearer_[REPLACE_THIS_WITH_YOUR_TOKEN_FROM_PREVIOUS_STEP]' \
--header 'Content-Type: application/json' \
--data-raw '{
    "message": "Hello World!"
}'
```

Fetch 10 messages:

```shell
curl --location --request POST 'http://localhost:8090/messages' \
--header 'Authorization: Bearer_[REPLACE_THIS_WITH_YOUR_TOKEN_FROM_PREVIOUS_STEP]' \
--header 'Content-Type: application/json' \
--data-raw '{
    "message": "history 10"
}'
```

## Development

Requirements:

- JDK 17
- Gradle

Start development services:

```shell
docker-compose -f ./docker-compose.dev.yml up
```

Run with Gradle:

```shell
./gradlew bootRun
```

Build Docker image (optional):

```shell
docker build -t vovafomin/inside-challenge .
```