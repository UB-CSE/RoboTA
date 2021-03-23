package controller

import akka.actor.{Actor, ActorRef}
import com.github.andyglow.websocket.{WebsocketClient, WebsocketHandler}
import com.github.andyglow.websocket.util.Uri
import controller.TwitchCommands.{NewQuestionCommand, RemoveQuestionCommand, TwitchCommandContract, UpvoteQuestionCommand}
import model._

object TwitchAPI{
  def escapeHTML(input: String): String = {
    input.replace("&", "&amp;")
      .replace("<", "&lt;")
      .replace(">", "&gt;")
  }

  def parseChatMessage(rawMessage: String): ChatMessage = {
    // Extract the username from index 1 until the first '!'
    var username: String = rawMessage.substring(1, rawMessage.indexOf('!'))
    username = escapeHTML(username)

    // Extract everything after the second ':' assuming the first char in a ':'
    var messageText: String = rawMessage.substring(rawMessage.drop(1).indexOf(':') + 2)
    messageText = escapeHTML(messageText)

    new ChatMessage(username, messageText)
  }
}

class TwitchAPI(twitchBot: ActorRef) extends Actor {
  setup()

  var loadedCommandList: List[TwitchCommandContract] = List(
    new NewQuestionCommand(),
    new RemoveQuestionCommand(),
    new UpvoteQuestionCommand()
  )

  def setup(): Unit = {
    val handler = new WebsocketHandler[String] {
      def receive = {
        case rawMessage: String =>
          println(rawMessage)
          if (rawMessage.startsWith("PING")) {
            sender() ! "PONG :tmi.twitch.tv"
          } else if (rawMessage.contains("PRIVMSG")) {
            val message = TwitchAPI.parseChatMessage(rawMessage)
            checkForBotCommands(message)
            // sender() ! "PRIVMSG #hartloff :" + "Hello " + message.username + "! Thank you for saying \"" + message.messageText + "\""
          }else if(rawMessage.startsWith("!")){
            sender() ! "Sorry that is not a valid command. Please do !h or !help for more option"
          }else{
          //do noting?
          }
      }
    }

    val client = WebsocketClient[String](Uri("wss://irc-ws.chat.twitch.tv:443"), handler)
    val socket = client.open()

    val twitchOauthToken = sys.env("TWITCH_OAUTH")

    socket ! "PASS " + twitchOauthToken
    socket ! "NICK " + sys.env("TWITCH_BOT_NICKNAME")
    socket ! "JOIN #" + sys.env("TWITCH_CHANNEL_NAME")
  }

  override def receive: Receive = {
    case _ =>
  }


  def checkForBotCommands(chatMessage: ChatMessage): Unit = {
    for (command <- loadedCommandList) {
      if (command.matchCommand(chatMessage)) {
        command.executeCommand(chatMessage, twitchBot)
      }
    }
  }
}
