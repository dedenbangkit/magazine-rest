package models.dao

import org.joda.time.DateTime
import anorm._
import models.MagzApi
import models.EditorApi
import play.api.db.DB
import play.api.Play.current

object MagzApiDAO {


  def show(magazineId: Int): List[MagzApi] = {
    DB.withConnection { implicit c =>
      val results = SQL(
        """
          | SELECT `id`,`issue_name`,`issue_master`,`project_id`,`issue_cover`,`status`,`compiled`,`created_at`,`updated_at`,`deleted_at`,`approval`,`page_counter`
          | FROM `issue`
          | WHERE `deleted_at` IS NULL
          | AND `status`="published"
          | AND `project_id`={project_id}
          | ORDER BY `updated_at` DESC;
        """.stripMargin).on(
        "project_id" -> magazineId
      ).apply()
      results.map { row =>
        MagzApi(row[Int]("id"),row[String]("issue_name"),row[Int]("issue_master"),row[String]("issue_cover"),row[String]("compiled"),row[DateTime]("updated_at"), row[String]("approval"), row[Int]("page_counter"))
      }.force.toList
    }
  }

  def index(magazineId: Int): List[MagzApi] = {
    DB.withConnection { implicit c =>
      val results = SQL(
        """
          | SELECT `id`,`issue_name`,`issue_master`,`project_id`,`issue_cover`,`status`,`compiled`,`created_at`,`updated_at`,`deleted_at`,`approval`,`page_counter`
          | FROM `issue`
          | WHERE `deleted_at` IS NULL
          | AND `status`="published"
          | AND `project_id`={project_id}
          | ORDER BY `updated_at` DESC;
        """.stripMargin).on(
          "project_id" -> magazineId
        ).apply()
      results.map { row =>
        MagzApi(row[Int]("id"),row[String]("issue_name"),row[Int]("issue_master"),row[String]("issue_cover"),row[String]("compiled"),row[DateTime]("updated_at"), row[String]("approval"), row[Int]("page_counter"))
      }.force.toList
    }
  }

  def check(magazineId: Int): List[MagzApi] = {
    DB.withConnection { implicit c =>
      val results = SQL(
        """
          | SELECT `id`,`issue_name`,`issue_master`,`project_id`,`issue_cover`,`status`,`compiled`,`created_at`,`updated_at`,`deleted_at`,`approval`,`page_counter`
          | FROM `issue`
          | WHERE `deleted_at` IS NULL
          | AND `status`="unpublished"
          | AND `updated_at` IS NOT NULL
          | AND `project_id`={project_id}
          | ORDER BY `updated_at` DESC;
        """.stripMargin).on(
        "project_id" -> magazineId
      ).apply()
      results.map { row =>
        MagzApi(row[Int]("id"),row[String]("issue_name"),row[Int]("issue_master"),row[String]("issue_cover"),row[String]("compiled"),row[DateTime]("updated_at"), row[String]("approval"), row[Int]("page_counter"))
      }.force.toList
    }
  }

  def client(codeId: String): List[MagzApi] = {
    DB.withConnection { implicit c =>
      val results = SQL(
        """
          | SELECT `id`,`issue_name`,`issue_master`,`project_id`,`issue_cover`,`status`,`compiled`,`created_at`,`updated_at`,`deleted_at`,`approval`,`page_counter`
          | FROM `issue`
          | WHERE `deleted_at` IS NULL
          | AND `status`="unpublished"
          | AND `updated_at` IS NOT NULL
          | AND `token`={code_id}
          | ORDER BY `updated_at` DESC;
        """.stripMargin).on(
        "code_id" -> codeId
      ).apply()
      results.map { row =>
        MagzApi(row[Int]("id"),row[String]("issue_name"),row[Int]("issue_master"),row[String]("issue_cover"),row[String]("compiled"),row[DateTime]("updated_at"), row[String]("approval"), row[Int]("page_counter"))
      }.force.toList
    }
  }

  def recheck(projectId:Int): List[EditorApi] = {
    DB.withConnection { implicit c =>
      val results = SQL(
        """
          | SELECT `id`,`issue_name`,`issue_master`,`project_id`,`issue_cover`,`status`,`compiled`,`created_at`,`updated_at`,`deleted_at`,`approval`,`page_counter`
          | FROM `issue`
          | WHERE `deleted_at` IS NULL
          | AND `status`="unpublished"
          | AND `updated_at` IS NOT NULL
          | AND `project_id`={project_id}
          | ORDER BY `updated_at` DESC;
        """.stripMargin).on(
        "project_id" -> projectId
      ).apply()
      results.map { row =>
        EditorApi(row[Int]("id"),row[String]("issue_name"),row[Int]("issue_master"),row[String]("issue_cover"),row[Int]("page_count"),row[String]("status"), row[String]("approval"), row[DateTime]("updated_at"))
      }.force.toList
    }
  }
}
