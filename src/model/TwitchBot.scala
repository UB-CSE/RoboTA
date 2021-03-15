package model

class TwitchBot(database: Database = new TestDatabase) {

  /**
   * Called when a user enters "!q <question_text>" in the chat
   *
   * Assigns a "display id" for users to upvote which is the smallest integer >=1 that is not
   * assigned to an unanswered question
   *
   * @param streamId     A unique id for the stream where the question was submitted (Currently not used)
   * @param questionText The test of the question
   * @param submitter    The Twitch username of the user who asked the question
   */
  def questionSubmitted(streamId: String, questionText: String, submitter: String): Unit = {
    // do some processing
    // question needs a unique id
    // ids should be short

    val displayId: String = this.getNextAvailableDisplayId()
    val question: Question = new Question(displayId, questionText, submitter)

    database.addQuestion(question)

  }

  /**
   * Called when a user "!u <question_id>" in chat
   *
   * @param questionID The unique id for the question
   * @param voter      The Twitch username of the voter
   */
  def upvote(displayId: String, voter: String): Unit = {
    val questions = database.questions().filter(!_.answered).filter(_.displayId == displayId)
    if (questions.isEmpty) {
      // User submitted an invalid question
    } else if (questions.length > 1) {
      // we messed up
    } else {
      val theQuestion = questions.head
      theQuestion.addUpvote(voter)
      database.updateQuestion(theQuestion)
    }
  }

  /**
   * Returns all unanswered questions sorted by decreasing number of upvotes
   *
   * @param streamId The id of the stream
   * @return The questions
   */
  def questions(streamId: String): List[Question] = {
    database.unansweredQuestions().sortBy(_.numberOfUpvotes()).reverse
  }

  def questionAnswered(displayId: String): Unit = {
    val questions = database.unansweredQuestions().filter(_.displayId == displayId)
    if (questions.isEmpty) {
      // You submitted an invalid question
    } else if (questions.length > 1) {
      // we messed up
    } else {
      val theQuestion = questions.head
      theQuestion.answered = true
      database.updateQuestion(theQuestion)
    }
  }


  def getNextAvailableDisplayId(): String = {
    val unansweredQuestionsByDisplayIdentification = this.database.unansweredQuestions().sortBy(_.displayId)
    var i: Int = 1
    while (i <= unansweredQuestionsByDisplayIdentification.length &&
      unansweredQuestionsByDisplayIdentification(i - 1).displayId == i.toString) {
      i += 1
    }
    i.toString
  }
}
