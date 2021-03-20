package controller.TwitchCommands

import akka.actor.ActorRef
import controller.{ChatMessage, TwitchAPI}

abstract class TwitchCommandContract {
  def commandPrefixes(): List[String] = {
    List()
  }

  def matchCommand(chatMessage: ChatMessage): Boolean = {
    var isMatch: Boolean = false
    val messageText: String = chatMessage.messageText.trim()

    for (prefix <- commandPrefixes()) {
      // search only if isMatch is false
      // so the search will be more efficient
      if (!isMatch) {
        isMatch = messageText.startsWith(prefix)
      }
    }

    isMatch
  }
  def executeCommand (chatMessage: ChatMessage, twitchBot: ActorRef): Unit;
}
