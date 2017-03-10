package models

import models.dao.ValidateDAO
import play.api.libs.concurrent.Akka
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.Play.current

case class Validate(userId: Int, key: String)

object Validate {

  implicit val validateWrites: Writes[Validate] = (
    (JsPath \ "userId").write[Int] and
    (JsPath \ "key").write[String]
    )(unlift(Validate.unapply))

  def find(userId: Int, key: String): Option[Validate] = {
    val validate = Validate(userId, key)
    if (ValidateDAO.exists(validate))
      Some(validate)
    else
      None
  }
}
