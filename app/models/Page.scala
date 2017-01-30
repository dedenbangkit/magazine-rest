package models

import models.dao.PageDAO

import scala.concurrent._

object Page {
  def findAllPages(pageMagazineId: Int)(implicit ec: ExecutionContext): Future[Set[Page]] =
    PageDAO.index(pageMagazineId).map(_.toSet)
}

case class Page(pageMagazineId: Int)