package webserver

import akka.actor.{ActorSystem, Props, typed}
import akka.actor.typed.scaladsl.adapter.ClassicActorSystemOps
import controller.TwitchAPI
import model.TwitchBotDatabase.{MySQLDatabase, TestDatabase}
import model.TwitchBot
import helpers.dotenv.Dotenv
import akka.actor.ActorRef


object Main {
  var webSocketServer: ActorRef = null
  var twitchBotActor: ActorRef = null
  var twitchAPIActor: ActorRef = null

  def main(args: Array[String]): Unit = {
//    Dotenv.loadEnv()

    val system: ActorSystem = ActorSystem("Web_System")

    // Use to comply with the new akka actor API which is required by akka.http
    implicit val shittySystem: typed.ActorSystem[Nothing] = system.toTyped

    val database = new TestDatabase

    webSocketServer = system.actorOf(Props(classOf[WebSocketServer], database))
    twitchBotActor = system.actorOf(Props(classOf[TwitchBot], webSocketServer, database))
    twitchAPIActor = system.actorOf(Props(classOf[TwitchAPI], twitchBotActor))

    new WebServer()


//    println("started")
//    val x = new MySQLDatabase()
//    println("created DB")
//    for(i <- 1 to 10000){
//      x.test()
//      System.out.flush()
//    }
//    println("ending")
  }

}
