package webserver

import akka.actor.Actor
import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.{Configuration, SocketIOClient, SocketIOServer}
import model.Question

class WebSocketServer extends Actor{

  val config: Configuration = new Configuration {
    setHostname("0.0.0.0")

    if (sys.env("WEBSOCKET_LISTEN") != "" && sys.env("WEBSOCKET_LISTEN").toInt > 0) {
      setPort(sys.env("WEBSOCKET_LISTEN").toInt)
    } else {
      setPort(8082)
    }
  }

  val server: SocketIOServer = new SocketIOServer(config)

  server.addConnectListener(new ConnectionListener())

  server.start()

  override def receive: Receive = {
    case questions: List[Question] => server.getBroadcastOperations.sendEvent("messages", questions.foldLeft("")((agg: String, question: Question) => agg + "<br/>" + question.toString))
  }
}

//object WebSocketServer{
//
//  def main(args: Array[String]): Unit = {
//    new WebSocketServer()
//  }
//}

class ConnectionListener() extends ConnectListener {
  override def onConnect(client: SocketIOClient): Unit = {
    println("Connected: " + client)
  }
}

