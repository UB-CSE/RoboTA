FROM ubuntu:18.04


RUN apt-get update
RUN apt-get install -y wget

#Java Setup
RUN apt-get update --fix-missing
RUN apt-get install -y default-jdk

##Java 15 Setup
#RUN apt-get update --fix-missing
#RUN apt update
#RUN apt-get install -y software-properties-common
#RUN add-apt-repository ppa:linuxuprising/java
#RUN apt update
#RUN echo "oracle-java15-installer shared/accepted-oracle-license-v1-2 select true" | debconf-set-selections
#RUN echo "oracle-java15-installer shared/accepted-oracle-license-v1-2 seen true" | debconf-set-selections
#RUN apt install -y --force-yes oracle-java15-installer


#Utility setup
RUN apt-get install -y unzip


#Scala setup
RUN apt-get remove scala-library scala
RUN wget http://scala-lang.org/files/archive/scala-2.13.4.deb
RUN dpkg -i scala-2.13.4.deb
RUN apt-get update
RUN apt-get install -y scala


RUN apt-get install -y software-properties-common
RUN add-apt-repository ppa:linuxuprising/java

RUN echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
RUN apt-get update
RUN apt-get install -y sbt

#Maven Setup
RUN apt-get install -y maven

# Build the app
ENV HOME /root
WORKDIR /root
COPY . .
RUN mvn package

# Download the wait command to wait for MySQL to run
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.2.1/wait /wait
RUN chmod +x /wait

# Run the app
CMD /wait && java -jar target/twitchbot-0.0.1.jar