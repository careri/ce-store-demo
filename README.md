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

## Dependency Injection

I'm using the import of differrent configurations to build the full application config.

**DONE, but untested**

## Persistance

Running the code outside of docker will use the H2 inmemory storage.
Once I have the docker compose in place a MongoDb will be used as backend.

**DONE, but untested**

## Swagger

I intend to have swagger exposed for testing the endpoints.

**TODO**

## CQRS

A naive implementation of the CQRS pattern:
https://martinfowler.com/bliki/CQRS.html

**Partial DONE, wrote the complex reflection part**
**TODO, populate using Dependency Injection**

### Query

You post a query in an async fashion and will finaly receive a result. This should be a read operiton.

### Command

You post a command, a result is optional. If you don't want a result you'll just return the NoValue singleton.
A command is a write operation of some sort.

## EventBus

I intend to have an event bus backed by RabbitMQ. The microservices will generate events withing the transaction writing to the storage.
The events will first be stored in the persistent layer as en entity, this is the Outbox pattern.

A worker thread will consume the inbox and publish to RabbitMQ.

**TODO**



