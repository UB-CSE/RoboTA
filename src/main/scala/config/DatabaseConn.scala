package config

import java.sql.{Connection, DriverManager}

object DatabaseConn {
  private val _url:String = sys.env("MYSQL_DB_URL")
  private val _username: String = sys.env("DB_USERNAME")
  private val _password: String = sys.env("DB_PASSWORD")

  println(_username)
  println(_password)

  private var _connection: Connection = DriverManager.getConnection(_url, _username, _password)

  def getDBConnection (): Connection = {
    _connection
  }

  def reconnect(): Unit = {
    if(this._connection.isClosed) {
      _connection = DriverManager.getConnection(_url, _username, _password)
    }
  }
}
