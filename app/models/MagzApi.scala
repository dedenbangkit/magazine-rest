package models

import org.joda.time.DateTime
import models.dao.MagzApiDAO
import play.api.libs.concurrent.Akka
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.Play.current

case class MagzApi(magazineId: Int, issueNumber: Int, title: String, author: String, summary: String, category: String, authorLink: String, releaseDate: String, pageUrl: String, imgUrl: String)

object MagzApi {

  implicit val magzApiWrites: Writes[MagzApi] = (
    (JsPath \ "magazineId").write[Int] and
    (JsPath \ "issueNumber").write[Int] and
    (JsPath \ "title").write[String] and
    (JsPath \ "author").write[String] and
    (JsPath \ "summary").write[String] and
    (JsPath \ "category").write[String] and
    (JsPath \ "authorLink").write[String] and
    (JsPath \ "releaseDate").write[String] and
    (JsPath \ "pageUrl").write[String] and
    (JsPath \ "imgUrl").write[String]
    )(unlift(MagzApi.unapply))

  def findAllByIssue(magazineId: Int): List[MagzApi] =
    MagzApiDAO.index(magazineId)

  def findIssue(magazineId: Int): List[MagzApi] =
    MagzApiDAO.show(magazineId)
}
