# Java Pub-Sub based Message-oriented Middleware
---
This is my attempt at fundamentally understanding the design of Publisher-Subscriber model and Message-Oriented Middlewares. 
This implementation utilized asynchronous message passing via message queue. The Publisher and Subscriber objects are loosely coupled
and have no knowledge about the existence of one another. 

To keep things simple, the objects that I used are Publisher, Subscriber, Topic, MessageBroker, and Message. 
I read wiki on [Publishe-Subscribe pattern](https://en.wikipedia.org/wiki/Publish–subscribe_pattern) to help me implement this idea.

  