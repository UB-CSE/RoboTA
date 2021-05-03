package webserver

import akka.actor.{Actor, ActorRef}
import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.{Configuration, SocketIOClient, SocketIOServer}
import model.TwitchBotDatabase.TwitchBotContract
import model.{GetQuestions, Question, QuestionListFunctions}

class WebSocketServer(val database: TwitchBotContract) extends Actor{

  val config: Configuration = new Configuration {
    setHostname("0.0.0.0")

    println("WEBSOCKET_LISTEN", sys.env("WEBSOCKET_LISTEN"))

    if (sys.env("WEBSOCKET_LISTEN") != "" && sys.env("WEBSOCKET_LISTEN").toInt > 0) {
      setPort(sys.env("WEBSOCKET_LISTEN").toInt)
    } else {
      setPort(8082)
    }
  }

  val server: SocketIOServer = new SocketIOServer(config)

  server.addConnectListener(new ConnectionListener(database))

  server.start()

  override def receive: Receive = {
    case questions: List[Question] =>
      server.getBroadcastOperations.sendEvent(
        "messages",
        QuestionListFunctions.sortByUpvotes(questions).foldLeft("")((agg: String, question: Question) => agg + "<hr/>" + question.toString)
      )
  }
}

//object WebSocketServer{
//
//  def main(args: Array[String]): Unit = {
//    new WebSocketServer()
//  }
//}

class ConnectionListener(val database: TwitchBotContract) extends ConnectListener {
  override def onConnect(client: SocketIOClient): Unit = {
    val questions: List[Question] = database.unansweredQuestions().sortBy(_.numberOfUpvotes()).reverse

    println("Connected: " + client)

    client.sendEvent(
      "messages",
      questions.foldLeft("")((agg: String, question: Question) => agg + "<br/>" + question.toString)
    )
  }
}

