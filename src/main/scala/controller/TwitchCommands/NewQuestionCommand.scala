package controller.TwitchCommands

import akka.actor.ActorRef
import controller.{ChatMessage, TwitchAPI}
import model.NewQuestion

class NewQuestionCommand(_twitchAPI: TwitchAPI) extends TwitchCommandContract(_twitchAPI) {
  override def commandPrefixes: List[String] = {
    List(
      "!q ", "!question "
    )
  }

  override def executeCommand(chatMessage: ChatMessage, twitchBot: ActorRef): Unit = {
    twitchBot ! NewQuestion("",
      chatMessage.removedCommandText,
      chatMessage.username)
  }
}
