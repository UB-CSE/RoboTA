package tests

import controller.{ChatMessage, TwitchAPI}
import model.Question
import org.scalatest.FunSuite

class TestParsingTwitchMessages extends FunSuite {

  test("parse a message") {
    val computed: ChatMessage = TwitchAPI.parseChatMessage(":priestnk1!priestnk1@priestnk1.tmi.twitch.tv PRIVMSG #hartloff :now we gotta let jesse see that comment")
    assert(computed.username == "priestnk1")
    assert(computed.messageText == "now we gotta let jesse see that comment")
  }

  test("parse another message") {
    val computed: ChatMessage = TwitchAPI.parseChatMessage(":thymufinman!thymufinman@thymufinman.tmi.twitch.tv PRIVMSG #hartloff :Dont worry jesse we’ll find a way to break twitch bot:D")
    assert(computed.username == "thymufinman")
    assert(computed.messageText == "Dont worry jesse we’ll find a way to break twitch bot:D")
  }

}
