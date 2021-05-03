package controller.TwitchCommands

import akka.actor.ActorRef
import controller.{ChatMessage, TwitchAPI}

class PriestCommand(_twitchAPI: TwitchAPI) extends TwitchCommandContract(_twitchAPI){

  override def commandPrefixes(): List[String] = {
    List(
      "!Priest", "!priest"
    )
  }

  override def executeCommand(chatMessage: ChatMessage, twitchBot: ActorRef): Unit = {
    twitchAPI.sendRawMessageInChat(chatMessage.username + ", please don't....")
  }

}
