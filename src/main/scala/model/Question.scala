package model

object Question {
  var currentUniqueId = 0

  def getUniqueId(): Int = {
    this.currentUniqueId += 1
    this.currentUniqueId
  }
}

class Question(val displayId: String, val questionText: String, val submitter: String) {

  val uniqueId: Int = Question.getUniqueId()

  var upvoters: List[String] = List()
  var answered: Boolean = false

  def numberOfUpvotes(): Int = {
    this.upvoters.size
  }

  def addUpvote(username: String): Unit = {
    if (!this.upvoters.contains(username)) {
      this.upvoters ::= username
    }
  }


  override def toString: String = {
    "displayId: " + displayId + " | questionText: " + questionText + " | submitter: " + submitter +
      " | answered: " + answered + " | upvoters: " + upvoters + " | uniqueId: " + uniqueId
  }
}
