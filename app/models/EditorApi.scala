package models

import org.joda.time.DateTime
import models.dao.MagzApiDAO
import org.joda.time.format.DateTimeFormat
import play.api.libs.concurrent.Akka
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.Play.current

case class EditorApi(magazineId: Int, issueName: String, masterId: Int, issueCover: String, pageCount:Int, status: String, approval: String, updatedAt:DateTime)

object EditorApi {

  val dateFormat = "yyyy-MM-dd'T'yyyy-MM-dd.SSSZ"

  val jodaDateReads = Reads[DateTime](js =>
    js.validate[String].map[DateTime](dtString =>
      DateTime.parse(dtString, DateTimeFormat.forPattern(dateFormat))
    )
  )

  val jodaDateWrites: Writes[DateTime] = new Writes[DateTime] {
    def writes(d: DateTime): JsValue = JsString(d.toLocalDate.toString())
  }

  val editorApiReads: Reads[EditorApi] = (
      (JsPath \ "magazineId").read[Int] and
      (JsPath \ "issueName").read[String] and
      (JsPath \ "masterId").read[Int] and
      (JsPath \ "issueCover").read[String] and
      (JsPath \ "pageCount").read[Int] and
      (JsPath \ "status").read[String] and
      (JsPath \ "approval").read[String] and
      (JsPath \ "releaseDate").read[DateTime](jodaDateReads)
    )(EditorApi.apply _)

  val editorApiWrites: Writes[EditorApi] = (
    (JsPath \ "magazineId").write[Int] and
      (JsPath \ "issueName").write[String] and
      (JsPath \ "masterId").write[Int] and
      (JsPath \ "issueCover").write[String] and
      (JsPath \ "pageCount").write[Int] and
      (JsPath \ "status").write[String] and
      (JsPath \ "approval").write[String] and
      (JsPath \ "releaseDate").write[DateTime](jodaDateWrites)
    )(unlift(EditorApi.unapply))

  def checkDraft(projectId: Int): List[EditorApi] =
    MagzApiDAO.check(projectId)

}