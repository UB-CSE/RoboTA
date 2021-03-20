package controller.TwitchCommands

import akka.actor.ActorRef
import controller.ChatMessage
import model.NewQuestion

class NewQuestionCommand extends TwitchCommandContract {
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
