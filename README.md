# TwitterAI

This software gathers data via Twitter4J API then trains a recurrent neural network.
After that it will use the model to tweet hourly a generated tweet.

## How to get started 

### preconditions
- Docker is installed and works. (google it)
- Twitter API credentials is generated and added to  `src/main/java/resources/twitter4j.properties`

### build
   
   - `./gradlew build`
   - `docker build --tag twitterai:1.0.0 .` 

### run
   - `docker un --detach --name myOwnTwitterAI twitterai:1.0.0`  
   
`docker ps` --> your container should be succesfully running.