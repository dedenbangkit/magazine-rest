package models

import models.dao.UserDAO
import play.api.libs.concurrent.Akka
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.Play.current

case class User(projectId: Int, key: String, position: String)

object User {

  implicit val userWrites: Writes[User] = (
    (JsPath \ "projectId").write[Int] and
    (JsPath \ "key").write[String] and
    (JsPath \ "position").write[String]
    )(unlift(User.unapply))

  def find(key:String, position:String): List[User] =
    UserDAO.find(key, position)

  def check(key: String, projectId: Int, position:String): Option[User] = {
    val user = User(projectId, key, position)
    if (UserDAO.exists(user))
      Some(user)
    else
      None
  }
}
