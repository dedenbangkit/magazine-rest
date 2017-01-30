package actors

import akka.actor.{Props, Actor}
import models.{MagzNotification, Page, MagzApi}

import scala.concurrent.ExecutionContext.Implicits.global

object NotificationActor {
  def props: Props = Props(new NotificationActor)
}

class NotificationActor extends Actor {
  def receive = {
    case magz: MagzApi =>
      notifyPagesOfMagz(magz)
  }

  private def notifyPagesOfMagz(magz: MagzApi): Unit = {
    // Lookup friends
    val fPages = Page.findAllPages(magz.magazineId)

    // Send a push notification to each friend
    for (
      pages <- fPages;
      page <- pages;
      notification = MagzNotification(page, magz)
    ) {
      notification.send()
    }
  }

}