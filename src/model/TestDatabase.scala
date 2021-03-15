package model

class TestDatabase extends Database {

  var database: Map[Int, Question] = Map() // uniqueId -> Question

  override def addQuestion(question: Question): Unit = {
    this.database += question.uniqueId -> question
  }

  override def updateQuestion(question: Question): Unit = {
    this.database += question.uniqueId -> question
  }

  override def unansweredQuestions(): List[Question] = {
    this.database.values.toList.filter(!_.answered)
  }
  override def questions(): List[Question] = {
    this.database.values.toList
  }

}
