package models

import org.joda.time.DateTime
import models.dao.PageDAO
import play.api.libs.concurrent.Akka
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.Play.current

case class Page(issueId: Int, pageNum: Int, pageName: String, pageContent: Option[String])

object Page {

  val oValue = """<o"""
  val pageUi = """<div id=/"page/" class=/"page/">"""
  val article = """<article></article>"""
  val articleO = """<article class=\"article\">"""
  val articleC = """</article>"""
  val divItem = """<div class=\"item"""
  val divRow = """<div class=\"row"""
  val rowValue = """class=\"row article__content\" id=\"demos\""""
  val pMce = """class=\"article__by-line\" data-selector=\"p\" """
  val editAble = """data-editable=\"\" """
  val divMce = """class=\"container mce-content-body\" data-selector=\".container\""""
  val mceRem = """id=\"mce_0\" contenteditable=\"true\" style=\"position: relative;\" spellcheck=\"false\""""
  val dataCol = """data-selector=\".column\""""
  val dataCon = """data-selector=\".container\""""
  val dataP = """data-selector=\"p\" """
  val imgSrc = "img img-responsive"
  val divColumn12 = "column col-xs-12"
  val divColumn10 = "column col-xs-10"
  val divColumn9 = "column col-xs-9"
  val divColumn8 = "column col-xs-8"
  val divColumn6 = "column col-xs-6"
  val divColumn4 = "column col-xs-4"
  val divColumn3 = "column col-xs-3"

  val replaceHTML: Writes[String] = new Writes[String] {
    def writes(d: String): JsValue = JsString(
     d.replaceAll(oValue, """<o style=\"display:none;\" """)
       .replaceAll(pageUi, """<div class=\"padding\"""")
       .replaceAll(article, "")
       .replaceAll(articleO, "")
       .replaceAll(articleC, "")
       .replaceAll(rowValue,"")
       .replaceAll(divRow,"""<div class=\"row-bootstrap""")
       .replaceAll(divItem,"""<div class=\"""")
       .replaceAll(editAble,"")
       .replaceAll(mceRem,"")
       .replaceAll(pMce, "")
       .replaceAll(divMce,"")
       .replaceAll(dataCol,"")
       .replaceAll(dataCon, "")
       .replaceAll(dataP, "")
       .replaceAll(imgSrc, "full-image")
       .replaceAll(divColumn12, "col")
       .replaceAll(divColumn10, "col")
       .replaceAll(divColumn9, "col")
       .replaceAll(divColumn8, "col")
       .replaceAll(divColumn6, "col")
       .replaceAll(divColumn4, "col")
       .replaceAll(divColumn3, "col")
    )
  }

  val PageReads: Reads[Page] = (
      (JsPath \ "issueId").read[Int] and
      (JsPath \ "pageId").read[Int] and
      (JsPath \ "pageName").read[String] and
      (JsPath \ "pageContent").readNullable[String]
    )(Page.apply _)

  implicit val PageWrites: Writes[Page] = (
      (JsPath \ "issueId").write[Int] and
      (JsPath \ "pageId").write[Int] and
      (JsPath \ "pageName").write[String] and
      (JsPath \ "pageContent").writeNullable[String](replaceHTML)
    )(unlift(Page.unapply))

  def findAllPage(issueId:Int): List[Page] =
    PageDAO.index(issueId)

  def find(issueId:Int): List[Page] =
    PageDAO.show(issueId)
}
