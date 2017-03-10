package models.dao

import org.joda.time.DateTime
import anorm._
import models.Page
import play.api.db.DB
import play.api.Play.current

object PageDAO {


  def show(issueId:Int, magazineId: Int): List[Page] = {
    DB.withConnection { implicit c =>
      val result = SQL(
        """
          | SELECT `pageUrl`,`pageNum`,`pageContent`,`magazineId`,`issueId`,`imgUrl`,`downloadUrl`
          | FROM `magazine_issue`
          | WHERE `magazineId`={magazineId} AND `issueId`={issueId};
        """.stripMargin).on(
        "magazineId" -> magazineId,
        "issueId" -> issueId
      ).apply()
      result.map { row =>
        Page(row[String]("pageUrl"),row[Int]("pageNum"),row[String]("pageContent"),row[Int]("magazineId"),row[Int]("issueId"),row[String]("imgUrl"),row[String]("downloadUrl"))
      }.force.toList
    }
  }

  def index(issueId:Int, magazineId: Int): List[Page] = {
    DB.withConnection { implicit c =>
      val results = SQL(
        """
          | SELECT `pageUrl`,`pageNum`,`pageContent`,`magazineId`,`issueId`,`imgUrl`,`downloadUrl`
          | FROM `magazine_issue`
          | WHERE `magazineId`={magazineId} AND `issueId`={issueId};
        """.stripMargin).on(
        "magazineId" -> magazineId,
        "issueId" -> issueId
      ).apply()
      results.map { row =>
        Page(row[String]("pageUrl"),row[Int]("pageNum"),row[String]("pageContent"),row[Int]("magazineId"),row[Int]("issueId"),row[String]("imgUrl"),row[String]("downloadUrl"))
      }.force.toList
    }
  }
}
