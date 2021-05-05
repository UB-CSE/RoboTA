package controller.TwitchCommands

import akka.actor.ActorRef
import controller.{ChatMessage, TwitchAPI}
import model.Upvote

class HelpCommand(_twitchAPI: TwitchAPI) extends TwitchCommandContract(_twitchAPI) {
  override def commandPrefixes: List[String] = {
    List(
      "!h", "!help", "!Help", "!Commands", "!commands"
    )
  }

  override def executeCommand(chatMessage: ChatMessage, twitchBot: ActorRef): Unit = {
    twitchAPI.sendRawMessageInChat(chatMessage.username + ", the commands are \"!q\" and \"!question\" to register a question, \"!a\" and \"!answer\" to answer a" +
      "question followed by its ID, and \"!u\" and \"!upvote\" to upvote a question followed by its ID.")
  }

}
