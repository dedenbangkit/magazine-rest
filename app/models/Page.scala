package models

import org.joda.time.DateTime
import models.dao.PageDAO
import play.api.libs.concurrent.Akka
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.Play.current

case class Page(issueId: Int, pageNum: Int, pageName: String, pageContent: Option[String])

object Page {

  val oRegex = """onclick=\"sortableMode\(\)\""""
  val oRegex2 = """onclick=\"sortableClose\(\)\""""
  val oValue1 = """<o class=\"btn btn-embossed sortable-function check-columns btn-success\" id=\"one-col\">1 <i class=\"fa fa-navicon\"></i></o>"""
  val oValue2 = """<o class=\"btn btn-inverse btn-embossed sortable-function check-columns\" id=\"two-col\">2 <i class=\"fa fa-navicon\"></i></o>"""
  val oValue3 = """<o class=\"btn btn-inverse btn-embossed sortable-function check-columns\" id=\"three-col\">3 <i class=\"fa fa-navicon\"></i></o>"""
  val oValue4 = """<o class=\"btn btn-inverse btn-embossed sortable-function check-columns\" id=\"four-col\">4 <i class=\"fa fa-navicon\"></i></o>"""
  val oValueA = """<o class=\"btn btn-inverse btn-embossed sortable-function\" style=\"display: inline-block;\"><i class=\"glyphicon glyphicon-move\"></i></o>"""
  val oValueB = """<o class=\"btn btn-inverse btn-embossed sortable-function\" style=\"display: inline-block;\"></o>"""
  val oValueC = """<o class=\"btn btn-success btn-embossed sortable-function-edit\" style=\"display: none;\"><i class=\"fa fa-arrows-h\"></i></o>"""
  val oValueD = """<o class=\"btn btn-inverse btn-embossed sortable-function\" id=\"sortableEdit\"  style=\"display: inline-block;\"><i class=\"fa fa-arrows-h\"></i></o>"""
  val oValueE = """<o class=\"btn btn-inverse btn-embossed sortable-function\" ><i class=\"glyphicon glyphicon-move\"></i></o>"""
  val oValueF = """<o class=\"btn btn-success btn-embossed sortable-function-edit\" style=\"display: none;\"><i class=\"glyphicon glyphicon-move\"></i></o>"""
  val pageUi = """<div class=\"item content\" id=\"content_section1\""""
  val divPage = """<div id=\"page\" class=\"page\">"""
  val article = """<article></article>"""
  val articleO = """<article class=\"article\">"""
  val articleC = """</article>"""
  val rowValue = """class=\"row article__content\""""
  val demosValue = """ id=\"demos\""""
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
  val carouselIn = """<div class=\"carousel-inner\"><ion-slides slider=\"data.slider\" class=\"do_not_swipe\">"""
  val carouselOut = """</ion-slides></div>"""
  val carouselCss = """class="carousels""""
  val dashedCss = """outline: red dashed 3px;"""
  val dashedCss2 = """outline: 3px dashed red;"""
  val cursorCss = """cursor: pointer;"""
  val carouselBtn1 = """<o class=\"carousel-control new-image\" id=\"add-img\"><span class=\"fui-plus\"></span></o>"""
  val carouselBtn2 = """<o class=\"carousel-control remove-image\" id=\"remove-img\"><span class=\"fui-trash\"></span></o>"""
  var ionSlides= """ion-slide-page class=\"item\""""
  var ionSlidesAct= """ion-slide-page class=\"item active\""""
  var ionStyle="""alt=\"\" data-selector=\".carousels\""""
  var ionData="""<a class=\"left carousel-control fui-arrow-left\" href=\"#myCarousel\" data-slide=\"prev\"></a> <a class=\"right carousel-control fui-arrow-right\" href=\"#myCarousel\" data-slide=\"next\"></a> </div>"""

  val replaceHTML: Writes[String] = new Writes[String] {
    def writes(d: String): JsValue = JsString(
     d.replaceAll(oRegex,"").replaceAll(oRegex2,"")
       .replaceAll(oValue1, "").replaceAll(oValue2,"").replaceAll(oValue3, "").replaceAll(oValue4,"")
       .replaceAll(oValueA,"").replaceAll(oValueB,"").replaceAll(oValueC, "").replaceAll(oValueD,"").replaceAll(oValueE,"").replaceAll(oValueF,"")
       .replaceAll(pageUi,"""<div class=\"padding\"""")
       .replaceAll(article, "")
       .replaceAll(articleO, "")
       .replaceAll(articleC, "")
       .replaceAll(rowValue,"""class=\"row\"""")
       .replaceAll(demosValue,"")
       .replaceAll(divPage,"""<div class=\"issue-page\">""")
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
       .replaceAll(carouselIn, """<ion-slides slider="data.slider" class="do_not_swipe">""")
       .replaceAll(carouselOut, """</ion-slides>""")
       .replaceAll(carouselCss, """width="100%"""")
       .replaceAll(dashedCss, "")
       .replaceAll(dashedCss2,"")
       .replaceAll(cursorCss, "")
       .replaceAll(carouselBtn1, "")
       .replaceAll(carouselBtn2, "")
       .replaceAll(ionSlides, "ion-slide-page")
       .replaceAll(ionSlidesAct, "ion-slide-page")
       .replaceAll(ionData, "")
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
