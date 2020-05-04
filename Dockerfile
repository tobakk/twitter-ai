FROM tensorflow/tensorflow:latest

RUN apt-get update && \
    apt-get install -y openjdk-11-jdk && \
    apt-get clean;

RUN apt-get update && \
    apt-get install ca-certificates-java && \
    apt-get clean && \
    update-ca-certificates -f;

ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64/
RUN export JAVA_HOME

RUN useradd -ms /bin/bash  ai
USER ai
WORKDIR /home/ai/

COPY data/model.h5 /home/ai/data/model.h5
COPY build/libs/*.jar /home/ai/twitterAi.jar

CMD "java -jar /home/ai/twitterAi.jar"
