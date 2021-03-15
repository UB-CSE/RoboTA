package model

trait Database {

  def addQuestion(question: Question): Unit
  def updateQuestion(question: Question): Unit

  def unansweredQuestions(): List[Question]
  def questions(): List[Question]

}
