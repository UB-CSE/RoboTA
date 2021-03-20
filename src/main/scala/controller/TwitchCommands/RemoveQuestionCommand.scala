package controller.TwitchCommands

import akka.actor.ActorRef
import controller.ChatMessage
import model.{QuestionAnswered, Upvote}

class RemoveQuestionCommand extends TwitchCommandContract {
  override def commandPrefixes: List[String] = {
    List(
      "!a ", "!answer "
    )
  }

  override def executeCommand(chatMessage: ChatMessage, twitchBot: ActorRef): Unit = {
    twitchBot ! QuestionAnswered(chatMessage.removedCommandText)
  }
}
