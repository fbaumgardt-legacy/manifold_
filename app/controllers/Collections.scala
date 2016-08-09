package controllers

import models.{Record, URN}
import org.slf4j.{Logger, LoggerFactory}
import play.api.mvc.{Action, Controller}
import play.modules.reactivemongo.MongoController

/**
  * Created by fbaumgardt on 31.05.16.
  */
class Collections extends Controller { //with MongoController {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Application])

  def index = Action {
    logger.debug("Index requested.")
    Ok("200")
  }

  def create(record:Record[Int]) = Action {
    logger.debug("Record created.")
    Ok("200")
  }

  def read(id:URN) = Action { // Int
    logger.debug(id.toString)
    Ok("200")
  }

  def update(record:Record[Int]) = Action {
    logger.debug("Record updated.")
    Ok("200")
  }

  def delete(record:Record[Int]) = Action {
    logger.debug("Record deleted.")
    Ok("200")
  }
}
