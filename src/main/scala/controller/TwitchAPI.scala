package controller

import akka.actor.{Actor, ActorRef}
import com.github.andyglow.websocket.{WebsocketClient, WebsocketHandler}
import com.github.andyglow.websocket.util.Uri
import model._
import model.command._

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
  var IsPriesthere:Boolean = false
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
            checkForBotCommands(message,this)
          }else{

          }
      }
    }

    val client = WebsocketClient[String](Uri("wss://irc-ws.chat.twitch.tv:443"), handler)
    val socket = client.open()

    val twitchOauthToken = sys.env("TWITCH_OAUTH")

/*
    Extra twitch stuff
    socket ! "CAP REQ :twitch.tv/membership" https://dev.twitch.tv/docs/irc/membership
    socket ! "CAP REQ :twitch.tv/commands" https://dev.twitch.tv/docs/irc/commands
    socket ! "CAP REQ :twitch.tv/tags" https://dev.twitch.tv/docs/irc/tags
*/

    socket ! "PASS " + twitchOauthToken
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

  def checkForBotCommands(chatMessage: ChatMessage,web:WebsocketHandler[String]): Unit = {
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
    }else if(messageText.toLowerCase().startsWith("!h")){
      val message:String = new Helpcommand(chatMessage.username).outputmessage
      web.sender() ! message
    }else if(messageText.startsWith("!")){ //this should always be checked last
      val message:NoCommandmatched = new NoCommandmatched
      web.sender() ! message.outputmessage
      //This might have problem with y2nk's pull request.
    }
  }

}
