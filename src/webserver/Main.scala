package webserver

import akka.actor.{ActorSystem, Props, typed}
import akka.actor.typed.scaladsl.adapter.ClassicActorSystemOps
import controller.TwitchAPI
import model.{MySQLDatabase, TestDatabase, TwitchBot}


object Main {

  def main(args: Array[String]): Unit = {
    val system: ActorSystem = ActorSystem("Web_System")

    // Use to comply with the new akka actor API which is required by akka.http
    implicit val shittySystem: typed.ActorSystem[Nothing] = system.toTyped

    val webSocketServer = system.actorOf(Props(classOf[WebSocketServer]))
    val twitchBotActor = system.actorOf(Props(classOf[TwitchBot], webSocketServer, new TestDatabase))
    val twitchAPIActor = system.actorOf(Props(classOf[TwitchAPI], twitchBotActor))

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
