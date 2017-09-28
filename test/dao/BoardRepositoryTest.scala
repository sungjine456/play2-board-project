package dao

import scala.concurrent.Future

import play.api.Configuration
import play.api.db.evolutions.Evolutions
import play.api.db.{ DBApi, Database }
import play.api.inject.guice.GuiceApplicationBuilder

import com.typesafe.config.{ Config, ConfigFactory }
import dao.BoardRepository.Boards
import domain.Board
import org.scalatest.Matchers._
import org.scalatest.{ AsyncFlatSpec, BeforeAndAfter }
import slick.jdbc.H2Profile.api._

class BoardRepositoryTest extends AsyncFlatSpec with BeforeAndAfter {

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
    val result: Future[Seq[Board]] = db.run(testBoards.result)

    result map { boards =>
      boards.size should equal(1)
      boards.head should equal(Board(1, "title", "context", "admin"))
    }
  }
}
