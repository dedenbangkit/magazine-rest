package models.dao

import anorm._
import models.User
import play.api.Play.current
import play.api.db.DB

object UserDAO {

  def exists(user: User): Boolean = {
    DB.withConnection { implicit c =>
      val result = SQL(
        """
          | SELECT COUNT(*) as numMatches
          | FROM `users`
          | WHERE `project_id`={projectId}
          | AND `position`={position}
          | AND `remember_token`={key};
        """.stripMargin).on(
        "projectId" -> user.projectId,
        "key" -> user.key,
        "position" -> user.position
      ).apply().head
      result[Int]("numMatches") != 0
    }
  }

  def find(key:String, position:String): List[User] = {
    DB.withConnection { implicit c =>
      val results = SQL(
        """
          | SELECT `remember_token`, `position`, `project_id`
          | FROM `users`
          | WHERE `remember_token`={key}
          | AND `position`={position};
        """.stripMargin).on(
        "key" -> key,
        "position" -> position
      ).apply()
      results.map { row =>
        User(row[Int]("project_id"), row[String]("remember_token"), row[String]("position"))
      }.force.toList
    }
  }

}
