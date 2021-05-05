package model

object QuestionListFunctions {

  def percentageAnswered(qList: List[Question]): Double ={
    val boolList = qList.map(_.answered)
    Math.floor(boolList.count(_ == true).toDouble / boolList.length * 10000.0) / 100
  }

}
