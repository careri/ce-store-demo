# ce-store-demo

A java Spring Boot demo

This is my first attempt at writing a Spring Boot application.

## Demo

The idea is to have stores (shops) that sell producs. The only type of product in this demo will be a book.
The book it self will not have a price, but you can get a price from the pricing micro service.

### Components

Each microservice will be a docker image.

* Store microservice, store logic
* Books microservice, the source of all available books. Not all books is a product.
* Pricing, will provide a price based on the shop and book provided. 

## IDE

Developed with VS Code and a .devcontainer for Java.

## Dependency Injection

I'm using the import of differrent configurations to build the full application config.

✔️ 

## Persistance

Running the code outside of docker will use the H2 inmemory storage.
Once I have the docker compose in place a MongoDb will be used as backend.

✔️ (Not tested with MongoDb)

## Swagger

I intend to have swagger exposed for testing the endpoints.
swagger-ui/index.html

✔️ 

## CQRS

A simple implementation of the CQRS pattern:
https://martinfowler.com/bliki/CQRS.html

### TODO

A more robust way of parsing the generic hierarcy of the handlers.

✔️

### Query

You post a query in an async fashion and will finaly receive a result. This should be a read operiton.

### Command

You post a command, a result is optional. If you don't want a result you'll just return the NoValue singleton.
A command is a write operation of some sort.

## EventBus

I intend to have an event bus backed by RabbitMQ. The microservices will generate events withing the transaction writing to the storage.
The events will first be stored in the persistent layer as en entity, this is the Outbox pattern.

A worker thread will consume the Outbox and publish to RabbitMQ. Look into the Spring Boot messaging.

### Core components

✔️

### Outbox pattern

:x:

## Docker

## Layered build

Defined in docker-compose-build.yaml

### Building

*docker compose -f docker-compose-build.yml build*

Builds the layers and copies only the needed output to the final app stage

### Run unit tests

*docker compose -f docker-compose-build.yml run tests*

### Running without dependecies (using in memory stuff)

*docker compose -f docker-compose-build.yml run app*

✔️ (can be improved)


### Docker Compose

All microservices and dependencies will be configured using docker compose.

:x:



