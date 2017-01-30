package models.dao

import models.Page
import play.api.Play
import play.api.Play.current
import play.api.libs.ws._

import scala.concurrent._

object PageDAO {
  val pagesServiceUrl = Play.current.configuration.getString("service.backend.url").get + "/pages"

  /**
   * Fetches a list of pages associated with the given page ID
   * @param magazineId The ID of the page to find pages of
   * @return A list of pages associated with the specified page
   */
  def index(magazineId: Int)(implicit ec: ExecutionContext): Future[List[Page]] = {
    val holder: WSRequestHolder = WS.url(pagesServiceUrl)
      .withQueryString("magazineId" -> magazineId.toString)

    // Execute the web service request and get back a future of a response
    val fResponse = holder.get()

    fResponse.map { response =>
      // The response will be JSON so parse out the list of pages' IDs
      val pageMagazineIds: List[Int] = (response.json \ "result" \\ "pageMagazineId").map(_.as[Int]).toList

      // Map the list of IDs to a list of pages
      pageMagazineIds.map(Page.apply)
    }
  }
}
