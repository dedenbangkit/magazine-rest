package models.dao

import org.joda.time.DateTime
import anorm._
import play.api.db.DB
import play.api.Play.current

object CommentDAO {

  def create(pageId: Int, issueId: Int): Option[Long] =
    DB.withConnection (implicit c => SQL(
        """
          | INSERT IGNORE INTO `master_comment` (`issue_id`, `page_id`)
          | VALUES ({issueId},{pageId})
        """.stripMargin).on(
        "pageId" -> pageId,
        "issueId" -> issueId
        ).executeInsert()
    )

  def write(masterId:Int, messages:String, userId:Int) =
    DB.withConnection(implicit c => SQL (
        """
          | INSERT IGNORE INTO `comment` (`master_comment_id`,`content`, `user_id`)
          | VALUES ({master_id}, {messages},{user_id})
        """.stripMargin).on(
        "master_id" -> masterId,
        "messages" -> messages,
        "user_id" -> userId
      ).executeInsert()
    )

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
