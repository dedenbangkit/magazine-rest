package models

case class MagzNotification(recipient: Page, magz: MagzApi) {
  def send(): Unit = {
    // TODO: Send a notification to Amazon SNS
    println("SENDING PUSH NOTIFICATION TO: "+recipient.pageMagazineId)
  }
}