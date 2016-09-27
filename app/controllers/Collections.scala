package controllers

import javax.inject.{Inject, Singleton}

import models._
import models.JsonFormats._
import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import play.modules.reactivemongo.json._
import reactivemongo.api.Cursor
import reactivemongo.play.json.collection.JSONCollection
import services.{SimpleUUIDGenerator, UUIDGenerator}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by fbaumgardt on 31.05.16.
  */
class Collections @Inject() (
                              val uuidGenerator: UUIDGenerator,
                              val reactiveMongoApi: ReactiveMongoApi
                            )
  extends Controller
    with MongoController
    with ReactiveMongoComponents
{

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Application])

  def collection: JSONCollection = db.collection[JSONCollection]("greek")

  def index = Action {
    logger.debug("Index requested.")
    Ok("200")
  }

  def create(record:Record[Int]) = Action {
    logger.debug("Record created.")
    Ok("200")
  }

  def read(id:URN) = Action.async { // Int

    val query = Json.obj(
      "_id.scheme" -> "urn",
      "_id.namespace"->id.namespace,
      "_id.domain"->id.domain,
      "_id.textgroup"->id.textgroup,
      "_id.work"->id.work,
      "_id.edition"->id.edition,
      "_id.passage"->id.passage
    )

    val cursor: Cursor[Sentence] = collection.
      // find all people with name `name`
      find(query).
      // perform the query and get a cursor of JsObject
      cursor[Sentence]

    // gather all the JsObjects in a list
    val futureUsersList: Future[List[Sentence]] = cursor.collect[List]()

    // everything's ok! Let's reply with the array
    futureUsersList.map { persons =>
      Ok(Json.toJson(persons))
    }
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
