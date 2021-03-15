package tests

import model.{Database, Question, TestDatabase, TwitchBot}
import org.scalatest.FunSuite

class TestTwitchBot extends FunSuite {

  def compareQuestions(expected: Question, actual: Question): Unit = {
    assert(expected.answered == actual.answered, "\nexpected -> " + expected + "\nactual -> " + actual)
    assert(expected.submitter == actual.submitter, "\nexpected -> " + expected + "\nactual -> " + actual)
    assert(expected.numberOfUpvotes() == actual.numberOfUpvotes(), "\nexpected -> " + expected + "\nactual -> " + actual)
    assert(expected.displayId == actual.displayId, "\nexpected -> " + expected + "\nactual -> " + actual)
    assert(expected.upvoters.sorted == actual.upvoters.sorted, "\nexpected -> " + expected + "\nactual -> " + actual)
  }

  test("add a single question") {
    val database: Database = new TestDatabase()
    val bot: TwitchBot = new TwitchBot(database)

    bot.questionSubmitted("stream_id", "Am I behind if I'm still on LT2??", "bicknrown")

    val questions: List[Question] = bot.questions("stream_id")

    val theOnlyQuestion: Question = questions.head
    assert(!theOnlyQuestion.answered)
    assert(theOnlyQuestion.submitter == "bicknrown")
    assert(theOnlyQuestion.numberOfUpvotes() == 0)
    assert(theOnlyQuestion.displayId == "1")
  }

  test("giant test so we can start implementing") {
    val database: Database = new TestDatabase()
    val bot: TwitchBot = new TwitchBot(database)

    bot.questionSubmitted("stream_id", "were we supposed to use more?", "thymufinman")
    bot.questionSubmitted("stream_id", "Am I behind if I'm still on LT2??", "bicknrown")
    bot.questionSubmitted("stream_id", "which one you mean?", "Y2Nk4")

    bot.upvote("1", "Y2Nk4")
    bot.upvote("1", "Y2Nk4") // not allowed
    bot.upvote("1", "Robloxlover42069")

    bot.upvote("3", "thymufinman")
    bot.upvote("3", "bicknrown")
    bot.upvote("3", "hartloff")
    bot.upvote("3", "repetiiton")


    val questions: List[Question] = bot.questions("stream_id")

    val expected: List[Question] = List(
      new Question("3", "which one you mean?", "Y2Nk4") {
        upvoters = List("thymufinman", "bicknrown", "hartloff", "repetiiton")
        answered = false
      },
      new Question("1", "were we supposed to use more?", "thymufinman") {
        upvoters = List("Y2Nk4", "Robloxlover42069")
        answered = false
      },
      new Question("2", "Am I behind if I'm still on LT2??", "bicknrown") {
        upvoters = List()
        answered = false
      },
    )

    assert(questions.length == expected.length)

    for(i <- expected.indices){
      compareQuestions(expected(i), questions(i))
    }

    bot.questionAnswered("1")

    bot.questionSubmitted("stream_id", "would Lightning McQueen buy car insurance or life insurance?", "DragoniteSpam")

    bot.upvote("1", "bicknrown")
    bot.upvote("1", "hartloff")
    bot.upvote("1", "repetiiton")
    bot.upvote("1", "Y2Nk4")
    bot.upvote("1", "Robloxlover42069")

    val expected2: List[Question] = List(

      new Question("1", "would Lightning McQueen buy car insurance or life insurance?", "DragoniteSpam") {
        upvoters = List("bicknrown", "hartloff", "repetiiton", "Y2Nk4", "Robloxlover42069")
        answered = false
      },new Question("3", "which one you mean?", "Y2Nk4") {
        upvoters = List("thymufinman", "bicknrown", "hartloff", "repetiiton")
        answered = false
      },
      new Question("2", "Am I behind if I'm still on LT2??", "bicknrown") {
        upvoters = List()
        answered = false
      },
    )

    val questions2: List[Question] = bot.questions("stream_id")
    assert(questions2.length == expected.length)

    for(i <- expected2.indices){
      compareQuestions(expected2(i), questions2(i))
    }

  }

}
