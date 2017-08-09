package models.dao

import org.joda.time.DateTime
import anorm._
import models.Page
import play.api.db.DB
import play.api.Play.current

object CommentDAO {

  def create(pageId: Int, issueId: Int): Stream[(Int, Int, String)] =
    DB.withConnection { implicit c =>
      val comment = SQL(
        """
          | INSERT IGNORE INTO `master_comment` (`issue_id`, `page_id`)
          | VALUES ({issueId},{pageId}, {userId);
        """.stripMargin).on(
        "pageId" -> pageId,
        "issueId" -> issueId
      ).apply()
      comment.map { row => (row[Int]("issue_id"), row[Int]("page_id"), row[String]("status"))
      }
    }

  def write(messages:String, userId:Int): Stream[(String, Int)] =
    DB.withConnection { implicit c =>
      val comment = SQL (
        """
          | INSERT IGNORE INTO `comment` (`content`, `user_id`)
          | VALUES ({messages},{userId})
        """.stripMargin).on(
        "messages" -> messages,
        "user_id" -> userId
      ).apply()
      comment.map{row => (row[String]("content"), row[Int]("user_id"))
      }
    }

  def check(issueId: Int): Stream[(Int, Int, String, Int)] =
    DB.withConnection { implicit c =>
      val comments = SQL(
        """
          | SELECT * FROM `master_comment`
          | WHERE `issue_id` = {issueId}
        """.stripMargin).on {
        "issueId" -> issueId
      }.apply()
      comments.map { row =>
        (row[Int]("id"), row[Int]("issue_id"),row[String]("status"),row[Int]("page_id"))
      }
    }

}