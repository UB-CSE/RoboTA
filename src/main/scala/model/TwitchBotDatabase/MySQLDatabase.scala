package model.TwitchBotDatabase

import config.DatabaseConn
import model.Question
import java.sql.{Connection}


class MySQLDatabase extends TwitchBotContract {

  var connection: Connection = DatabaseConn.getDBConnection()

  initializeDatabase()
  test()

  def initializeDatabase(): Unit = {
    DatabaseConn.reconnect()
    val statement = connection.createStatement()
    statement.execute("DROP TABLE IF EXISTS questions")
    statement.execute("CREATE TABLE IF NOT EXISTS questions (displayId TEXT, questionText TEXT, submitter TEXT, answered BOOLEAN, uniqueId INT NOT NULL PRIMARY KEY AUTO_INCREMENT)")

  }

  def test(): Unit = {
    DatabaseConn.reconnect()
    val statement = connection.prepareStatement("INSERT INTO questions (displayId, questionText, submitter, answered) VALUES (?, ?, ?, ?)")
    statement.setString(1, "1")
    statement.setString(2, "How ya'll doin?")
    statement.setString(3, "hartloff")
    statement.setBoolean(4, false)
    statement.execute()

    println("made it here!")

    val s2 = connection.createStatement()
    val results = s2.executeQuery("SELECT * FROM questions")
    while(results.next()){
      println("uniqueId: " + results.getInt("uniqueId"))
      println("displayId: " + results.getString("displayId"))
      println("questionText: " + results.getString("questionText"))
      println("submitter: " + results.getString("submitter"))
      println("answered: " + results.getBoolean("answered"))
    }
  }



  override def addQuestion(question: Question): Unit = {

    DatabaseConn.reconnect()
//    this.database += question.uniqueId -> question
  }

  override def updateQuestion(question: Question): Unit = {
    DatabaseConn.reconnect()
//    this.database += question.uniqueId -> question
  }

  override def unansweredQuestions(): List[Question] = {
    DatabaseConn.reconnect()
//    this.database.values.toList.filter(!_.answered)
    null
  }
  override def questions(): List[Question] = {
    DatabaseConn.reconnect()
//    this.database.values.toList
    null
  }

}
