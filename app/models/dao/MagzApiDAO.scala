package models.dao

import anorm._
import models.MagzApi
import play.api.db.DB
import play.api.Play.current

object MagzApiDAO {


    def show(magazineId: Int): List[MagzApi] = {
    DB.withConnection { implicit c =>
      val result = SQL(
        """
          | SELECT `magazineId`,`issueNumber`,`title`,`author`,`summary`,`category`,`authorLink`,`releaseDate`,`pageUrl`,`imgUrl`
          | FROM `magazine_list`
          | WHERE `magazineId`={magazineId};
        """.stripMargin).on(
          "magazineId" -> magazineId
        ).apply()
        result.map { row =>
        MagzApi(row[Int]("magazineId"), row[Int]("issueNumber"),row[String]("title"),row[String]("author"),row[String]("summary"),row[String]("category"),row[String]("authorLink"),row[String]("releaseDate"),row[String]("pageUrl"),row[String]("imgUrl"))
      }.force.toList
    }
  }

  def index(magazineId: Int): List[MagzApi] = {
    DB.withConnection { implicit c =>
      val results = SQL(
        """
          | SELECT `magazineId`,`issueNumber`,`title`,`author`,`summary`,`category`,`authorLink`,`releaseDate`,`pageUrl`,`imgUrl`
          | FROM `magazine_list`
          | WHERE `magazineId`={magazineId};
        """.stripMargin).on(
          "magazineId" -> magazineId
        ).apply()

      results.map { row =>
        MagzApi(row[Int]("magazineId"), row[Int]("issueNumber"),row[String]("title"),row[String]("author"),row[String]("summary"),row[String]("category"),row[String]("authorLink"),row[String]("releaseDate"),row[String]("pageUrl"),row[String]("imgUrl"))
      }.force.toList
    }
  }
}
