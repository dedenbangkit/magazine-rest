package controllers


import authentikat.jwt._
import models.{MagzApi, Page, Validate}
import models.MagzApi._
import models.Page._
import play.api.cache.{Cache, Cached}
import play.api.libs.json._
import play.api.mvc._
import play.api.Play.current
import play.api.mvc.Action
import java.net._

import scala.concurrent.Future
import scala.util.Random
import play.api.http.HeaderNames._


object MagzApis extends Controller {
  private def clearCaches(magazineId: Int, key: String) =
    List(
      "find_" + magazineId + "_" + key,
      "findAll_" + magazineId
    ).map { key =>
      Cache.remove(key)
    }

  val localhost = InetAddress.getLocalHost
  val localIpAddress = localhost.getHostAddress
  val header = JwtHeader("HS256")
  val cky = Random.alphanumeric.filter(_.isLetter).head

  def findstrict(magazineId: Int, key:String, userId:Int) = Action {

        Validate.find(userId, key)
        match {
          case None => NotFound(Json.obj("status" -> "Not Found", "result" -> "404"))
          case Some(validate) =>
            val claimsSet = JwtClaimsSet(Map(key -> magazineId))
            val token: String = JsonWebToken(header, claimsSet, List(localIpAddress, magazineId).mkString).drop(30)
            val allMagzs = MagzApi.findIssue(magazineId)
            val endtoken: String = JsonWebToken(header, claimsSet, List(localIpAddress).mkString).drop(30)
            Ok(Json.obj("results" -> allMagzs)).withHeaders(AUTHORIZATION -> endtoken,
              ACCESS_CONTROL_ALLOW_ORIGIN -> "*",
              ACCESS_CONTROL_ALLOW_METHODS -> "GET, POST, OPTIONS",
              ACCESS_CONTROL_ALLOW_HEADERS ->  "Origin, Accept, Authorization, X-Auth-Token",
              ACCESS_CONTROL_ALLOW_CREDENTIALS -> "true")
    }
  }
  def findstrictAll(magazineId: Int, key: String, userId:Int) = Cached("findAll_"+magazineId) {
    Action {
      Validate.find(userId, key)
      match {
        case None => NotFound(Json.obj("status" -> "Not Found", "result" -> "404"))
        case Some(validate) =>
          val claimsSet = JwtClaimsSet(Map(key -> magazineId))
          val token: String = JsonWebToken(header, claimsSet, List(userId,localIpAddress, magazineId).mkString).drop(30)
          val allMagzs = MagzApi.findAllByIssue(magazineId)
          val endtoken: String = JsonWebToken(header, claimsSet, List(userId, localIpAddress).mkString).drop(30)
          Ok(Json.obj("results" -> allMagzs)).withHeaders(AUTHORIZATION -> endtoken,
            ACCESS_CONTROL_ALLOW_ORIGIN -> "*",
            ACCESS_CONTROL_ALLOW_METHODS -> "GET, POST, OPTIONS",
            ACCESS_CONTROL_ALLOW_HEADERS ->  "Origin, Accept, Authorization, X-Auth-Token",
            ACCESS_CONTROL_ALLOW_CREDENTIALS -> "true")
        }
    }
  }
  
  def find(magazineId: Int, key:String) =
	Action {
            val claimsSet = JwtClaimsSet(Map(key -> magazineId))
            val token: String = JsonWebToken(header, claimsSet, List(localIpAddress, magazineId).mkString).drop(30)
            val allMagzs = MagzApi.findIssue(magazineId)
            val endtoken: String = JsonWebToken(header, claimsSet, List(localIpAddress).mkString).drop(30)
            Ok(Json.obj("results" -> allMagzs)).withHeaders(
              ACCESS_CONTROL_ALLOW_ORIGIN -> "*",
              ACCESS_CONTROL_ALLOW_METHODS -> "GET, POST, OPTIONS",
              ACCESS_CONTROL_ALLOW_HEADERS ->  "Origin, Accept, Authorization, X-Auth-Token",
              ACCESS_CONTROL_ALLOW_CREDENTIALS -> "true")
  }
  def findAll(magazineId: Int, key: String) = Cached("findAll_"+magazineId) {
    Action {
          val claimsSet = JwtClaimsSet(Map(key -> magazineId))
          val token: String = JsonWebToken(header, claimsSet, List(localIpAddress, magazineId).mkString).drop(30)
          val allMagzs = MagzApi.findAllByIssue(magazineId)
          val endtoken: String = JsonWebToken(header, claimsSet, List(localIpAddress).mkString).drop(30)
          Ok(Json.obj("results" -> allMagzs)).withHeaders(
            ACCESS_CONTROL_ALLOW_ORIGIN -> "*",
            ACCESS_CONTROL_ALLOW_METHODS -> "GET, POST, OPTIONS",
            ACCESS_CONTROL_ALLOW_HEADERS ->  "Origin, Accept, Authorization, X-Auth-Token",
            ACCESS_CONTROL_ALLOW_CREDENTIALS -> "true")
    }
  }

  def findPages(issueId: Int) = {
    Action {
          val allPages = Page.findAllPage(issueId)
          Ok(Json.obj("results" -> allPages)).withHeaders(
            ACCESS_CONTROL_ALLOW_ORIGIN -> "*",
            ACCESS_CONTROL_ALLOW_METHODS -> "GET, POST, OPTIONS",
            ACCESS_CONTROL_ALLOW_HEADERS ->  "Origin, Accept, Authorization, X-Auth-Token",
            ACCESS_CONTROL_ALLOW_CREDENTIALS -> "true")
    }
  }

}
