package controller.TwitchCommands

import akka.actor.ActorRef
import controller.ChatMessage
import model.{NewQuestion, Upvote}

class UpvoteQuestionCommand extends TwitchCommandContract {
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
