package models

import org.joda.time.DateTime
import models.dao.PageDAO
import play.api.libs.concurrent.Akka
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.Play.current

case class Page(pageUrl: String, pageNum: Int, pageContent: String, magazineId: Int, issueId: Int, imgUrl:String, downloadUrl: String)

object Page {

  implicit val PageWrites: Writes[Page] = (
      (JsPath \ "pageUrl").write[String] and
      (JsPath \ "pageNum").write[Int] and
      (JsPath \ "pageContent").write[String] and
      (JsPath \ "magazineId").write[Int] and
      (JsPath \ "issueId").write[Int] and
      (JsPath \ "imgUrl").write[String] and
      (JsPath \ "downloadUrl").write[String]
    )(unlift(Page.unapply))

  def findAllPage(issueId:Int, magazineId: Int): List[Page] =
    PageDAO.index(issueId, magazineId)

  def find(issueId:Int, magazineId: Int): List[Page] =
    PageDAO.show(issueId, magazineId)
}
