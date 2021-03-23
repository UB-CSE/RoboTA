package controller.TwitchCommands

import akka.actor.ActorRef
import controller.{ChatMessage, TwitchAPI}
import model.{QuestionAnswered, Upvote}

class RemoveQuestionCommand(_twitchAPI: TwitchAPI) extends TwitchCommandContract(_twitchAPI) {
  override def commandPrefixes: List[String] = {
    List(
      "!a ", "!answer "
    )
  }

  override def executeCommand(chatMessage: ChatMessage, twitchBot: ActorRef): Unit = {
    twitchBot ! QuestionAnswered(chatMessage.removedCommandText)
  }
}
