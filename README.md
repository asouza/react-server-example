# Example of a client/server using ProjectReactor

This project is just an example about how to use the new abstractions that will be available to us in the next big
version of Spring. The Reactor API will have a very important role and we need to understand how it can help us
to scale out our projects. 

The **CompleteReactiveTransactionDao** is a simple DAO that uses Flux and Mono to let the query execution as lazy as possible.
I am still learning the API and you are welcome to submit any pull request :).

#Dump database

There is a dump that you can use to load data into the database
