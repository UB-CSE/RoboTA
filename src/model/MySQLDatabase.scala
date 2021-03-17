package model

import java.sql.{Connection, DriverManager}

class MySQLDatabase extends Database {

  val url = "jdbc:mysql://localhost/twitchbot?autoReconnect=true"

  val username: String = "root" //sys.env("DB_USERNAME")
  val password: String = "123456" // sys.env("DB_PASSWORD")

  println(username)
  println(password)

  var connection: Connection = DriverManager.getConnection(url, username, password)

  initializeDatabase()
  test()

  def reconnect(): Unit = {
    if(this.connection.isClosed) {
      this.connection = DriverManager.getConnection(url, username, password)
    }
  }

  def initializeDatabase(): Unit = {
    reconnect()
    val statement = connection.createStatement()
    statement.execute("DROP TABLE IF EXISTS questions")
    statement.execute("CREATE TABLE IF NOT EXISTS questions (displayId TEXT, questionText TEXT, submitter TEXT, answered BOOLEAN, uniqueId INT NOT NULL PRIMARY KEY AUTO_INCREMENT)")

  }

  def test(): Unit = {
    reconnect()
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
    reconnect()
//    this.database += question.uniqueId -> question
  }

  override def updateQuestion(question: Question): Unit = {
    reconnect()
//    this.database += question.uniqueId -> question
  }

  override def unansweredQuestions(): List[Question] = {
    reconnect()
//    this.database.values.toList.filter(!_.answered)
    null
  }
  override def questions(): List[Question] = {
    reconnect()
//    this.database.values.toList
    null
  }

}
