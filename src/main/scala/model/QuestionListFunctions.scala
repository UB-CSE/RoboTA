package model

object QuestionListFunctions {

  def percentageAnswered(qList: List[Question]): Double ={
    val boolList = qList.map(_.answered)
    Math.floor(boolList.count(_ == true).toDouble / boolList.length * 100.0)
  }

  def sortByUpvotes(qList: List[Question]): List[Question] ={
    val upvoteList = qList.sortBy(_.numberOfUpvotes()).reverse
    upvoteList
  }



}
