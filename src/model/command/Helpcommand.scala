package model.command

//only use for command that sent message using socket

class Helpcommand(username:String) extends command {
    val CommandMapping:Map[String,String] = Map(
      "!q,!question:" -> "add question to bot",
      "!u:" -> "upvote the question base on unique id #",   //if someone find a way to breakline in twitch
      "!a:" -> "that question is already answer" //Need help with formatting the white space
    )

  //current know way to breakline in twitch:
    //count space
    //link
    //https://www.reddit.com/r/Twitch/comments/crn1ko/line_breaks_for_chat_commands/

    for((command ,explain) <- CommandMapping){
      this.outputmessage = " "+ this.outputmessage + command + " " + explain //twitch doesn't take line break
//      val lengthword:Int = this.outputmessage.length
//      val amountofWhitespace:Int = (length of the username + amounte of whitespace left) - lengthword
//      var placeholder = " "
//      val whitespace:Unit = for(i <- 0 until amountofWhitespace) placeholder = placeholder + " "
//      this.outputmessage = this.outputmessage + placeholder
    }

  this.outputmessage = this.prefixsender+username+" "+this.outputmessage
    // I didn't add this command in the twitchApi file since I think it might conflict with y2nk's pull request

}
