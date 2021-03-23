package model.command

import controller.TwitchAPI

class PriestChecker(username:String,twitchAPI: TwitchAPI) extends command {
  if(username.toLowerCase().contains("priest") && !twitchAPI.IsPriesthere){
    this.outputmessage = this.prefixsender + username + " has arrived"
    twitchAPI.IsPriesthere = true
  }
}
