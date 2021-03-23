package tests

import org.scalatest.FunSuite

class PriestTest extends FunSuite{

  def testPriest(rawmessage:String):Boolean ={
    if (rawmessage.contains("USERSTATE")){
      val messageUsername:List[String] = rawmessage.split(";").toList
      var placeholder:String = ""
      val Recollecting:Unit = for (information <- messageUsername if information.contains("display-name")) placeholder = information.split("=")(1)
      if(placeholder.toLowerCase().contains("priest")){
        return true
      }
    }
    false
  }
  test("AHHHHHHHHHH"){
    val Truetestcase:List[String] = List(
    "@badge-info=<badge-info>;badges=<badges>;color=<color>;display-name=Priest;emote-sets=<emote-sets>;mod=<mod>;subscriber=<subscriber>;turbo=<turbo>;user-type=<user-type>:tmi.twitch.tv USERSTATE #<channel>",
    "display-name=Priest;emote-sets=<emote-sets>;mod=<mod>;subscriber=<subscriber>;turbo=<turbo>;user-type=<user-type>:tmi.twitch.tv USERSTATE #<channel>",
    "display-name=Priest:tmi.twitch.tv USERSTATE #<channel>"
    )
    val Falsetestcae:List[String] =List(
      "@badge-info=<badge-info>;badges=<badges>;color=<color>;;emote-sets=<emote-sets>;mod=<mod>;subscriber=<subscriber>;turbo=<turbo>;user-type=<user-type>:tmi.twitch.tv USERSTATE #<channel>",
      "emote-sets=<emote-sets>;mod=<mod>;subscriber=<subscriber>;turbo=<turbo>;user-type=<user-type>:tmi.twitch.tv USERSTATE #<channel>"
    )


    for(message <- Truetestcase){
      assert(testPriest(message),message)
    }

    for(message <- Falsetestcae){
      assert(!testPriest(message),message)
    }
  }

}
