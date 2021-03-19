package controller

import akka.actor.{Actor, ActorRef}
import com.github.andyglow.websocket.{WebsocketClient, WebsocketHandler}
import com.github.andyglow.websocket.util.Uri
import model._

object TwitchAPI{

  def parseChatMessage(rawMessage: String): ChatMessage = {
    // Extract the username from index 1 until the first '!'
    val username: String = rawMessage.substring(1, rawMessage.indexOf('!'))

    // Extract everything after the second ':' assuming the first char in a ':'
    val messageText: String = rawMessage.substring(rawMessage.drop(1).indexOf(':') + 2)

    new ChatMessage(username, messageText)
  }
}

class TwitchAPI(twitchBot: ActorRef) extends Actor {

  setup()

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
          }else if (rawMessage.contains("USERSTATE")){
            val messageUsername:List[String] = rawMessage.split(";").toList
            var placeholder:String = ""
            val Recollecting= for (information <- messageUsername if information.contains("display-name")) placeholder = information.split("=")(1)
            if(placeholder.toLowerCase().contains("priest")){
              sender() !"PRIVMSG #hartloff :" + messageUsername + " has has arrived"

              //your welcome priest
              //This is a joke pull request but I will love to see priest reaction
              //I have no idea if this works or not it should work base on my understanding of how this bot is working as
            }

          }
      }
    }

    val client = WebsocketClient[String](Uri("wss://irc-ws.chat.twitch.tv:443"), handler)
    val socket = client.open()

    socket ! "PASS oauth:???"
    socket ! "NICK Botloff"
    socket ! "JOIN #hartloff"
  }

  override def receive: Receive = {
    case _ =>
  }



  def removeCommandFromMessage(message: String): String = {
    if (message.indexOf(' ') < (message.length - 1)) {
      message.trim().substring(message.indexOf(' ') + 1).trim()
    } else {
      ""
    }
  }

  def checkForBotCommands(chatMessage: ChatMessage): Unit = {
    val messageText: String = chatMessage.messageText.trim()

    if (messageText.toLowerCase().startsWith("!q ") || messageText.toLowerCase().startsWith("!question ")) {
      twitchBot ! NewQuestion("",
        removeCommandFromMessage(chatMessage.messageText),
        chatMessage.username)
    } else if (messageText.toLowerCase().startsWith("!u ")) {
      twitchBot ! Upvote(
        removeCommandFromMessage(chatMessage.messageText),
        chatMessage.username
      )
    } else if (messageText.toLowerCase().startsWith("!a ")) {
      twitchBot ! QuestionAnswered(removeCommandFromMessage(chatMessage.messageText))
    }else{
      // no commands
    }
  }

}
