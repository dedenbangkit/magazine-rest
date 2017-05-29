package models


import org.joda.time.DateTime
import models.dao.MagzApiDAO
import org.joda.time.format.DateTimeFormat
import play.api.libs.concurrent.Akka
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.Play.current

case class MagzApi(magazineId: Int, issueName: String, masterId: Int, issueCover: String, zipFile: String, releaseDate: DateTime, pageCount:Int)

object MagzApi {

  val dateFormat = "yyyy-MM-dd'T'yyyy-MM-dd.SSSZ"

  val jodaDateReads = Reads[DateTime](js =>
    js.validate[String].map[DateTime](dtString =>
      DateTime.parse(dtString, DateTimeFormat.forPattern(dateFormat))
    )
  )

  val jodaDateWrites: Writes[DateTime] = new Writes[DateTime] {
    def writes(d: DateTime): JsValue = JsString(d.toLocalDate.toString())
  }

  val zipUrl = "https://s3-ap-southeast-1.amazonaws.com/publixx-statics/issue-lib/"

  val writeUrl: Writes[String] = new Writes[String]{
    def writes(url: String): JsValue = JsString(zipUrl + url)
  }

  val magzApiReads: Reads[MagzApi] = (
    (JsPath \ "magazineId").read[Int] and
    (JsPath \ "issueName").read[String] and
    (JsPath \ "masterId").read[Int] and
    (JsPath \ "issueCover").read[String] and
    (JsPath \ "zipFile").read[String] and
    (JsPath \ "releaseDate").read[DateTime](jodaDateReads) and
    (JsPath \ "pageCount").read[Int]
    )(MagzApi.apply _)

  implicit val magzApiWrites: Writes[MagzApi] = (
    (JsPath \ "magazineId").write[Int] and
    (JsPath \ "issueName").write[String] and
    (JsPath \ "masterId").write[Int] and
    (JsPath \ "issueCover").write[String] and
    (JsPath \ "zipFile").write[String](writeUrl) and
    (JsPath \ "releaseDate").write[DateTime](jodaDateWrites) and
    (JsPath \ "pageCount").write[Int]
    )(unlift(MagzApi.unapply))

  def findAllByIssue(magazineId: Int): List[MagzApi] =
    MagzApiDAO.index(magazineId)

  def findIssue(magazineId: Int): List[MagzApi] =
    MagzApiDAO.show(magazineId)
}
