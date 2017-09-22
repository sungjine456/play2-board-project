package dao

import scala.concurrent.Await
import scala.concurrent.duration._

import play.api.Configuration
import play.api.db.evolutions.Evolutions
import play.api.db.{ DBApi, Database }
import play.api.inject.guice.GuiceApplicationBuilder

import com.typesafe.config.{ Config, ConfigFactory }
import dao.BoardRepository.Boards
import domain.Board
import org.scalatest.Matchers._
import org.scalatest.{ BeforeAndAfter, FlatSpec }
import slick.jdbc.H2Profile.api._

class BoardRepositoryTest extends FlatSpec with BeforeAndAfter {

  val config: Config = ConfigFactory.load("testApplication.conf")
  val configuration: Configuration = Configuration(config)
  val appBuilder: GuiceApplicationBuilder = new GuiceApplicationBuilder().configure(configuration)

  def database: Database = appBuilder.injector().instanceOf[DBApi].database("default")

  def evolutionConfig: Option[Configuration] = configuration.getOptional[Configuration]("play.evolutions.db.default")

  val db = Database.forConfig("slick.dbs.default.db", config)
  val testBoards = TableQuery[Boards]

  before {
    Evolutions.applyEvolutions(database)
  }

  after {
    Evolutions.cleanupEvolutions(database)
  }

  "boards size" should "one" in {
    val result: Seq[Board] = Await.result(db.run(testBoards.result), 1.seconds)

    result.seq.size should equal(1)
    result.head should equal(Board(1, "title", "context", "admin"))
  }
}
