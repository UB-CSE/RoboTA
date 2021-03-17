package webserver

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import model.MySQLDatabase

object Main {

  def main(args: Array[String]): Unit = {
//    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "Web_System")

//    new WebServer()
    println("started")
    val x = new MySQLDatabase()
    println("created DB")
    for(i <- 1 to 10000){
      x.test()
      System.out.flush()
    }
    println("ending")
  }

}
