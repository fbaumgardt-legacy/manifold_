package models
import play.api.mvc.PathBindable
import play.modules.reactivemongo.json._

/**
  * Created by fbaumgardt on 18.05.16.
  */
case class URN(namespace:String="",domain:String="",textgroup:String="",work:String="",edition:String="",passage:String="",sentence:String="")

object URN {
  val sentence = "urn:([^:^\\.]+):([^:^\\.]+):([^:^\\.]+)\\.([^:^\\.]+)\\.([^:^\\.]+):([^:^\\.]+)\\.([^:^\\.]+)".r
  val passage = "urn:([^:^\\.]+):([^:^\\.]+):([^:^\\.]+)\\.([^:^\\.]+)\\.([^:^\\.]+):([^:^\\.]+)".r
  val edition = "urn:([^:^\\.]+):([^:^\\.]+):([^:^\\.]+)\\.([^:^\\.]+)\\.([^:^\\.]+)".r
  val work = "urn:([^:^\\.]+):([^:^\\.]+):([^:^\\.]+)\\.([^:^\\.]+)".r
  val textgroup = "urn:([^:^\\.]+):([^:^\\.]+):([^:^\\.]+)".r
  val domain = "urn:([^:^\\.]+):([^:^\\.]+)".r
  val namespace = "^urn:([^:^\\.]+)$".r

  def apply(urn: String) = urn match {
    case sentence(a, b, c, d, e, f, g) => new URN(a, b, c, d, e, f, g)
    case passage(a, b, c, d, e, f) => new URN(a, b, c, d, e, f)
    case edition(a, b, c, d, e) => new URN(a, b, c, d, e)
    case work(a, b, c, d) => new URN(a, b, c, d)
    case textgroup(a, b, c) => new URN(a, b, c)
    case domain(a, b) => new URN(a, b)
    case namespace(a) => new URN(a)
    case _ => new URN("")
  }

  implicit def pathBinder() = new PathBindable[models.URN] {

    override def bind(key:String, value:String):URN = URN.apply(value)

    override def unbind(key: String, value: URN): String = value match {
      case URN(ns,"","","","","","") => "urn:"+ns
      case URN(ns,dm,"","","","","") => "urn:"+ns+":"+dm
      case URN(ns,dm,tg,"","","","") => "urn:"+ns+":"+dm+":"+tg
      case URN(ns,dm,tg,wk,"","","") => "urn:"+ns+":"+dm+":"+tg+"."+wk
      case URN(ns,dm,tg,wk,ed,"","") => "urn:"+ns+":"+dm+":"+tg+"."+wk+"."+ed
      case URN(ns,dm,tg,wk,ed,ps,"") => "urn:"+ns+":"+dm+":"+tg+"."+wk+"."+ed+":"+ps
      case URN(ns,dm,tg,wk,ed,ps,sn) => "urn:"+ns+":"+dm+":"+tg+"."+wk+"."+ed+":"+ps+"."+sn
      case _ => "urn:"
    }

  }
}
case class Word(id:String, form:String, lemma:String, morphology:Map[String,String], head:String, relation:String)

case class Sentence(_id:URN,words:Seq[Word])

// case class Adress(val scheme:String, )

object JsonFormats {
  import play.api.libs.json.Json
  implicit val urnFormat = Json.format[URN]
  implicit val wordFormat = Json.format[Word]
  implicit val sentenceFormat = Json.format[Sentence]
}