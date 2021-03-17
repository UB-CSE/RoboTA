package model

case class NewQuestion(streamId: String, questionText: String, submitter: String)
case class Upvote(displayId: String, voter: String)
case class QuestionAnswered(displayId: String)
case object GetQuestions
