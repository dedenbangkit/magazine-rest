package models.dao

import anorm._
import models.Validate
import play.api.Play.current
import play.api.db.DB

object ValidateDAO {

  def exists(validate: Validate): Boolean = {
    DB.withConnection { implicit c =>
      val result = SQL(
        """
          | SELECT COUNT(*) as numMatches
          | FROM `member`
          | WHERE `id`={userId} AND `apiKey`={key};
        """.stripMargin).on(
        "userId" -> validate.userId,
        "key" -> validate.key
      ).apply().head

      result[Int]("numMatches") != 0
    }
  }

  def index(userId: Int, key:String): List[Validate] = {
    DB.withConnection { implicit c =>
      val results = SQL(
        """
          | SELECT `userId`, `apiKey`
          | FROM `member`
          | WHERE `id`={userId} AND apiKey={key};
        """.stripMargin).on(
        "userId" -> userId,
        "key" -> key
      ).apply()

      results.map { row =>
        Validate(row[Int]("userId"), row[String]("key"))
      }.force.toList
    }
  }
}
