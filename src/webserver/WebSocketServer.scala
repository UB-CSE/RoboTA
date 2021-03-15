package webserver

import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.{Configuration, SocketIOClient, SocketIOServer}

class WebSocketServer {

  val config: Configuration = new Configuration {
    setHostname("0.0.0.0")
    setPort(8080)
  }

  val server: SocketIOServer = new SocketIOServer(config)

  server.addConnectListener(new ConnectionListener())

  server.start()
}

object WebSocketServer{

  def main(args: Array[String]): Unit = {
    new WebSocketServer()
  }
}

class ConnectionListener() extends ConnectListener {
  override def onConnect(client: SocketIOClient): Unit = {
    println("Connected: " + client)
  }
}

