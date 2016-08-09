package controllers

import models.URN
import org.slf4j.{Logger, LoggerFactory}
import play.modules.reactivemongo.MongoController
import play.api.mvc.{Action, Controller}

/**
  * Created by fbaumgardt on 08.06.16.
  */
class Aggregates extends Controller { //with MongoController{

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Application])

  def gender(id:URN) = Action {
    val find = """{$match:{"_id.textgroup":"""+s"${id.textgroup}"+"""","_id.work":""""+s"${id.work}"+""""}}"""
    val project = """{$project:{"gender":"$words.morphology.gender"}}"""
    val unwind = "{$unwind:\"$gender\"}"
    val count = "{$group:{_id:{psg:\"$_id.passage\",snt:\"$_id.sentence\",gnd:\"$gender\"},count:{$sum:1}}}"
    val group = "{$group:{_id:{psg:\"$_id.psg\",snt:\"$_id.snt\"},gender:{$push:{type:\"$_id.gnd\",count:\"$count\"}}}}"

    val query = s"db.greek.aggregate([$find,$project,$unwind,$count,$group])"
    Ok("200")
  }

}
