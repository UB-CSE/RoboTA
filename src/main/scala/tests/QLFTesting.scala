package tests

import model.Question
import model.QuestionListFunctions
import org.scalatest.FunSuite

class QLFTesting extends FunSuite{
  test("percentage_answered"){
    val q1: Question = new Question("1","First?","first")
    val q2:Question = new Question("2", "Is this a question?", "second")
    q2.addUpvote("voter")
    val q3:Question = new Question("3", "Is this a better question?", "third")
    q3.addUpvote("voter")
    q3.addUpvote("voter")

    val questionList = List(q1, q2, q3)

    var percentage = QuestionListFunctions.percentageAnswered(questionList)
    println(percentage)

    assert(TestHelper.compareDoubles(percentage, 0.0))

    q1.answered = true

    percentage = QuestionListFunctions.percentageAnswered(questionList)
    println(percentage)
    assert(TestHelper.compareDoubles(percentage, 33.33))
  }
}
