package webserver

import akka.actor.{Actor, ActorRef}
import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.{Configuration, SocketIOClient, SocketIOServer}
import model.TwitchBotDatabase.TwitchBotContract
import model.{GetQuestions, Question, QuestionListFunctions}

class WebSocketServer() extends Actor{

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

  server.addConnectListener(new ConnectionListener(this))

  server.start()

  var cachedMessage: String = ""

  override def receive: Receive = {
    case questions: List[Question] =>
      cachedMessage = questions.foldLeft("")((agg: String, question: Question) => agg + "<hr/>" + question.toString)
      server.getBroadcastOperations.sendEvent(
        "messages",
        cachedMessage
      )

  }
}

class ConnectionListener(webSocketServer: WebSocketServer) extends ConnectListener {
  override def onConnect(client: SocketIOClient): Unit = {

    println("Connected: " + client)

    client.sendEvent(
      "messages",
      webSocketServer.cachedMessage
    )
  }
}

