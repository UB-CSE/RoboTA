package tests

import controller.{ChatMessage, TwitchAPI}
import org.scalatest.FunSuite

class PriestCommand extends FunSuite{

  // This test is useless. I tested it on the twitch chat but I'll leave the test here.

  test("check if a message contains !Priest"){
    val computed: ChatMessage = TwitchAPI.parseChatMessage(":PriestPope!PriestPope@PriestPope.tmi.twitch.tv PRIVMSG #hartloff :!Priest")
    assert(computed.username == "PriestPope")
    assert(computed.messageText == "!Priest")
  }

}
