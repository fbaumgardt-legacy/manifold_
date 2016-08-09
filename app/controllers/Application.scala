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
import services.{UUIDGenerator,SimpleUUIDGenerator}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Instead of declaring an object of Application as per the template project, we must declare a class given that
 * the application context is going to be responsible for creating it and wiring it up with the UUID generator service.
 * @param uuidGenerator the UUID generator service we wish to receive.
 */
@Singleton
class Application @Inject() (
                              val uuidGenerator: UUIDGenerator,
                              val reactiveMongoApi: ReactiveMongoApi
                            )
  extends Controller
    with MongoController
    with ReactiveMongoComponents
{

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Application])

  def collection: JSONCollection = db.collection[JSONCollection]("greek")

  def index = Action.async {
    logger.info("Serving index page...")

    val cursor: Cursor[Sentence] = collection.
      // find all people with name `name`
      find(Json.obj("_id.scheme" -> "urn", "_id.namespace"->"cts", "_id.domain"->"greekLit", "_id.textgroup"->"tlg0003", "_id.work"->"tlg001", "_id.edition"->"perseus-grc1")).
      // perform the query and get a cursor of JsObject
      cursor[Sentence]

    // gather all the JsObjects in a list
    val futureUsersList: Future[List[Sentence]] = cursor.collect[List]()

    // everything's ok! Let's reply with the array
    futureUsersList.map { persons =>
      Ok(Json.toJson(persons))
    }
  }

  def randomUUID = Action {
    logger.info("calling UUIDGenerator...")
    Ok(uuidGenerator.generate.toString)
  }

}
