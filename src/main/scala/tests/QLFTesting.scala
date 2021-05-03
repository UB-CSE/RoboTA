package tests

import org.scalatest.FunSuite
import model.{QuestionListFunctions, Question}

class QLFTesting extends FunSuite{

  test("Upvotes Empty List"){
    val empty = List()
    val output = QuestionListFunctions.sortByUpvotes(empty)
    assert(output.equals(empty))
  }

  test("Upvotes In Order"){
    val q1: Question = new Question("1","First?","first")
    val q2:Question = new Question("2", "Is this a question?", "second")
    q2.addUpvote("voter")
    val output = QuestionListFunctions.sortByUpvotes(List(q1, q2)).map(_.displayId)
    assert(output.equals(List("2","1")))
  }

  test("Upvotes Same Order"){
    val q1: Question = new Question("1","First?","first")
    val q2:Question = new Question("2", "Is this a question?", "second")
    q2.addUpvote("voter")
    val q3:Question = new Question("2", "Is this a better question?", "third")
    q3.addUpvote("voter")
    q3.addUpvote("voter")
    val output = QuestionListFunctions.sortByUpvotes(List(q3, q2, q1)).map(_.displayId)
    assert(output.equals(List("3","2","1")))
  }

}
