import play.api.cache.{Cache, Cached}
import play.api.libs.json._
import play.api.mvc._
import play.api.Play.current
import play.api.mvc.Action
import java.net._

import models.dao.UserDAO

import scala.concurrent.Future
import scala.util.Random
import play.api.http.HeaderNames._

object CommentApis extends Controller{

  def addComment(issueId:Int, pageId:Int, key:String) = Action {
    request => request.body.asJson map { message =>
      (message \ "message").as[String]
    } match {
      case Some(message) =>
        if (UserDAO.token(key)) {
          var user = UserDAO.link(key)
          Ok(Json.obj("results" -> user)).withHeaders(
            ACCESS_CONTROL_ALLOW_ORIGIN -> "*",
            ACCESS_CONTROL_ALLOW_METHODS -> "GET, POST, OPTIONS",
            ACCESS_CONTROL_ALLOW_HEADERS -> "Origin, Accept, Authorization, X-Auth-Token",
            ACCESS_CONTROL_ALLOW_CREDENTIALS -> "true")
        } else {
          NotFound(Json.obj("results" -> "404"))
        }
      case _ => BadRequest(Json.obj("status" -> "Bad Request", "result" -> "400"))
    }
  }
}