package controller

class ChatMessage(val username: String, val messageText: String) {
  var removedCommandText: String = ""
  private var _message: String = messageText.trim()

  // remove command from the chat-text
  if (_message.indexOf(' ') < (_message.length - 1)) {
    removedCommandText = _message.trim().substring(_message.indexOf(' ') + 1).trim()
  }
}
