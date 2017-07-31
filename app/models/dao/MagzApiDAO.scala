package models.dao

import org.joda.time.DateTime
import anorm._
import models.MagzApi
import play.api.db.DB
import play.api.Play.current

object MagzApiDAO {


  def show(magazineId: Int): List[MagzApi] = {
    DB.withConnection { implicit c =>
      val results = SQL(
        """
          | SELECT `id`,`issue_name`,`issue_master`,`project_id`,`issue_cover`,`status`,`compiled`,`created_at`,`updated_at`,`deleted_at`,`page_counter`
          | FROM `issue`
          | WHERE `deleted_at` IS NULL
          | AND `status`="published"
          | AND `project_id`={project_id}
          | ORDER BY `updated_at` DESC;
        """.stripMargin).on(
        "project_id" -> magazineId
      ).apply()
      results.map { row =>
        MagzApi(row[Int]("id"),row[String]("issue_name"),row[Int]("issue_master"),row[String]("issue_cover"),row[String]("compiled"),row[DateTime]("updated_at"), row[Int]("page_counter"))
      }.force.toList
    }
  }

  def index(magazineId: Int): List[MagzApi] = {
    DB.withConnection { implicit c =>
      val results = SQL(
        """
          | SELECT `id`,`issue_name`,`issue_master`,`project_id`,`issue_cover`,`status`,`compiled`,`created_at`,`updated_at`,`deleted_at`,`page_counter`
          | FROM `issue`
          | WHERE `deleted_at` IS NULL
          | AND `deleted_at`= null
          | AND `status`="published"
          | AND `project_id`={project_id};
          | ORDER BY `updated_at` DESC;
        """.stripMargin).on(
          "project_id" -> magazineId
        ).apply()
      results.map { row =>
        MagzApi(row[Int]("id"),row[String]("issue_name"),row[Int]("issue_master"),row[String]("issue_cover"),row[String]("compiled"),row[DateTime]("updated_at"), row[Int]("page_counter"))
      }.force.toList
    }
  }

  def check(codeId: String, projectId:Int): List[MagzApi] = {
    DB.withConnection { implicit c =>
      val results = SQL(
        """
          | SELECT `id`,`issue_name`,`issue_master`,`project_id`,`issue_cover`,`status`,`compiled`,`created_at`,`updated_at`,`deleted_at`,`page_counter`
          | FROM `issue`
          | WHERE `deleted_at` IS NULL
          | AND `status`="unpublished"
          | AND `project_id`={project_id}
          | AND `token`={code_id}
          | ORDER BY `updated_at` DESC;
        """.stripMargin).on(
        "project_id" -> projectId,
        "code_id" -> codeId
      ).apply()
      results.map { row =>
        MagzApi(row[Int]("id"),row[String]("issue_name"),row[Int]("issue_master"),row[String]("issue_cover"),row[String]("compiled"),row[DateTime]("updated_at"), row[Int]("page_counter"))
      }.force.toList
    }
  }
}
