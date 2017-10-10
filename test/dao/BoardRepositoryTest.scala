package dao

import play.api.Configuration
import play.api.db.evolutions.Evolutions
import play.api.db.{ DBApi, Database }
import play.api.inject.guice.GuiceApplicationBuilder

import com.typesafe.config.ConfigFactory
import domain.Board
import org.scalatest.Matchers._
import org.scalatest.{ AsyncFlatSpec, BeforeAndAfter }
import slick.jdbc.H2Profile

class BoardRepositoryTest extends AsyncFlatSpec with BeforeAndAfter {

  val config = ConfigFactory.load("testApplication.conf")
  val configuration = Configuration(config)
  val appBuilder = new GuiceApplicationBuilder().configure(configuration)

  def database: Database = appBuilder.injector().instanceOf[DBApi].database("default")

  def evolutionConfig: Option[Configuration] = configuration.getOptional[Configuration]("play.evolutions.db.default")

  val repository = new BoardRepository(new DatabaseSupport(config, H2Profile))

  before {
    Evolutions.applyEvolutions(database)
  }

  after {
    Evolutions.cleanupEvolutions(database)
  }

  "test the findAll method in BoardRepository" should "size of boards is one" in {
    val result = repository.findAll()
    val board = Some(Board(1, "title", "context", "admin"))

    result map { boards =>
      boards.size should equal(1)
      boards.head should equal(board.value)
    }
  }

  "test the delete method in BoardRepository" should "size of boards is zero" in {
    repository.delete(1L)

    val result = repository.findAll()

    result map { boards =>
      boards.size should equal(0)
    }
  }

  "test the update method in BoardRepository" should "the board has been changed" in {
    val board = Board(1, "change title", "change context", "change admin")

    repository.update(board)

    val result = repository.findAll()

    result map { boards =>
      boards.size should equal(1)
      boards.last should equal(board)
    }
  }
}
