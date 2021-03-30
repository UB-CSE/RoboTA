package controller.TwitchCommands

import akka.actor.ActorRef
import controller.{ChatMessage, TwitchAPI}
import config.TwitchConfiguration

class PriestChecker(_twitchAPI: TwitchAPI) extends TwitchCommandContract(_twitchAPI) {
  override def matchCommand(chatMessage: ChatMessage): Boolean = {
    !twitchAPI.isPriestHere && chatMessage.username.toLowerCase().contains("priest")
  }

  override def executeCommand(chatMessage: ChatMessage, twitchBot: ActorRef): Unit = {
    twitchAPI.sendRawMessageInChat(chatMessage.username + " has arrived")
    twitchAPI.isPriestHere = true
  }
}
