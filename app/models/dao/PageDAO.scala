package models.dao

import org.joda.time.DateTime
import anorm._
import models.Page
import play.api.db.DB
import play.api.Play.current

object PageDAO {


  def show(issueId:Int): List[Page] = {
    DB.withConnection { implicit c =>
      val result = SQL(
        """
          | SELECT `issue_id`,`id`,`page_name`,`test_content`
          | FROM `page`
          | WHERE `issue_id`={issueId};
        """.stripMargin).on(
        "issueId" -> issueId
      ).apply()
      result.map { row =>
        Page(row[Int]("issue_id"),row[Int]("id"),row[String]("page_name"),row[Option[String]]("test_content"))
      }.force.toList
    }
  }

  def index(issueId:Int): List[Page] = {
    DB.withConnection { implicit c =>
      val results = SQL(
        """
          | SELECT `issue_id`,`id`,`page_name`,`test_content`
          | FROM `page`
          | WHERE `issue_id`={issueId};
        """.stripMargin).on(
        "issueId" -> issueId
      ).apply()
      results.map { row =>
        Page(row[Int]("issue_id"),row[Int]("id"),row[String]("page_name"),row[Option[String]]("test_content"))
      }.force.toList
    }
  }
}
