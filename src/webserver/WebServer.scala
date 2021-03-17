package webserver

import akka.actor.ActorRef
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

import scala.concurrent.{ExecutionContext, Future}

class WebServer(implicit val system: ActorSystem[Nothing]) {

  val executionContext: ExecutionContext = system.executionContext

  val indexRouter: Route  = path("") {
    getFromFile("public/index.html")
  }

  val staticRouter: Route = pathPrefix("") {
      getFromDirectory("public")
  }


  val router: Route = concat(indexRouter, staticRouter)

  val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt("0.0.0.0", 8081).bind(router)

  //    bindingFuture.flatMap(_.unbind())(executionContext).onComplete(_ -> system.terminate())(executionContext)
}
