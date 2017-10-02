package dao

import scala.concurrent.Future

import play.api.Configuration
import play.api.db.evolutions.Evolutions
import play.api.db.{ DBApi, Database }
import play.api.inject.guice.GuiceApplicationBuilder

import com.typesafe.config.{ Config, ConfigFactory }
import domain.Board
import org.scalatest.Matchers._
import org.scalatest.OptionValues._
import org.scalatest.{ AsyncFlatSpec, BeforeAndAfter }
import slick.jdbc.H2Profile

class BoardRepositoryTest extends AsyncFlatSpec with BeforeAndAfter {

  val config: Config = ConfigFactory.load("testApplication.conf")
  val configuration: Configuration = Configuration(config)
  val appBuilder: GuiceApplicationBuilder = new GuiceApplicationBuilder().configure(configuration)

  def database: Database = appBuilder.injector().instanceOf[DBApi].database("default")

  def evolutionConfig: Option[Configuration] = configuration.getOptional[Configuration]("play.evolutions.db.default")

  val repository: BoardRepository = new BoardRepository(new DatabaseSupport(config, H2Profile))

  before {
    Evolutions.applyEvolutions(database)
  }

  after {
    Evolutions.cleanupEvolutions(database)
  }

  "test the findAll method in BoardRepository" should "size of boards is one" in {
    val result: Future[Seq[Board]] = repository.findAll()
    val board: Option[Board] = Some(Board(1, "title", "context", "admin"))

    result map { boards =>
      boards.size should equal(1)
      boards.head should equal(board.value)
    }
  }

  "test the delete method in BoardRepository" should "size of boards is zero" in {
    repository.delete(1L)

    val result: Future[Seq[Board]] = repository.findAll()

    result map { boards =>
      boards.size should equal(0)
    }
  }
}
