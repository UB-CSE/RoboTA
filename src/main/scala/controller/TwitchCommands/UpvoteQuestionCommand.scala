package controller.TwitchCommands

import akka.actor.ActorRef
import controller.{ChatMessage, TwitchAPI}
import model.{NewQuestion, Upvote}

class UpvoteQuestionCommand(_twitchAPI: TwitchAPI) extends TwitchCommandContract(_twitchAPI) {
  override def commandPrefixes: List[String] = {
    List(
      "!u ", "!upvote "
    )
  }

  override def executeCommand(chatMessage: ChatMessage, twitchBot: ActorRef): Unit = {
    twitchBot ! Upvote(
      chatMessage.removedCommandText,
      chatMessage.username
    )
  }
}
