package model.TwitchBotDatabase

import model.Question

trait TwitchBotContract {

  def addQuestion(question: Question): Unit

  def updateQuestion(question: Question): Unit

  def unansweredQuestions(): List[Question]

  def questions(): List[Question]

}
