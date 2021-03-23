package controller

import akka.actor.{Actor, ActorRef}
import com.github.andyglow.websocket.{Websocket, WebsocketClient, WebsocketHandler}
import com.github.andyglow.websocket.util.Uri
import config.TwitchConfiguration
import controller.TwitchCommands.{NewQuestionCommand, RemoveQuestionCommand, TwitchCommandContract, UpvoteQuestionCommand}
//import model._

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
  /* properties */
  var isPriestHere:Boolean = false
  var loadedCommandList: List[TwitchCommandContract] = List(
    new NewQuestionCommand(this),
    new RemoveQuestionCommand(this),
    new UpvoteQuestionCommand(this)
  )

  /* prepare to establish ws connection with Twitch */
  val handler: WebsocketHandler[String] = new WebsocketHandler[String] {
    def receive = {
      case rawMessage: String =>
        println(rawMessage)
        if (rawMessage.startsWith("PING")) {
          sender() ! "PONG :tmi.twitch.tv"
        } else if (rawMessage.contains("PRIVMSG")) {
          val message = TwitchAPI.parseChatMessage(rawMessage)
          checkForBotCommands(message)
        }else{

        }
    }
  }
  /* establish ws connection with Twitch */
  val client: WebsocketClient[String] = WebsocketClient[String](Uri("wss://irc-ws.chat.twitch.tv:443"), handler)
  val socket: Websocket = client.open()

  /* prepare to do authentication for bot to login */
  val twitchOauthToken: String = sys.env("TWITCH_OAUTH")

  socket ! "PASS " + twitchOauthToken
  socket ! "NICK " + sys.env("TWITCH_BOT_NICKNAME")
  socket ! "JOIN #" + sys.env("TWITCH_CHANNEL_NAME")
  /*
      Extra twitch stuff
      socket ! "CAP REQ :twitch.tv/membership" https://dev.twitch.tv/docs/irc/membership
      socket ! "CAP REQ :twitch.tv/commands" https://dev.twitch.tv/docs/irc/commands
      socket ! "CAP REQ :twitch.tv/tags" https://dev.twitch.tv/docs/irc/tags
  */

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

  def sendRawMessageInChat(rawMessage: String): Unit = {
    socket ! (TwitchConfiguration.senderPrefix + rawMessage)
  }
}
