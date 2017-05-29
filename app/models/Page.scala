package models

import org.joda.time.DateTime
import models.dao.PageDAO
import play.api.libs.concurrent.Akka
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.Play.current

case class Page(issueId: Int, pageNum: Int, pageName: String, pageContent: Option[String])

object Page {


  implicit val PageWrites: Writes[Page] = (
      (JsPath \ "issueId").write[Int] and
      (JsPath \ "pageId").write[Int] and
      (JsPath \ "pageName").write[String] and
      (JsPath \ "pageContent").writeNullable[String]
    )(unlift(Page.unapply))

  def findAllPage(issueId:Int): List[Page] =
    PageDAO.index(issueId)

  def find(issueId:Int): List[Page] =
    PageDAO.show(issueId)
}
