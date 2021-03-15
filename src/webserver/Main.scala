package webserver

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

object Main {

  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "Web_System")

    new WebServer()
  }

}
