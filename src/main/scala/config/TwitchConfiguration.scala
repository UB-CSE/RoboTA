package config

object TwitchConfiguration {
  var outputTips:String = ""

  var twitchChannel: String =
    if (sys.env("TWITCH_CHANNEL_NAME") != "") {
      sys.env("TWITCH_CHANNEL_NAME")
    } else {
      "hartloff"
    }

  var senderPrefix:String = "PRIVMSG #" + twitchChannel + " :"  //#channel can change base on which channel you want to sent
}
