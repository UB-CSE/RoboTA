package model.command

//only use when you need to directly output message to chat
//test are done locally
abstract class command {
  var outputmessage:String = ""
  var prefixsender:String = "PRIVMSG #hartloff :"  //#channel can change base on which channel you want to sent

}
