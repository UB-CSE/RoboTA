package tests

import scala.collection.JavaConverters._

import org.scalatest.FunSuite
import helpers.dotenv.Dotenv
import helpers.dotenv.DirtyEnvironmentHack

class Y2Nk4_DotEnv extends FunSuite {
  test("import_dot_env_file") {
    Dotenv.loadEnv(".env.test")

    assert(sys.env("RUNNING_ENV") == "TESTING")
  }

  test("dirty_set_environment_variables") {
    DirtyEnvironmentHack.setEnv(Map(
      "RUNNING_ENV" -> "DEFINITELY_NOT_TESTING"
    ).asJava)

    assert(sys.env("RUNNING_ENV") == "DEFINITELY_NOT_TESTING")
  }
}
